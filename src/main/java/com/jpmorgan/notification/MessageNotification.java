package com.jpmorgan.notification;

import com.jpmorgan.util.AdjustmentType;

/**
 * Message notification interface.
 */
public interface MessageNotification {

    /**
     * Adds message, which is Message Type 1 in the task.
     *
     * @param product product type name
     * @param value   sale value
     */
    void addSale(String product, String value);

    /**
     * Adds message, which is Message Type 2 in the task.
     *
     * @param product     product type name
     * @param value       sale value
     * @param salesNumber the number of occurrences of that sale
     */
    void addMultiSale(String product, String value, int salesNumber);

    /**
     * Add message, which is Message Type 3 in the task.
     *
     * @param product product type name
     * @param value   sale value
     * @param type    adjustment operation to be applied to all stored sales of this product type
     */
    void addAdjustment(String product, String value, AdjustmentType type);
}
