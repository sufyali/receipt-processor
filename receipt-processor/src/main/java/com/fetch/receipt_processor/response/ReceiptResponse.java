package com.fetch.receipt_processor.response;

import java.util.UUID;

public class ReceiptResponse {
    private UUID id;

    public ReceiptResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
