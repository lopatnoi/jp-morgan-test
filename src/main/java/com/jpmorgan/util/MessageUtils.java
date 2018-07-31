package com.jpmorgan.util;

import com.jpmorgan.domain.AdjustmentSaleMessage;
import com.jpmorgan.domain.MultiSaleMessage;
import com.jpmorgan.domain.SaleMessage;

import java.util.concurrent.atomic.AtomicBoolean;

public final class MessageUtils {

    private static AtomicBoolean messagePausing = new AtomicBoolean(Boolean.FALSE);

    public static boolean isMessagePausing() {
        return messagePausing.get();
    }

    public static void setMessagePausing(boolean pause) {
        messagePausing.set(pause);
    }

    public static SaleMessage createSaleMessage(String product, String value) {
        if (product == null || product.isEmpty()) throw new IllegalArgumentException("Product is null or empty.");
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Value is null or empty.");

        return SaleBuilder.builder()
                .product(product)
                .value(value)
                .build();
    }

    public static MultiSaleMessage createMultiSale(String product, String value, int salesNumber) {
        if (product == null || product.isEmpty()) throw new IllegalArgumentException("Product is null or empty.");
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Value is null or empty.");
        if (salesNumber < 1) throw new IllegalArgumentException("Sales number cannot be less than one sale.");

        return MultiSaleBuilder.builder()
                .product(product)
                .value(value)
                .count(salesNumber)
                .build();
    }

    public static AdjustmentSaleMessage createAdjustmentSale(String product, String value, AdjustmentType type) {
        if (product == null || product.isEmpty()) throw new IllegalArgumentException("Product is null or empty.");
        if (value == null || value.isEmpty()) throw new IllegalArgumentException("Value is null or empty.");
        if (type == null) throw new IllegalArgumentException("Adjustment type is null.");

        return AdjustmentSaleBuilder.builder()
                .product(product)
                .value(value)
                .type(type)
                .build();
    }

    private static class SaleBuilder {

        private SaleMessage saleMessage;

        private SaleBuilder() {
            saleMessage = new SaleMessage();
        }

        static SaleBuilder builder() {
            return new SaleBuilder();
        }

        SaleBuilder product(String product) {
            saleMessage.setProduct(product);
            return this;
        }

        SaleBuilder value(String value) {
            saleMessage.setValue(value);
            return this;
        }

        SaleMessage build() {
            return saleMessage;
        }
    }

    private static class MultiSaleBuilder {

        private MultiSaleMessage multiSaleMessage;

        private MultiSaleBuilder() {
            multiSaleMessage = new MultiSaleMessage();
        }

        static MultiSaleBuilder builder() {
            return new MultiSaleBuilder();
        }

        MultiSaleBuilder product(String product) {
            multiSaleMessage.setProduct(product);
            return this;
        }

        MultiSaleBuilder value(String value) {
            multiSaleMessage.setValue(value);
            return this;
        }

        MultiSaleBuilder count(int salesNumber) {
            multiSaleMessage.setSalesNumber(salesNumber);
            return this;
        }

        MultiSaleMessage build() {
            return multiSaleMessage;
        }
    }

    private static class AdjustmentSaleBuilder {

        private AdjustmentSaleMessage adjSale;

        private AdjustmentSaleBuilder() {
            adjSale = new AdjustmentSaleMessage();
        }

        static AdjustmentSaleBuilder builder() {
            return new AdjustmentSaleBuilder();
        }

        AdjustmentSaleBuilder product(String product) {
            adjSale.setProduct(product);
            return this;
        }

        AdjustmentSaleBuilder value(String value) {
            adjSale.setValue(value);
            return this;
        }

        AdjustmentSaleBuilder type(AdjustmentType type) {
            adjSale.setType(type);
            return this;
        }

        AdjustmentSaleMessage build() {
            return adjSale;
        }
    }
}
