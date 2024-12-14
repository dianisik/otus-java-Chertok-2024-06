package ru.otus;

import ru.otus.proxy.Ioc;
import ru.otus.proxy.TestLogging;

public class Main {
    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createMyClass();
        testLogging.calculation(5);
        testLogging.calculation(35, 37);
        testLogging.calculation(55, 57, 59);
    }
}