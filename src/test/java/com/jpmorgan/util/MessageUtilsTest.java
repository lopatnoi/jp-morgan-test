package com.jpmorgan.util;

import com.jpmorgan.domain.AdjustmentSaleMessage;
import com.jpmorgan.domain.Message;
import com.jpmorgan.domain.MultiSaleMessage;
import com.jpmorgan.domain.SaleMessage;
import org.junit.After;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MessageUtilsTest {

    @Test
    public void messagePausing() {
        assertThat(MessageUtils.isMessagePausing()).isFalse();

        MessageUtils.setMessagePausing(Boolean.TRUE);
        assertThat(MessageUtils.isMessagePausing()).isTrue();
    }

    @Test
    public void createSaleMessage() {
        String product = "test";
        String value = "11.1";
        SaleMessage message = MessageUtils.createSaleMessage(product, value);

        assertThat(message).isNotNull();
        assertThat(message).extracting(Message::getValue, Message::getProduct)
                .containsExactly("11.1", "test");
    }

    @Test
    public void failNullProductSaleMessage() {
        try {
            MessageUtils.createSaleMessage(null, "1");
            fail("IllegalArgumentException expected because product is null in sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Product is null or empty.");
        }
    }

    @Test
    public void failNullValueSaleMessage() {
        try {
            MessageUtils.createSaleMessage("test", null);
            fail("IllegalArgumentException expected because value is null in sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Value is null or empty.");
        }
    }

    @Test
    public void createMultiSale() {
        String product = "test";
        String value = "11.1";
        int salesNumber = 5;
        MultiSaleMessage message = MessageUtils.createMultiSale(product, value, salesNumber);

        assertThat(message).isNotNull();
        assertThat(message).extracting(MultiSaleMessage::getSalesNumber, Message::getValue, Message::getProduct)
                .containsExactly(5, "11.1", "test");
    }

    @Test
    public void failNullProductMultiSale() {
        try {
            MessageUtils.createMultiSale(null, "1", 1);
            fail("IllegalArgumentException expected because product is null in multi sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Product is null or empty.");
        }
    }

    @Test
    public void failNullValueMultiSale() {
        try {
            MessageUtils.createMultiSale("test", null, 1);
            fail("IllegalArgumentException expected because value is null in multi sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Value is null or empty.");
        }
    }

    @Test
    public void failZeroNumberMultiSale() {
        try {
            MessageUtils.createMultiSale("test", "111", 0);
            fail("IllegalArgumentException expected because number of sales is less than one in multi sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Sales number cannot be less than one sale.");
        }
    }

    @Test
    public void addAdjustmentSale() {
        AdjustmentSaleMessage message = MessageUtils.createAdjustmentSale("test", "11.1", AdjustmentType.ADD);
        assertThat(message).extracting(AdjustmentSaleMessage::getType, Message::getValue, Message::getProduct)
                .containsExactly(AdjustmentType.ADD, "11.1", "test");
    }

    @Test
    public void subtractAdjustmentSale() {
        AdjustmentSaleMessage message = MessageUtils.createAdjustmentSale("test", "22", AdjustmentType.SUBTRACT);
        assertThat(message).extracting(AdjustmentSaleMessage::getType, Message::getValue, Message::getProduct)
                .containsExactly(AdjustmentType.SUBTRACT, "22", "test");
    }

    @Test
    public void multiplyAdjustmentSale() {
        AdjustmentSaleMessage message = MessageUtils.createAdjustmentSale("test", "33.33", AdjustmentType.MULTIPLY);
        assertThat(message).extracting(AdjustmentSaleMessage::getType, Message::getValue, Message::getProduct)
                .containsExactly(AdjustmentType.MULTIPLY, "33.33", "test");
    }

    @Test
    public void failNullProductAdjustmentSale() {
        try {
            MessageUtils.createAdjustmentSale(null, "1", AdjustmentType.MULTIPLY);
            fail("IllegalArgumentException expected because product is null in adjustment sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Product is null or empty.");
        }
    }

    @Test
    public void failNullValueAdjustmentSale() {
        try {
            MessageUtils.createAdjustmentSale("test", null, AdjustmentType.SUBTRACT);
            fail("IllegalArgumentException expected because value is null in adjustment sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Value is null or empty.");
        }
    }

    @Test
    public void failNullTypeAdjustmentSale() {
        try {
            MessageUtils.createAdjustmentSale("test", "1", null);
            fail("IllegalArgumentException expected because type is null in adjustment sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Adjustment type is null.");
        }
    }

    @After
    public void close() {
        MessageUtils.setMessagePausing(Boolean.FALSE);
    }
}
