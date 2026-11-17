package com.example.consumer.controller;

import com.example.consumer.api.ConsumerApi;
import com.example.consumer.model.ConsumerResponse;
import com.example.consumer.service.ConsumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements the interface generated from contract.yaml.
 * Path, query parameters and response type are all dictated by the contract,
 * so the implementation cannot drift from it without breaking the build.
 */
@RestController
public class ConsumerController implements ConsumerApi {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Override
    public ResponseEntity<ConsumerResponse> getConsumerData(String a, String b) {
        return ResponseEntity.ok(consumerService.getConsumerData(a, b));
    }
}
