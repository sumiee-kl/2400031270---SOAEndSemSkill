package com.example.consumer.service;

import com.example.consumer.model.ConsumerData;
import com.example.consumer.model.ConsumerResponse;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    public ConsumerResponse getConsumerData(String a, String b) {
        ConsumerData data = new ConsumerData()
                .a(a)
                .b(b);

        return new ConsumerResponse()
                .status(200)
                .message("SUCCESS")
                .data(data);
    }
}
