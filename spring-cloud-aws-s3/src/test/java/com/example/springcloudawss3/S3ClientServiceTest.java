package com.example.springcloudawss3;

import com.example.springcloudawss3.service.S3ClientService;
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

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class S3ClientServiceTest {

    @Mock
    private S3Client s3ClientMock;



    private S3ClientService s3ClientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        s3ClientService = new S3ClientService(s3ClientMock);
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
        s3ClientService.createBucket(bucketName);

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
        s3ClientService.uploadObject(bucketName, key, file);

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
        s3ClientService.deleteObject(bucketName, key);

        // Verify the S3Client method was called with the expected request
        verify(s3ClientMock).deleteObject(expectedDeleteRequest);
    }
}
