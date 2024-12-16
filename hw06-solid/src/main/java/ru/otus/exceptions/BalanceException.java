package ru.otus.exceptions;

public class BalanceException extends RuntimeException {

    private final static String MESSAGE_BALANCE_EXCEPTION = "Balance error, ATM has: %d , but was attemp to get: %d money";

    public BalanceException(String message) {
        super(message);
    }

    public BalanceException(int total, int requestSum){
        super(String.format(MESSAGE_BALANCE_EXCEPTION, total, requestSum));
    }

}
