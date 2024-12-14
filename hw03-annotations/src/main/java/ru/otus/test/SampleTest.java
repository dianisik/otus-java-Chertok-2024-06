package ru.otus.test;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

@SuppressWarnings({"java:S106", "java:S112", "java:S125"})
public class SampleTest {

    @Before
    void setup() {
        // throw new RuntimeException("Error in setup");
        System.out.println("Setup...");
    }

    @Before
    void secondSetup() {
        System.out.println("Setup another ...");
    }

    @Test
    void testPrintOne() {
        System.out.println("Hello World one!");
    }

    @Test
    void testPrintTwo() {
        System.out.println("Hello World two!");
    }

    @Test
    void testPrintThree() {
        throw new AssertionError("Assertion error!");
    }

    @Test
    void testPrintFour() {
        System.out.println("Hello World four!");
    }

    @After
    void takeDown() {
        System.out.println("TakeDown...");
    }

    @After
    void secondTakeDown() {
        System.out.println("TakeDown another ..." + System.lineSeparator());
    }
}
