package ru.otus.exceptions;

public class GettingBanknotesException extends RuntimeException {

    private final static String MESSAGE_GET_BANKNOTE_EXCEPTION = "Banknotes count: %d , but attemp to get: %d";

    public GettingBanknotesException(int banknotesCount, int requestCount){
        super(String.format(MESSAGE_GET_BANKNOTE_EXCEPTION, banknotesCount, requestCount));
    }

    public GettingBanknotesException(String message) {
        super(message);
    }
}
