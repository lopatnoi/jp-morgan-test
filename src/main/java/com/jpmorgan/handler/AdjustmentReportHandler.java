package com.jpmorgan.handler;

import com.jpmorgan.domain.AdjustmentSaleMessage;
import com.jpmorgan.domain.Message;
import com.jpmorgan.util.MessageUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Implementation of {@link ReportHandler} interface.
 * <br>
 * <b>Report requirements.</b>
 * <br>
 * The report should log that it is pausing, stop accepting new messages
 * and log a report of the adjustments that have been made to each sale type while the application was running.
 */
public class AdjustmentReportHandler implements ReportHandler {

    private Map<String, List<AdjustmentSaleMessage>> report = new HashMap<>();

    private Consumer<AdjustmentSaleMessage> consumer = message -> {
        String product = message.getProduct();

        if (!report.containsKey(product)) {
            report.put(product, new LinkedList<>());
        }
        report.get(product).add(message);
    };

    @Override
    public <T extends Message> void handle(Stream<T> messages) {
        MessageUtils.setMessagePausing(Boolean.TRUE);
        System.out.println("Stopped the application to receive messages.");

        messages.filter(message -> message instanceof AdjustmentSaleMessage)
                .map(message -> (AdjustmentSaleMessage) message)
                .forEach(consumer);

        report.forEach((product, productMessages) -> {
            System.out.println("product: " + product);
            productMessages.forEach(
                    message -> System.out.printf("\t%8s %s%n", message.getType(), message.getValue())
            );
        });

        System.out.println("Continue the application to receive messages." + System.lineSeparator());
        MessageUtils.setMessagePausing(Boolean.FALSE);
    }
}
