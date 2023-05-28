package com.example.springcloudawss3.service;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3ClientService {



    @Value("s3://howtodoinjava03/12345678910")
    private Resource s3Resource;

    private final S3Client  s3Client;

    public S3ClientService(S3Client s3Client ) {
        this.s3Client = s3Client;
    }


    public void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

       s3Client.createBucket(createBucketRequest);
    }

    public void uploadObject(String bucketName, String key, MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, bytes.length));
    }
    public void deleteObject(String bucketName, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
    public String readFileFromS3(String bucketName, String objectKey) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
        return  new String(response.readAllBytes());
    }
    public String getResource() throws IOException {
        try (InputStream inputStream = s3Resource.getInputStream()) {
            // Read the content from the S3 object
            // ...
            return new String(inputStream.readAllBytes());
        }
    }
    public Resource readS3ObjectAsResource(String bucketName, String objectKey) {
        return s3Resource ;
    }

}
