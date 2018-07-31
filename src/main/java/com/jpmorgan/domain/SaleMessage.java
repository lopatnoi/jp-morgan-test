package com.jpmorgan.domain;

/**
 * Simple sale message which contains the details of 1 sale E.g apple at 10p
 */
public class SaleMessage extends Message {

    @Override
    public String toString() {
        return "SaleMessage {" + "product=" + getProduct() + ", value=" + getValue() + '}';
    }

}
