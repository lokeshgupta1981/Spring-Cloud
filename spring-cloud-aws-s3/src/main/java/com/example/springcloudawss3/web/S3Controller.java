package com.example.springcloudawss3.web;

import com.example.springcloudawss3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
            // s3Service.createBucket(bucketName); with S3Client
            s3Service.createBucketWithS3Template(bucketName); // with S3Template
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
            // s3Service.uploadObject(bucketName, key, file);  with S3Clienr
            s3Service.uploadObjectWithS3Template(bucketName ,key , file);
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
            // s3Service.deleteObject(bucketName, key); with S3Client
            s3Service.deleteObjectWithS3Template(bucketName , key);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete file: " + e.getMessage());
        }
    }

    @GetMapping("/read")
    public ResponseEntity<String> readFileFromS3(@RequestParam("bucketName") String bucketName,
                                             @RequestParam("key") String key) throws IOException {
        return ResponseEntity.ok().body( s3Service.readFileFromS3(bucketName ,key));
    }

    @GetMapping("/resource")
    public ResponseEntity<Resource> readS3ObjectAsResource(@RequestParam("bucketName") String bucketName,
                                                           @RequestParam("key") String key) throws IOException {
        return ResponseEntity.ok().body( s3Service.readFileFromS3WithS3Template(bucketName ,key));
    }



    // Add more endpoints for additional operations as needed

}
