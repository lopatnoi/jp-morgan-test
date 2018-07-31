package com.jpmorgan.notification;

import com.jpmorgan.process.MessageProcess;
import com.jpmorgan.domain.AdjustmentSaleMessage;
import com.jpmorgan.domain.MultiSaleMessage;
import com.jpmorgan.domain.SaleMessage;
import com.jpmorgan.util.AdjustmentType;
import com.jpmorgan.util.MessageUtils;

/**
 * Implementation of {@link MessageNotification} interface.
 */
public class MessageNotificationImpl implements MessageNotification {

    private final MessageProcess messageProcess;

    public MessageNotificationImpl(MessageProcess messageProcess) {
        this.messageProcess = messageProcess;
    }

    @Override
    public void addSale(String product, String value) {
        SaleMessage msg = MessageUtils.createSaleMessage(product, value);
        messageProcess.process(msg);
    }

    @Override
    public void addMultiSale(String product, String value, int salesNumber) {
        MultiSaleMessage msg = MessageUtils.createMultiSale(product, value, salesNumber);
        messageProcess.process(msg);
    }

    @Override
    public void addAdjustment(String product, String value, AdjustmentType type) {
        AdjustmentSaleMessage msg = MessageUtils.createAdjustmentSale(product, value, type);
        messageProcess.process(msg);
    }
}
