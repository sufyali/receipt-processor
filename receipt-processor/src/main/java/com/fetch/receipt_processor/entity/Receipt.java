package com.fetch.receipt_processor.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Receipt {
    private final String INVALID_INPUT_MESSAGE = "Invalid input entered";
    @NotBlank(message = "A retailer is required")
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = INVALID_INPUT_MESSAGE)
    private String retailer;

    @NotNull(message = "A purchase date is required")
    @DateTimeFormat
    private LocalDate purchaseDate;

    @NotNull(message = "A purchase time is required")
    @DateTimeFormat
    private LocalTime purchaseTime;

    @NotEmpty(message = "Purchased items are required")
    @Valid
    private List<Item> items;

    @NotBlank(message = "The total price is required")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = INVALID_INPUT_MESSAGE)
    private String total;

    public Receipt (String retailer, LocalDate purchaseDate, LocalTime purchaseTime, List<Item> items, String total) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.total = total;
    }

    public String getRetailer() {
        return retailer;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalTime getPurchaseTime() {
        return purchaseTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getTotal() {
        return total;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setPurchaseTime(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
