package com.example.consumer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConsumerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returns200WithContractResponse() throws Exception {
        mockMvc.perform(get("/api/v1/consumer")
                        .param("a", "5")
                        .param("b", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "status": 200,
                          "message": "SUCCESS",
                          "data": { "a": "5", "b": "10" }
                        }
                        """, true));
    }

    @Test
    void returns400WhenParamMissing() throws Exception {
        mockMvc.perform(get("/api/v1/consumer").param("a", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void contractFileIsServed() throws Exception {
        mockMvc.perform(get("/openapi/contract.yaml"))
                .andExpect(status().isOk());
    }
}
