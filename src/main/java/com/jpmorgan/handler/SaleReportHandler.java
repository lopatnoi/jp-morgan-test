package com.jpmorgan.handler;

import com.jpmorgan.domain.AdjustmentSaleMessage;
import com.jpmorgan.domain.Message;
import com.jpmorgan.domain.MultiSaleMessage;
import com.jpmorgan.domain.SaleMessage;
import com.jpmorgan.util.AdjustmentType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * Implementation of {@link ReportHandler} interface.
 * <br>
 * <b>Report requirements.</b>
 * <br>
 * The report should log the number of sales of each product and their total value.
 */
public class SaleReportHandler implements ReportHandler {

    private Map<String, SaleReport> report = new HashMap<>();

    private BiConsumer<Map<String, List<Message>>, Message> accumulator = (map, message) -> {
        String product = message.getProduct();

        // sort messages by product
        if (!map.containsKey(product)) {
            map.put(product, new LinkedList<>());
        }
        map.get(product).add(message);

        // save new product
        if (!report.containsKey(product)) {
            report.put(product, new SaleReport());
        }
    };

    @Override
    public <T extends Message> void handle(Stream<T> messages) {
        Map<String, List<Message>> map = messages.collect(HashMap::new, accumulator, Map::putAll);

        map.forEach((product, productMessages) -> { // handle each message by product
            SaleReport saleReport = report.get(product);
            AtomicInteger totalSalesNumber = saleReport.getTotalSalesNumber();
            AtomicReference<Double> totalValue = saleReport.getTotalValue();

            productMessages.forEach(msg -> {
                if (msg instanceof SaleMessage) { // handle simple sale
                    SaleMessage message = (SaleMessage) msg;
                    handleSaleMessage(message, totalValue, totalSalesNumber);
                }

                if (msg instanceof MultiSaleMessage) { // handle multi sale
                    MultiSaleMessage message = (MultiSaleMessage) msg;
                    handleMultiSaleMessage(message, totalValue, totalSalesNumber);
                }

                if (msg instanceof AdjustmentSaleMessage) { // handle adjustment operations
                    AdjustmentSaleMessage message = (AdjustmentSaleMessage) msg;
                    handleAdjustmentSaleMessage(message, totalValue, totalSalesNumber);
                }
            });
        });

        report.forEach( // print report by each product
                (product, report) ->
                        System.out.printf("%d %s %.2f%n", report.getTotalSalesNumber().get(), product, report.getTotalValue().get())
        );
    }

    private void handleSaleMessage(SaleMessage message, AtomicReference<Double> totalValue, AtomicInteger totalSalesNumber) {
        double saleValue = Double.parseDouble(message.getValue());
        totalValue.updateAndGet(total -> total + saleValue); // total value += sale value
        totalSalesNumber.getAndIncrement();
    }

    private void handleMultiSaleMessage(MultiSaleMessage message, AtomicReference<Double> totalValue, AtomicInteger totalSalesNumber) {
        double saleValue = Double.parseDouble(message.getValue());
        int salesNumber = message.getSalesNumber();
        totalValue.updateAndGet(total -> total + saleValue * salesNumber); // total value += sale value * number of sales
        totalSalesNumber.addAndGet(salesNumber);
    }

    private void handleAdjustmentSaleMessage(AdjustmentSaleMessage message, AtomicReference<Double> totalValue, AtomicInteger totalSalesNumber) {
        double saleValue = Double.parseDouble(message.getValue());
        AdjustmentType saleType = message.getType();

        switch (saleType) {
            case ADD: // total value += sale value * number of total sales
                totalValue.updateAndGet(total -> total + saleValue * totalSalesNumber.get());
                break;
            case SUBTRACT: // total value -= sale value * number of total sales
                totalValue.updateAndGet(total -> total - saleValue * totalSalesNumber.get());
                if (totalValue.get() < 0) totalValue.set(0D);
                break;
            case MULTIPLY: // total value *= sale value
                totalValue.updateAndGet(total -> total * saleValue);
                break;
        }
    }

    /**
     * Sale report entity.
     */
    private class SaleReport {

        private AtomicInteger totalSalesNumber = new AtomicInteger();
        private AtomicReference<Double> totalValue = new AtomicReference<>((double) 0);

        AtomicInteger getTotalSalesNumber() {
            return totalSalesNumber;
        }

        AtomicReference<Double> getTotalValue() {
            return totalValue;
        }
    }
}
