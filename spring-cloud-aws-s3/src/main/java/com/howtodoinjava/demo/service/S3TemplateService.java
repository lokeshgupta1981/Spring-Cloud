package com.howtodoinjava.demo.service;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3TemplateService {


    private S3Template s3Template ;

    public S3TemplateService(S3Template s3Template) {
        this.s3Template = s3Template;
    }

    public S3Resource readFileFromS3WithS3Template(String bucketName, String objectKey) {
        return s3Template.download(bucketName , objectKey);
    }

    public void uploadObjectWithS3Template(String bucketName, String key, MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        s3Template.upload(bucketName,key , inputStream);
    }
    public void deleteObjectWithS3Template(String bucketName, String key) {
        s3Template.deleteObject(bucketName , key);
    }

    public void createBucketWithS3Template(String bucketName) {
        s3Template.createBucket(bucketName);
    }



}
