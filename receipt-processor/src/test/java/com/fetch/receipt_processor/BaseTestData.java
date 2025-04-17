package com.fetch.receipt_processor;

import com.fetch.receipt_processor.entity.Item;
import com.fetch.receipt_processor.entity.Receipt;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BaseTestData {

    private static Receipt testReceipt;
    private static Receipt testReceipt1;

    @BeforeEach
    void setup() {
        String testRetailer = "Target";
        LocalDate testDate = LocalDate.of(2022, 1, 1);
        LocalTime testTime = LocalTime.of(13, 1);
        List<Item> testItems = new ArrayList<>();
        String testTotal = "35.35";

        String testItem0Desc = "Mountain Dew 12PK";
        String testItem0Price = "6.49";
        Item testItem0 = new Item(testItem0Desc, testItem0Price);


        String testItem1Desc = "Emils Cheese Pizza";
        String testItem1Price = "12.25";
        Item testItem1 = new Item(testItem1Desc, testItem1Price);

        String testItem2Desc = "Knorr Creamy Chicken";
        String testItem2Price = "1.26";
        Item testItem2 = new Item(testItem2Desc, testItem2Price);


        String testItem3Desc = "Doritos Nacho Cheese";
        String testItem3Price = "3.35";
        Item testItem3 = new Item(testItem3Desc, testItem3Price);

        String testItem4Desc = "   Klarbrunn 12-PK 12 FL OZ  ";
        String testItem4Price = "12.00";
        Item testItem4 = new Item(testItem4Desc, testItem4Price);

        testItems.add(testItem0);
        testItems.add(testItem1);
        testItems.add(testItem2);
        testItems.add(testItem3);
        testItems.add(testItem4);

        testReceipt = new Receipt(testRetailer, testDate, testTime, testItems, testTotal);

        // Second receipt objects
        String testRetailer1 = "M&M Corner Market";
        LocalDate testDate1 = LocalDate.of(2022, 3, 20);
        LocalTime testTime1 = LocalTime.of(14, 33);
        List<Item> testItems1 = new ArrayList<>();
        String testTotal1 = "9.00";

        String testItem5Desc = "Gatorade";
        String testItem5Price = "2.25";
        Item testItem5 = new Item(testItem5Desc, testItem5Price);

        testItems1.add(testItem5);
        testItems1.add(testItem5);
        testItems1.add(testItem5);
        testItems1.add(testItem5);

        testReceipt1 =new Receipt(testRetailer1, testDate1, testTime1, testItems1, testTotal1);
    }

    public static Receipt getTestReceipt() {
        return testReceipt;
    }

    public static Receipt getTestReceipt1() {
        return testReceipt1;
    }
}
