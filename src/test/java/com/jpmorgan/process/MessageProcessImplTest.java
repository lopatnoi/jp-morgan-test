package com.jpmorgan.process;

import com.jpmorgan.domain.SaleMessage;
import com.jpmorgan.handler.ReportHandler;
import com.jpmorgan.util.MessageUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.jpmorgan.process.MessageProcessImpl.ADJUSTMENT_REPORT_MESSAGE_NUMBER;
import static com.jpmorgan.process.MessageProcessImpl.SALE_REPORT_MESSAGE_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class MessageProcessImplTest {

    @Mock
    private ReportHandler saleReportHandler;
    @Mock
    private ReportHandler adjustmentReportHandler;

    private MessageProcessImpl messageProcess;

    @Before
    public void setup() {
        messageProcess = new MessageProcessImpl(saleReportHandler, adjustmentReportHandler);
    }

    @Test
    public void messageProcess() {
        SaleMessage message = MessageUtils.createSaleMessage("test", "11");

        messageProcess.process(message);
        assertThat(messageProcess).isNotNull();
    }

    @Test
    public void shortReportProcess() {
        doNothing().when(saleReportHandler).handle(any());

        SaleMessage message = MessageUtils.createSaleMessage("test", "11");
        for (int i = 0; i < SALE_REPORT_MESSAGE_NUMBER; i++) {
            messageProcess.process(message);
        }
        assertThat(messageProcess).isNotNull();
    }

    @Test
    public void longReportProcess() {
        doNothing().when(saleReportHandler).handle(any());
        doNothing().when(adjustmentReportHandler).handle(any());

        SaleMessage message = MessageUtils.createSaleMessage("test", "11");
        for (int i = 0; i < ADJUSTMENT_REPORT_MESSAGE_NUMBER; i++) {
            messageProcess.process(message);
        }
        assertThat(messageProcess).isNotNull();
    }
}
