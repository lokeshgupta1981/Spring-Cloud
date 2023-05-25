package com.example.springcloudawss3.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {


    private final S3Client  s3Client;

    public S3Service(S3Client s3Client) {
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

}
