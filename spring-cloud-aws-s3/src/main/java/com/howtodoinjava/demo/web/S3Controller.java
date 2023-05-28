package com.howtodoinjava.demo.web;

import com.howtodoinjava.demo.service.S3ClientService;
import com.howtodoinjava.demo.service.S3TemplateService;
import io.awspring.cloud.s3.S3Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3ClientService s3ClientService;

    private final S3TemplateService s3TemplateService ;

    @Autowired
    public S3Controller(S3ClientService s3ClientService, S3TemplateService s3TemplateService) {
        this.s3ClientService = s3ClientService;
        this.s3TemplateService = s3TemplateService;
    }
    // tested
    @PostMapping("/bucket")
    public ResponseEntity<String> createBucket(
                                             @RequestParam("bucketName") String bucketName )
    {
        try {
            // s3ClientService.createBucket(bucketName); with S3Client
            s3TemplateService.createBucketWithS3Template(bucketName); // with S3Template
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
            // s3ClientService.uploadObject(bucketName, key, file);  with S3Clienr
            s3TemplateService.uploadObjectWithS3Template(bucketName ,key , file);
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
            s3TemplateService.deleteObjectWithS3Template(bucketName , key);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete file: " + e.getMessage());
        }
    }

    @GetMapping("/read")
    public ResponseEntity<S3Resource> readFileFromS3(@RequestParam("bucketName") String bucketName,
                                                     @RequestParam("key") String key) throws IOException {
       //  s3ClientService.readFileFromS3(bucketName, key); with S3Client
        return ResponseEntity.ok().body( s3TemplateService.readFileFromS3WithS3Template(bucketName ,key));
    }

    @GetMapping("/resource")
    public ResponseEntity<Resource> readS3ObjectAsResource(@RequestParam("bucketName") String bucketName,
                                                           @RequestParam("key") String key) throws IOException {
        return ResponseEntity.ok().body( s3TemplateService.readFileFromS3WithS3Template(bucketName ,key));
    }

    @GetMapping("/test")
    public ResponseEntity<String> getResource() throws IOException {
        return ResponseEntity.ok().body( s3ClientService.getResource());
    }




    // Add more endpoints for additional operations as needed

}
