package ru.otus.enums;

public enum Banknote {

    BANKNOTE_10(10),
    BANKNOTE_50(50),
    BANKNOTE_100(100),
    BANKNOTE_200(200),
    BANKNOTE_500(500),
    BANKNOTE_1000(1000);

    private final int value;

    Banknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
