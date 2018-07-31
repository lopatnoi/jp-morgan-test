package com.jpmorgan.notification;

import com.jpmorgan.process.MessageProcess;
import com.jpmorgan.util.AdjustmentType;
import com.jpmorgan.util.MessageUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class MessageNotificationImplTest {

    @Mock
    private MessageProcess messageProcess;

    private MessageNotificationImpl messageNotification;

    @Before
    public void setup() {
        messageNotification = new MessageNotificationImpl(messageProcess);
    }

    @Test
    public void addSale() {
        doNothing().when(messageProcess).process(any());

        messageNotification.addSale("test", "11.1");
        assertThat(messageNotification).isNotNull();
    }

    @Test
    public void failEmptyProductSaleMessage() {
        try {
            messageNotification.addSale("", "11.1");
            fail("IllegalArgumentException expected because product is empty in sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Product is null or empty.");
        }
    }

    @Test
    public void failEmptyValueSaleMessage() {
        try {
            messageNotification.addSale("test", "");
            fail("IllegalArgumentException expected because value is empty in sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Value is null or empty.");
        }
    }

    @Test
    public void addMultiSale() {
        doNothing().when(messageProcess).process(any());

        messageNotification.addMultiSale("test", "11.1", 11);
        assertThat(messageNotification).isNotNull();
    }

    @Test
    public void failEmptyProductMultiSale() {
        try {
            messageNotification.addMultiSale("", "1", 111);
            fail("IllegalArgumentException expected because product is empty in multi sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Product is null or empty.");
        }
    }

    @Test
    public void failEmptyValueMultiSale() {
        try {
            messageNotification.addMultiSale("test", "", 2);
            fail("IllegalArgumentException expected because value is empty in multi sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Value is null or empty.");
        }
    }

    @Test
    public void failLessOneNumberMultiSale() {
        try {
            messageNotification.addMultiSale("test", "111", -1);
            fail("IllegalArgumentException expected because number of sales is less than one in multi sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Sales number cannot be less than one sale.");
        }
    }

    @Test
    public void addAdjustmentSale() {
        doNothing().when(messageProcess).process(any());

        messageNotification.addAdjustment("test", "11.1", AdjustmentType.ADD);
        assertThat(messageNotification).isNotNull();
    }

    @Test
    public void failEmptyProductAdjustmentSale() {
        try {
            MessageUtils.createAdjustmentSale("", "1111", AdjustmentType.ADD);
            fail("IllegalArgumentException expected because product is empty in adjustment sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Product is null or empty.");
        }
    }

    @Test
    public void failEmptyValueAdjustmentSale() {
        try {
            messageNotification.addAdjustment("test", "", AdjustmentType.ADD);
            fail("IllegalArgumentException expected because value is empty in adjustment sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Value is null or empty.");
        }
    }

    @Test
    public void failNullTypeAdjustmentSale() {
        try {
            messageNotification.addAdjustment("test", "1", null);
            fail("IllegalArgumentException expected because type is null in adjustment sale message.");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("Adjustment type is null.");
        }
    }
}
