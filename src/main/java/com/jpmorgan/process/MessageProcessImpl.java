package com.jpmorgan.process;

import com.jpmorgan.domain.Message;
import com.jpmorgan.handler.ReportHandler;
import com.jpmorgan.util.MessageUtils;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * Implementation of {@link MessageProcess} interface.
 * <br>
 * Processing requirements.
 * <br>
 * <b>1.</b> All sales must be recorded.
 * <br>
 * <b>2.</b> All messages must be processed.
 * <br>
 * <b>3.</b> After every 10th message received your application should log a report detailing the number of sales of each
 * product and their total value.
 * <br>
 * <b>4.</b> After 50 messages your application should log that it is pausing, stop accepting new messages and
 * log a report of the adjustments that have been made to each sale type while the application was running.
 */
public class MessageProcessImpl implements MessageProcess {

    static final int SALE_REPORT_MESSAGE_NUMBER = 10;
    static final int ADJUSTMENT_REPORT_MESSAGE_NUMBER = 50;

    private ExecutorService executor = ForkJoinPool.commonPool();
    private Collection<Message> messages = new ConcurrentLinkedQueue<>();

    private final ReportHandler saleReportHandler;
    private final ReportHandler adjustmentReportHandler;

    public MessageProcessImpl(ReportHandler saleReportHandler, ReportHandler adjustmentReportHandler) {
        this.saleReportHandler = saleReportHandler;
        this.adjustmentReportHandler = adjustmentReportHandler;
    }

    @Override
    public <T extends Message> void process(T message) {
        addMessage(message); // record message

        int messageSize = messages.size();
        if (messageSize % SALE_REPORT_MESSAGE_NUMBER == 0) { // process report after every 10 messages
            Stream<Message> stream = messages.stream().skip(messageSize - SALE_REPORT_MESSAGE_NUMBER);
            handleReport(saleReportHandler, stream);
        }

        if (messageSize % ADJUSTMENT_REPORT_MESSAGE_NUMBER == 0) { // process report after every 50 messages
            Stream<Message> stream = messages.stream().skip(messageSize - ADJUSTMENT_REPORT_MESSAGE_NUMBER);
            handleReport(adjustmentReportHandler, stream);
        }
    }

    private void addMessage(Message message) {
        while (MessageUtils.isMessagePausing()) {
            System.out.println("pause");
            timeout(1);
        }
        messages.add(message);
    }

    private void handleReport(ReportHandler reportHandler, Stream<Message> stream) {
        Future<?> future = executor.submit(
                () -> reportHandler.handle(stream)
        );

        while (!future.isDone()) { // wait while is done
            timeout(1);
        }
    }

    private void timeout(long timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
