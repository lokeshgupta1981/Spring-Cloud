package com.example.springcloudawss3;

import com.example.springcloudawss3.service.S3Service;
import io.awspring.cloud.s3.InMemoryBufferingS3OutputStreamProvider;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class S3ServiceTest {

    @Mock
    private S3Client s3ClientMock;

    @Mock
    private S3Template s3Template ;

    private S3Service s3Service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        s3Service = new S3Service(s3ClientMock , s3Template);
    }

    @Test
    void testCreateBucket() {
        String bucketName = "my-bucket";

        CreateBucketRequest expectedCreateRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        // Mock the S3Client behavior
        when(s3ClientMock.createBucket(expectedCreateRequest))
                .thenReturn(CreateBucketResponse.builder().build());

        // Call the method under test
        s3Service.createBucket(bucketName);

        // Verify the S3Client method was called with the expected request
        verify(s3ClientMock).createBucket(expectedCreateRequest);
    }

    @Test
     void testUploadObject() throws IOException, IOException {
        String bucketName = "my-bucket";
        String key = "my-key";
        MultipartFile file = mock(MultipartFile.class);
        byte[] bytes = "file-content".getBytes();

        // Mock the behavior for getting file bytes
        when(file.getBytes()).thenReturn(bytes);

        // Call the method under test
        s3Service.uploadObject(bucketName, key, file);

        // Verify the S3Client method was called with the expected request
        verify(s3ClientMock).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void testDeleteObject() {
        String bucketName = "my-bucket";
        String key = "my-key";

        DeleteObjectRequest expectedDeleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        // Mock the S3Client behavior
        when(s3ClientMock.deleteObject(expectedDeleteRequest))
                .thenReturn(DeleteObjectResponse.builder().build());

        // Call the method under test
        s3Service.deleteObject(bucketName, key);

        // Verify the S3Client method was called with the expected request
        verify(s3ClientMock).deleteObject(expectedDeleteRequest);
    }

    @Test
    public void testCreateBucketWithS3Template() {
        String bucketName = "test-bucket";
        s3Service.createBucketWithS3Template(bucketName);
        verify(s3Template).createBucket(bucketName);
    }
    @Test
    void testUploadObjectWithS3Template() throws IOException {
        String bucketName = "test-bucket";
        String key = "test-object";
        String content = "Hello, World!";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                "text/plain", content.getBytes());

        s3Service.uploadObjectWithS3Template(bucketName, key, file);

        verify(s3Template).upload(eq(bucketName), eq(key), any(InputStream.class));
    }
    @Test
    void testDeleteObjectWithS3Template() {
        String bucketName = "test-bucket";
        String key = "test-object";
        s3Service.deleteObjectWithS3Template(bucketName, key);
        verify(s3Template).deleteObject(bucketName, key);
    }
    @Test
    void testReadFileFromS3WithS3Template() throws IOException {
        String bucketName = "test-bucket";
        String key = "test-object";
        S3Resource s3Resource = new S3Resource(bucketName, key, s3ClientMock,new  InMemoryBufferingS3OutputStreamProvider(s3ClientMock , null));
        when(s3Template.download(bucketName, key)).thenReturn(s3Resource);

        S3Resource result = s3Service.readFileFromS3WithS3Template(bucketName, key);

        verify(s3Template).download(bucketName, key);
        assertEquals(s3Resource, result);
    }
}
