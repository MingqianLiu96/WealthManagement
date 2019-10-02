package com.ascending.mingqian.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.util.IOUtils;
import com.ascending.mingqian.init.AppInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class FileServiceMockAWSTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private AmazonS3 amazonS3;
    @Autowired @Spy private Logger logger;
    @InjectMocks private FileService fileService;

    private String bucketName = "mingqian";
    private String fileName = "test.txt";
    private URL fakeFileUrl;
    private MultipartFile multipartFile;
    private String path;

    @Before
    public void setUp() throws MalformedURLException, FileNotFoundException, IOException {
        logger.info(">>>>>>>>>>>>>> Start test");

        MockitoAnnotations.initMocks(this);

        fakeFileUrl = new URL("http://www.fakeQueueUrl.com/abc/123/fake");
        File file = new File("/Users/molly/test.txt");
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
        path = System.getProperty("user.dir") + File.separator + "temp";

        when(amazonS3.doesObjectExist(anyString(),anyString())).thenReturn(false);
        when(amazonS3.generatePresignedUrl(any())).thenReturn(fakeFileUrl);

    }

    @After
    public void tearDown(){
        logger.info(">>>>>>>>>>>>>> End test");
    }

    @Test
    public void saveFile() throws IOException, FileNotFoundException{
        MultipartFile multipartFile = new MockMultipartFile(" ", new byte[1]);
        String path = " ";

        FileService fs = Mockito.mock(FileService.class);

        when(fs.saveFile(any(),anyString())).thenReturn(true); //It will call real method on spied object
       // doReturn(true).when(fs).saveFile(any(),anyString()); //it will not call real method on spied object
        // same to mock object, different to spy object


        boolean isSuccess = fs.saveFile(multipartFile, path);

        Assert.assertTrue(isSuccess);

        verify(fs, times(1)).saveFile(any(), anyString());
    }

    @Test
    public void getFileUrl(){
        String fileUrl = fileService.getFileUrl(bucketName, fileName);
        Assert.assertEquals(fileUrl, fakeFileUrl.toString());
        verify(amazonS3, times(1)).generatePresignedUrl(any());
    }

    @Test
    public  void  uploadFile() throws IOException{
        String fileUrl = fileService.uploadFile(bucketName, multipartFile);
        Assert.assertNotNull(fileUrl);
        verify(amazonS3, times(1)).doesObjectExist(anyString(), anyString());
        verify(amazonS3, times(1)).putObject(anyString(), anyString(), any(), any());
    }

}
