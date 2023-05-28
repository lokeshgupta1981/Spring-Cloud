package com.example.springcloudawss3.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.s3.InMemoryBufferingS3OutputStreamProvider;
import io.awspring.cloud.s3.Jackson2JsonS3ObjectConverter;
import io.awspring.cloud.s3.S3Template;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class Config {


    @Bean
    public S3Client s3Client(){
       return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("AKIAVSFK53MGYEY4P4Y3", "HW7FS0o2H4S06fEoXwbZGbaqXhtGfFk8tPHa7zgh"))).build();
    }
    @Bean
    public S3Template s3Template(){
        return new S3Template(s3Client() , new InMemoryBufferingS3OutputStreamProvider(s3Client() , null) ,new Jackson2JsonS3ObjectConverter(new ObjectMapper()), S3Presigner.create());
    }

}
