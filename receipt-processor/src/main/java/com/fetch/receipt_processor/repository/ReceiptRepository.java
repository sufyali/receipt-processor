package com.fetch.receipt_processor.repository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class ReceiptRepository {
    private final HashMap<UUID, Integer> storedReceipts;

    public ReceiptRepository () {
        this.storedReceipts = new HashMap<>();
    }

    /**
     * Adds the receipt id and its corresponding point value to the database
     * @param receiptId ID of the receipt
     * @param points Point value of the given receipt
     */
    public void addReceiptID(UUID receiptId, int points) {
        storedReceipts.put(receiptId, points);
    }

    /**
     * Fetches point value for the given receipt id
     * @param receiptID ID of the receipt
     * @return Point value of the receipt id. Throws 404 if the id isn't present
     */
    public Integer getReceiptPoints(UUID receiptID) {
        if (!storedReceipts.containsKey(receiptID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt id not found");
        }

        return storedReceipts.get(receiptID);
    }
}
