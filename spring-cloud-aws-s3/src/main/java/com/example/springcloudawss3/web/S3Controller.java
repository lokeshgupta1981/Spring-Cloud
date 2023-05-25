package com.example.springcloudawss3.web;

import com.example.springcloudawss3.service.S3Service;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.internal.resource.S3BucketResource;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    @Autowired
    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }
    // tested
    @PostMapping("/bucket")
    public ResponseEntity<String> createBucket(
                                             @RequestParam("bucketName") String bucketName )
    {
        try {
            s3Service.createBucket(bucketName);
            return ResponseEntity.ok("bucket created successfully .");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create bucket with main reason: " + e.getMessage());
        }
    }
    // tested
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("bucketName") String bucketName,
                                             @RequestParam("key") String key) {
        try {
            s3Service.uploadObject(bucketName, key, file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }

    // tested
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("bucketName") String bucketName,
                                             @RequestParam("key") String key) {
        try {
            s3Service.deleteObject(bucketName, key);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete file: " + e.getMessage());
        }
    }

    // Add more endpoints for additional operations as needed

}
