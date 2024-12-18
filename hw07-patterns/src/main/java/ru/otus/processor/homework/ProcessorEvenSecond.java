package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorEvenSecond implements Processor {
    private DateTimeProvider dateTimeProvider;

    public void setDateTimeProvider(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        int second = dateTimeProvider == null ? getNowSecond() : getSecond();
        if (isEven(second)) {
            throw new EvenSecondException("The even second for processor of message");
        }
        return message;
    }

    private int getNowSecond() {
        return LocalDateTime.now().getSecond();
    }

    private int getSecond() {
        return dateTimeProvider.getDateTime().getSecond();
    }

    private boolean isEven(int second) {
        return second % 2 == 0;
    }

    public static class EvenSecondException extends RuntimeException {
        public EvenSecondException(String message) {
            super(message);
        }
    }


}
