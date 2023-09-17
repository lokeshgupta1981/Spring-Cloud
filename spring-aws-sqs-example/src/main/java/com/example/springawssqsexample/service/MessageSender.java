package com.example.springawssqsexample.service;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @Autowired
    private SqsTemplate sqsTemplate;


    public void sendMessage(String message) {

        sqsTemplate
                .send(sqsSendOptions ->
                        sqsSendOptions
                                .queue("HowToDoInJava")
                                .payload(message)
                );
    }


}
