package com.fetch.receipt_processor.controller;

import com.fetch.receipt_processor.entity.Receipt;
import com.fetch.receipt_processor.response.PointResponse;
import com.fetch.receipt_processor.response.ReceiptResponse;
import com.fetch.receipt_processor.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    /**
     * Endpoint for processing and storing a given receipt
     * @param receipt Receipt object to be processed
     * @return ID object with the id of the processed receipt
     */
    @PostMapping("process")
    public ResponseEntity<ReceiptResponse> processReceipt(@Valid @RequestBody Receipt receipt) {
        UUID receiptId = receiptService.processReceipt(receipt);

        return ResponseEntity.ok(new ReceiptResponse(receiptId));
    }

    /**
     * Endpoint for retrieving the points of a given receipt ID
     * @param id The receipt id
     * @return The points of the receipt corresponding to the given receipt ID
     */
    @GetMapping("{id}/points")
    public ResponseEntity<PointResponse> getPoints(@PathVariable UUID id) {
        return ResponseEntity.ok(new PointResponse(receiptService.getReceiptPoints(id)));
    }
}
