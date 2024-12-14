package ru.otus;

import ru.otus.test.SampleTest;
import ru.otus.testrunner.TestRunner;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        TestRunner.runTests(SampleTest.class);
        // ./gradlew :hw03-annotations:run
    }
}
