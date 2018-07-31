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

public class AdjustmentReportHandlerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private AdjustmentReportHandler adjustmentReport;

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));

        adjustmentReport = new AdjustmentReportHandler();
    }

    @Test
    public void handleMessages() {
        Stream<Message> stream = Stream.of(
                MessageUtils.createAdjustmentSale("test", "10", AdjustmentType.ADD),
                MessageUtils.createAdjustmentSale("test", "10", AdjustmentType.ADD),
                MessageUtils.createAdjustmentSale("test", "10", AdjustmentType.SUBTRACT),
                MessageUtils.createAdjustmentSale("test", "10", AdjustmentType.MULTIPLY),
                MessageUtils.createAdjustmentSale("test", "10", AdjustmentType.MULTIPLY));

        adjustmentReport.handle(stream);
        assertThat(outContent.toString()).isEqualTo(
                "Stopped the application to receive messages." + System.lineSeparator() +
                        "product: test" + System.lineSeparator() +
                        "\t     ADD 10" + System.lineSeparator() +
                        "\t     ADD 10" + System.lineSeparator() +
                        "\tSUBTRACT 10" + System.lineSeparator() +
                        "\tMULTIPLY 10" + System.lineSeparator() +
                        "\tMULTIPLY 10" + System.lineSeparator() +
                        "Continue the application to receive messages." + System.lineSeparator() + System.lineSeparator());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}
