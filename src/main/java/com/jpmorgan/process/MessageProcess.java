package com.jpmorgan.process;

import com.jpmorgan.domain.Message;

/**
 * Interface which is process messages.
 */
public interface MessageProcess {

    /**
     * Processes each message.
     * @param message message which is extend {@link Message}
     * @param <T> type of the {@link Message}
     */
    <T extends Message> void process(T message);
}
