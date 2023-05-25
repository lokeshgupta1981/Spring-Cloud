package com.example.springcloudawss3.config;

import io.awspring.cloud.s3.S3Template;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class Config {


    @Bean
    public S3Client s3Client(){
       return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("AKIA22RC3YCYGDEOA5WJ", "LFVrtjND1FKnZqVCK8CwBziBdpEpc3QOsUQWtRJI"))).build();
    }

}
