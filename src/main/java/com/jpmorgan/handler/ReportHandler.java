package com.jpmorgan.handler;

import com.jpmorgan.domain.Message;

import java.util.stream.Stream;

/**
 * Interface for handle reports.
 */
public interface ReportHandler {

    /**
     * Handles report by stream of messages.
     * @param messages stream of {@link Message}
     * @param <T> type of {@link Message}
     */
    <T extends Message> void handle(Stream<T> messages);
}
