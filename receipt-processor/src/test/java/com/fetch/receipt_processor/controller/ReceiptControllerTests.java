package com.fetch.receipt_processor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetch.receipt_processor.BaseTestData;
import com.fetch.receipt_processor.entity.Receipt;
import com.fetch.receipt_processor.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@AutoConfigureMockMvc
@WebMvcTest(ReceiptController.class)
public class ReceiptControllerTests extends BaseTestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReceiptService receiptService;

    @InjectMocks
    private ReceiptController receiptController;

    @Test
    public void testProcessReceipt_success() throws Exception {
        String receiptJson = objectMapper.writeValueAsString(getTestReceipt());
        UUID testUUID = UUID.randomUUID();
        Mockito.when(receiptService.processReceipt(any(Receipt.class))).thenReturn(testUUID);

        mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                .content(receiptJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(testUUID.toString()));
    }

    @Test
    public void testProcessReceipt_badRequest() throws Exception {
        Receipt testReceipt = getTestReceipt();
        testReceipt.setItems(null);
        String receiptJson = objectMapper.writeValueAsString(getTestReceipt());

        mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .content(receiptJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetPoints_success() throws Exception {
        UUID testUUID = UUID.randomUUID();
        int testPoints = 100;

        Mockito.when(receiptService.getReceiptPoints(any(UUID.class))).thenReturn(testPoints);

        mockMvc.perform(MockMvcRequestBuilders.get("/receipts/{id}/points", testUUID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("points").value(testPoints));
    }

    @Test
    public void testGetPoints_notFound() throws Exception {
        UUID testUUID = UUID.randomUUID();

        Mockito.when(receiptService.getReceiptPoints(any(UUID.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.get("/receipts/{id}/points", testUUID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
