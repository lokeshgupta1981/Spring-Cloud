package com.example.springawssqsexample.config;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementResultCallback;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;

@Configuration
public class SqsConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(SqsConfig.class);

  @Value("${spring.cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${spring.cloud.aws.credentials.secret-key}")
  private String secretKey;

  @Value("${spring.cloud.aws.region.static}")
  private String region;

    /*
    @Bean
    SqsAsyncClient sqsAsyncClient(){
        return SqsAsyncClient
                .builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        // add more Options
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient){
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient).build();
    }*/

  @Bean
  SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(
      SqsAsyncClient sqsAsyncClient) {
    return SqsMessageListenerContainerFactory.builder()
        .configure(options -> options.acknowledgementMode(AcknowledgementMode.MANUAL)
            .acknowledgementInterval(
                Duration.ofSeconds(3)) // NOTE: With acknowledgementInterval 3 seconds,
            .acknowledgementThreshold(0)
        )
        .acknowledgementResultCallback(new AckResultCallback()).sqsAsyncClient(sqsAsyncClient)
        .build();
  }


  static class AckResultCallback implements AcknowledgementResultCallback<Object> {

    @Override
    public void onSuccess(Collection<Message<Object>> messages) {
      LOGGER.info("Ack with success at {}", OffsetDateTime.now());
    }

    @Override
    public void onFailure(Collection<Message<Object>> messages, Throwable t) {
      LOGGER.error("Ack with fail", t);
    }
  }
}
