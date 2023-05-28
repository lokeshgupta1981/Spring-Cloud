package com.example.springcloudawss3;

import com.example.springcloudawss3.service.S3ClientService;
import com.example.springcloudawss3.service.S3TemplateService;
import io.awspring.cloud.s3.InMemoryBufferingS3OutputStreamProvider;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class S3TemplateSErviceTest {

    @Mock
    private S3Template s3Template ;

    @Mock
    private S3Client s3ClientMock;



    private S3TemplateService s3ClientService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        s3ClientService = new S3TemplateService(s3Template);
    }

    @Test
    void testCreateBucketWithS3Template() {
        String bucketName = "test-bucket";
        s3ClientService.createBucketWithS3Template(bucketName);
        verify(s3Template).createBucket(bucketName);
    }
    @Test
    void testUploadObjectWithS3Template() throws IOException {
        String bucketName = "test-bucket";
        String key = "test-object";
        String content = "Hello, World!";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                "text/plain", content.getBytes());

        s3ClientService.uploadObjectWithS3Template(bucketName, key, file);

        verify(s3Template).upload(eq(bucketName), eq(key), any(InputStream.class));
    }
    @Test
    void testDeleteObjectWithS3Template() {
        String bucketName = "test-bucket";
        String key = "test-object";
        s3ClientService.deleteObjectWithS3Template(bucketName, key);
        verify(s3Template).deleteObject(bucketName, key);
    }
    @Test
    void testReadFileFromS3WithS3Template() throws IOException {
        String bucketName = "test-bucket";
        String key = "test-object";
        S3Resource s3Resource = new S3Resource(bucketName, key, s3ClientMock,new InMemoryBufferingS3OutputStreamProvider(s3ClientMock , null));
        when(s3Template.download(bucketName, key)).thenReturn(s3Resource);

        S3Resource result = s3ClientService.readFileFromS3WithS3Template(bucketName, key);

        verify(s3Template).download(bucketName, key);
        assertEquals(s3Resource, result);
    }
}
