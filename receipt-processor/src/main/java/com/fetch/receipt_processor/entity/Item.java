package com.fetch.receipt_processor.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Item {
    private final String INVALID_INPUT_MESSAGE = "Invalid input entered";

    @NotBlank(message = "An item description is required")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = INVALID_INPUT_MESSAGE)
    private String shortDescription;

    @NotBlank(message = "An item price is required")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = INVALID_INPUT_MESSAGE)
    private String price;

    public Item(String shortDescription, String price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }

    public String getDescription() {
        return shortDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.shortDescription = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
