package com.example.springcloudawss3.service;

import io.awspring.cloud.s3.InMemoryBufferingS3OutputStreamProvider;
import io.awspring.cloud.s3.S3OutputStreamProvider;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Autowired;
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
public class S3Service {




    private S3Template s3Template ;
    private final S3Client  s3Client;

    public S3Service(S3Client s3Client , S3Template s3Template) {
        this.s3Template = s3Template ;
        this.s3Client = s3Client;
    }


    public void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

       s3Client.createBucket(createBucketRequest);
    }
    public void createBucketWithS3Template(String bucketName) {
        s3Template.createBucket(bucketName);
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
    public void uploadObjectWithS3Template(String bucketName, String key, MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        s3Template.upload(bucketName,key , inputStream);
    }
    public void deleteObject(String bucketName, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
    public void deleteObjectWithS3Template(String bucketName, String key) {
        s3Template.deleteObject(bucketName , key);
    }
    public String readFileFromS3(String bucketName, String objectKey) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        ResponseInputStream  response = s3Client.getObject(getObjectRequest);
        return  new String(response.readAllBytes());
    }
    public S3Resource readFileFromS3WithS3Template(String bucketName, String objectKey) throws IOException {
      return s3Template.download(bucketName , objectKey);
    }
    public Resource readS3ObjectAsResource(String bucketName, String objectKey) {
        return new S3Resource( bucketName ,objectKey ,s3Client ,new InMemoryBufferingS3OutputStreamProvider(s3Client , null));
    }

}
