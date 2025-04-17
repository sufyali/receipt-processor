package com.fetch.receipt_processor.service;

import com.fetch.receipt_processor.BaseTestData;
import com.fetch.receipt_processor.entity.Receipt;
import com.fetch.receipt_processor.repository.ReceiptRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class ReceiptServiceTests extends BaseTestData {
    @Mock
    private static ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    @Test
    public void testProcessReceipt() {
        UUID testReceiptID0 = receiptService.processReceipt(getTestReceipt());

        // Verify that a UUID is returned
        assertNotNull(testReceiptID0);

        // Verify that processing a receipt multiple times returns the same ID
        UUID testReceiptIDDupe = receiptService.processReceipt(getTestReceipt());
        assertEquals(testReceiptID0, testReceiptIDDupe);

        // Verify that processing a different receipt returns a new ID
        UUID testReceiptID1 = receiptService.processReceipt(getTestReceipt1());
        assertNotEquals(testReceiptID0, testReceiptID1);
    }

    @Test
    public void testGetReceiptPoints() {
        UUID randomUUID = UUID.randomUUID();
        UUID randomUUID1 = UUID.randomUUID();

        Integer randomPoints = 28;
        Integer randomPoints1 = 109;

        Mockito.when(receiptRepository.getReceiptPoints(randomUUID)).thenReturn(randomPoints);
        Mockito.when(receiptRepository.getReceiptPoints(randomUUID1)).thenReturn(randomPoints1);

        // Verify that the expected point values are returned
        assertEquals(randomPoints, receiptService.getReceiptPoints(randomUUID));
        assertEquals(randomPoints1, receiptService.getReceiptPoints(randomUUID1));
    }

    @Test
    public void testCalculateReceiptPoints() {
        int testReceiptExpectedPoints = 28;
        int testReceipt1ExpectedPoints = 109;
        Receipt testReceipt = getTestReceipt();
        Receipt testReceipt1 = getTestReceipt1();

        assertEquals(testReceiptExpectedPoints, receiptService.calculateReceiptPoints(testReceipt.getRetailer(),
                testReceipt.getPurchaseDate(), testReceipt.getPurchaseTime(), testReceipt.getItems(),
                testReceipt.getTotal()));

        assertEquals(testReceipt1ExpectedPoints, receiptService.calculateReceiptPoints(testReceipt1.getRetailer(),
                testReceipt1.getPurchaseDate(), testReceipt1.getPurchaseTime(), testReceipt1.getItems(),
                testReceipt1.getTotal()));
    }
}
