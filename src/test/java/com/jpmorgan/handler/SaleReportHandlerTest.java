package com.jpmorgan.handler;

import com.jpmorgan.domain.Message;
import com.jpmorgan.util.AdjustmentType;
import com.jpmorgan.util.MessageUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SaleReportHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private SaleReportHandler saleReport;

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));

        saleReport = new SaleReportHandler();
    }

    @Test
    public void handleMessages() {
        Stream<Message> stream = Stream.of(
                MessageUtils.createSaleMessage("test", "10"),
                MessageUtils.createSaleMessage("test", "10"),
                MessageUtils.createSaleMessage("test", "10"),
                MessageUtils.createMultiSale("test", "10", 2),
                MessageUtils.createAdjustmentSale("test", "10", AdjustmentType.ADD),
                MessageUtils.createAdjustmentSale("test", "10", AdjustmentType.ADD),
                MessageUtils.createAdjustmentSale("test", "10", AdjustmentType.SUBTRACT),
                MessageUtils.createAdjustmentSale("test", "10", AdjustmentType.MULTIPLY));

        saleReport.handle(stream);
        assertThat(outContent.toString()).isEqualTo("5 test 1000.00" + System.lineSeparator());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}
