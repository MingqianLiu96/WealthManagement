package com.ascending.mingqian.service;

import com.amazonaws.util.IOUtils;
import com.ascending.mingqian.init.AppInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class FileServiceTest {
    @Autowired
    private Logger logger;
    @Autowired
    private FileService fileService;

    private String bucketName = "mingqian";
    private String fileName = "test.txt";
    private URL fakeFileUrl;
    private MultipartFile multipartFile;
    private String path;

    @Before
    public void setUp() throws MalformedURLException, FileNotFoundException, IOException {
        fakeFileUrl = new URL("http://www.fakeQueueUrl.com/abc/123/fake");
        File file = new File("/Users/molly/test.txt");
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
        path = System.getProperty("user.dir") + File.separator + "temp";
    }

    @After
    public void tearDown(){
         logger.info(">>>>>>>>>>>>>> End test");
        }

    @Test
    public void creatBucket(){
        fileService.createBucket("mingqian1996");
    }


    @Test
     public void uploadFile() throws IOException{
         String fileUrl = fileService.uploadFile(bucketName, multipartFile);
            Assert.assertNotNull(fileUrl);
        }

    @Test
    public void getFileUrl() throws IOException, FileNotFoundException{
        String fileUrl = fileService.getFileUrl(bucketName, fileName);
        System.out.println(fileUrl);
        Assert.assertNotNull(fileUrl);
    }


    @Test
    public void saveFile() throws IOException, FileNotFoundException{
        boolean isSuccess = fileService.saveFile(multipartFile, path);
            Assert.assertEquals(isSuccess,true);
    }



}
