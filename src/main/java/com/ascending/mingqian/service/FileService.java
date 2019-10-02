package com.ascending.mingqian.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service
public class FileService {
    @Autowired
    private Logger logger;
    @Autowired
    private AmazonS3 amazonS3;

    public void createBucket(String bucketName){
        if(!amazonS3.doesBucketExistV2(bucketName))
            amazonS3.createBucket(bucketName);
    }

    public void listBucket() {
        amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        List<Bucket> buckets = amazonS3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }
    }

    public void deleteBucket(String bucketName){
        try {
            System.out.println(" - removing objects from bucket");
            ObjectListing object_listing = amazonS3.listObjects(bucketName);
            while (true) {
                for (Iterator<?> iterator =
                     object_listing.getObjectSummaries().iterator();
                     iterator.hasNext(); ) {
                    S3ObjectSummary summary = (S3ObjectSummary) iterator.next();
                    amazonS3.deleteObject(bucketName, summary.getKey());
                }

                // more object_listing to retrieve?
                if (object_listing.isTruncated()) {
                    object_listing = amazonS3.listNextBatchOfObjects(object_listing);
                } else {
                    break;
                }
            }

            System.out.println(" - removing versions from bucket");
            VersionListing version_listing = amazonS3.listVersions(
                    new ListVersionsRequest().withBucketName(bucketName));
            while (true) {
                for (Iterator<?> iterator =
                     version_listing.getVersionSummaries().iterator();
                     iterator.hasNext(); ) {
                    S3VersionSummary vs = (S3VersionSummary) iterator.next();
                    amazonS3.deleteVersion(
                            bucketName, vs.getKey(), vs.getVersionId());
                }

                if (version_listing.isTruncated()) {
                    version_listing = amazonS3.listNextBatchOfVersions(
                            version_listing);
                } else {
                    break;
                }
            }

            System.out.println(" OK, bucket ready to delete!");
            amazonS3.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

        System.out.println("Done!");
    }

    public void deleteObject(String bucketName, String objectKey){
        System.out.format("Deleting object %s from S3 bucket: %s\n", objectKey,
                bucketName);
        try {
            amazonS3.deleteObject(bucketName, objectKey);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }

    public void listObject(String bucketName){
        System.out.format("Objects in S3 bucket %s:\n", bucketName);
        ListObjectsV2Result result = amazonS3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
        }
    }

    public void downloadObject(String bucketName, String keyName){
        System.out.format("Downloading %s from S3 bucket %s...\n", keyName, bucketName);

        try {
            S3Object o = amazonS3.getObject(bucketName, keyName);
            S3ObjectInputStream s3is = o.getObjectContent();
            FileOutputStream fos = new FileOutputStream(new File(keyName));
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Done!");

    }


    public String getFileUrl(String bucketName, String fileName){
        LocalDateTime expiration = LocalDateTime.now().plusDays(1);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        generatePresignedUrlRequest.withMethod(HttpMethod.GET);
        generatePresignedUrlRequest.withExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)));

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    public String uploadFile(String bucketName, MultipartFile file) throws IOException {
        try {
            if (amazonS3.doesObjectExist(bucketName, file.getOriginalFilename())) {
                logger.info(String.format("The file '%s' exists in the bucket %s", file.getOriginalFilename(), bucketName));
                return null;
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), objectMetadata);

//            PutObjectRequest request = new PutObjectRequest(bucketName, file.getOriginalFilename(), new File(file.getName()));
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(file.getContentType());
//            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
//            request.setMetadata(metadata);
//            amazonS3.putObject(request);


            logger.info(String.format("The file name=%s, size=%d was uploaded to bucket %s", file.getOriginalFilename(), file.getSize(), bucketName));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return getFileUrl(bucketName, file.getOriginalFilename());
    }

    //write to disk, no relationship with amazon
    public boolean saveFile(MultipartFile multipartFile, String filePath) {
        boolean isSuccess = false;
        try{
            File directory = new File(filePath);
            if(!directory.exists()) directory.mkdir();
            Path filepath = Paths.get(filePath, multipartFile.getOriginalFilename());
            multipartFile.transferTo(filepath);
            isSuccess = true;
            logger.info(String.format("The file %s is saved in the folder %s", multipartFile.getOriginalFilename(),filePath));
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return isSuccess;
    }

}
