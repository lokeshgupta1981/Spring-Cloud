package com.howtodoinjava.demo;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.howtodoinjava.demo.service.S3ClientService;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

class S3ClientServiceTest {

  @Mock
  private S3Client mock;

  private S3ClientService s3ClientService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    s3ClientService = new S3ClientService(mock);
  }

  @Test
  void testCreateBucket() {
    String bucketName = "my-bucket";

    CreateBucketRequest expectedCreateRequest = CreateBucketRequest.builder()
        .bucket(bucketName)
        .build();

    // Mock the S3Client behavior
    when(mock.createBucket(expectedCreateRequest))
        .thenReturn(CreateBucketResponse.builder().build());

    // Call the method under test
    s3ClientService.createBucket(bucketName);

    // Verify the S3Client method was called with the expected request
    verify(mock).createBucket(expectedCreateRequest);
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
    verify(mock).putObject(any(PutObjectRequest.class), any(RequestBody.class));
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
    when(mock.deleteObject(expectedDeleteRequest))
        .thenReturn(DeleteObjectResponse.builder().build());

    // Call the method under test
    s3ClientService.deleteObject(bucketName, key);

    // Verify the S3Client method was called with the expected request
    verify(mock).deleteObject(expectedDeleteRequest);
  }
}
