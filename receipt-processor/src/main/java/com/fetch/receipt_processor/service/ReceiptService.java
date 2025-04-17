package com.fetch.receipt_processor.service;

import com.fetch.receipt_processor.entity.Item;
import com.fetch.receipt_processor.entity.Receipt;
import com.fetch.receipt_processor.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReceiptService {
    private final int ROUND_DOLLAR_BONUS = 50;
    private final int QUARTER_MULTIPLE_BONUS = 25;
    private final int TWO_ITEM_BONUS = 5;
    private final float DESC_LENGTH_MULTIPLIER = 0.2f;
    private final int ODD_PURCHASE_DATE_BONUS = 6;
    private final int BONUS_PURCHASE_TIME_BONUS = 10;
    private final int BONUS_START_HOUR = 14;
    private final int BONUS_END_HOUR = 16;

    @Autowired
    private ReceiptRepository receiptRepository;

    /**
     * Processes the given receipt, storing its point value and returning the receipt's id
     * @param receipt The receipt object to be processed
     * @return A UUID for the newly processed receipt
     */
    public UUID processReceipt(Receipt receipt) {
        String retailer = receipt.getRetailer();
        LocalDate purchaseDate = receipt.getPurchaseDate();
        LocalTime purchaseTime = receipt.getPurchaseTime();
        List<Item> items = receipt.getItems();
        String total = receipt.getTotal();

        UUID receiptID = generateReceiptID(retailer, purchaseDate, purchaseTime, items.size(), total);
        int score = calculateReceiptPoints(retailer,purchaseDate, purchaseTime, items, total);

        receiptRepository.addReceiptID(receiptID, score);

        return receiptID;
    }

    /**
     * Get the points for a given receipt id
     * @param receiptID The receipt object ID
     * @return The points of the receipt corresponding to the given receipt ID
     */
    public int getReceiptPoints(UUID receiptID) {
        return receiptRepository.getReceiptPoints(receiptID);
    }

    /**
     * Calculates the points of a receipt
     * @param retailer Receipt retailer
     * @param purchaseDate Purchase date of the receipt
     * @param purchaseTime Purchase time of the receipt
     * @param items Items purchased
     * @param total Total amount of the receipt
     * @return Total points of the receipt
     */
    protected int calculateReceiptPoints(String retailer, LocalDate purchaseDate, LocalTime purchaseTime,
                                       List<Item> items, String total) {
        int score = 0;
        float totalAsFloat = Float.parseFloat(total);

        // Adds points for each alphanumeric character in the retailer name
        score += calculateAlphanumericLength(retailer);

        // Add 50 points if the total is a round dollar value
        if (totalAsFloat >= 1.0 && totalAsFloat % 1.0 == 0) {
            score += ROUND_DOLLAR_BONUS;
        }

        // Add 25 points if the total is a multiple of 0.25
        if (totalAsFloat % 0.25 == 0) {
            score += QUARTER_MULTIPLE_BONUS;
        }

        // Add 5 points for every two items in the receipt
        score += (items.size() / 2) * TWO_ITEM_BONUS;

        // Add points for each item where the trimmed length of the item description is a multiple of 3
        for (Item item : items) {
            int trimmedDescLength = item.getDescription().trim().length();

            if (trimmedDescLength % 3 == 0) {
                int lengthBonus = (int) Math.ceil(Float.parseFloat(item.getPrice()) * DESC_LENGTH_MULTIPLIER);
                score += lengthBonus;
            }
        }

        // Add 6 points if the purchase date is odd
        int purchaseDay = Integer.parseInt(purchaseDate.toString().substring(purchaseDate.toString().length() - 2));

        if (purchaseDay % 2 == 1) {
            score += ODD_PURCHASE_DATE_BONUS;
        }

        // Add 10 points if the purchase time is between 2pm and 4pm
        if (isWithinBonusPurchaseTime(purchaseTime.toString())) {
            score += BONUS_PURCHASE_TIME_BONUS;
        }

        return score;
    }

    /**
     * Generates an ID for the receipt based on the receipts retailer, purchase date, purchase time, and purchase total
     * @param retailer Receipt retailer
     * @param purchaseDate Purchase date of the receipt
     * @param purchaseTime Purchase time of the receipt
     * @param total Total amount of the receipt
     * @return UUID representing the receipt's ID
     */

    private UUID generateReceiptID(String retailer, LocalDate purchaseDate, LocalTime purchaseTime, int itemCount,
                                   String total) {
        String receiptIDString = retailer + purchaseDate + purchaseTime + itemCount + total;
        return UUID.nameUUIDFromBytes(receiptIDString.getBytes());
    }

    /**
     * Calculates and returns the alphanumeric length of the given string
     * @param s String to calculate alphanumeric length for
     * @return The alphanumeric length of the string
     */
    private int calculateAlphanumericLength(String s) {
        int alphanumericLength = 0;

        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                alphanumericLength++;
            }
        }

        return alphanumericLength;
    }

    /**
     * Determines if the time of purchase is within the bonus time period
     * @param purchaseTime String representation of the purchase time. Ex: "13:01"
     * @return True if purchase time is between the bonus time period
     */
    private boolean isWithinBonusPurchaseTime(String purchaseTime) {
        int hourEndPosition = purchaseTime.indexOf(":");

        int purchaseHourAsInt = Integer.parseInt(purchaseTime.substring(0, hourEndPosition));
        int purchaseMinuteAsInt = Integer.parseInt(purchaseTime.substring(hourEndPosition + 1));

        if (purchaseHourAsInt >= BONUS_START_HOUR && purchaseHourAsInt < BONUS_END_HOUR) {
            if (purchaseHourAsInt == BONUS_START_HOUR) {
                return purchaseMinuteAsInt != 0;
            }

            return true;
        }

        return false;
    }
}
