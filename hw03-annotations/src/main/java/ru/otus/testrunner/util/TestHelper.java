package ru.otus.testrunner.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("java:S3011")
public class TestHelper {
    private static final Logger logger = LoggerFactory.getLogger(TestHelper.class);

    private TestHelper() {
        throw new UnsupportedOperationException("This is utility class, no instantiation");
    }

    public static Method[] getTestMethods(Class<?> testClass, Class<? extends Annotation> annotation) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .toArray(Method[]::new);
    }

    public static Object instantiateTestClass(Class<?> testClass) {
        Object instance;
        try {
            Constructor<?> constructor = testClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (Exception e) {
            logger.error("Cannot instantiate test class");
            throw new TestClassInstantiationException(e);
        }
        return instance;
    }

    public static void invokeTestMethod(Method method, Object testClass)
            throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(testClass);
    }

    public static void runTestMethod(Method method, Object testClass) {
        try {
            invokeTestMethod(method, testClass);
        } catch (InvocationTargetException e) {
            logger.error("Error in test: ", e.getCause());
        } catch (IllegalAccessException e) {
            logger.error("Error accessing test method: ", e);
        }
    }

    public static void displayStats(int totalTests, int successTests) {
        logger.info("Tests complete: {}", totalTests);
        logger.info("Tests successful: {}", successTests);
        if (successTests < totalTests) {
            logger.error("Tests failed: {}", totalTests - successTests);
        }
    }

    public static void runAfterMethods(Method[] afterMethods, Object testClass) {
        try {
            for (Method afterMethod : afterMethods) {
                invokeTestMethod(afterMethod, testClass);
            }
        } catch (Exception e) {
            logger.error("Error performing test tear-down");
            throw new TestTakeDownException(e);
        }
    }

    public static void runBeforeMethods(Method[] beforeMethods, Object testClass)
            throws InvocationTargetException, IllegalAccessException {
        for (Method beforeMethod : beforeMethods) {
            invokeTestMethod(beforeMethod, testClass);
        }
    }
}
