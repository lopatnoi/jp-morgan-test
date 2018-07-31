package com.jpmorgan.domain;

import com.jpmorgan.util.AdjustmentType;

import java.util.Objects;

/**
 * Sale message which contains the details of a sale and an adjustment operation to be
 * applied to all stored sales of this product type. <br>
 * Operations can be add, subtract, or multiply e.g.
 * Add 20p apples would instruct your application to add 20p to each sale of apples you have recorded.
 */
public class AdjustmentSaleMessage extends Message {

    private AdjustmentType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdjustmentSaleMessage that = (AdjustmentSaleMessage) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }

    public AdjustmentType getType() {
        return type;
    }

    public void setType(AdjustmentType type) {
        this.type = type;
    }
}
