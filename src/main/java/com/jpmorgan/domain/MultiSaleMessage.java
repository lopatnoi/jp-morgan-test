package com.jpmorgan.domain;

import java.util.Objects;

/**
 * Sale message which contains the details of a sale and the number of occurrences of
 * that sale. E.g 20 sales of apples at 10p each.
 */
public class MultiSaleMessage extends Message {

    private int salesNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MultiSaleMessage that = (MultiSaleMessage) o;
        return salesNumber == that.salesNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salesNumber);
    }

    public int getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(int salesNumber) {
        this.salesNumber = salesNumber;
    }
}
