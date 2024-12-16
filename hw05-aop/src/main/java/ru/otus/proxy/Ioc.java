package ru.otus.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Ioc {

    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    public static TestLogging createMyClass() {
        java.lang.reflect.InvocationHandler handler = new InvocationHandler(new TestLoggingImpl());
        return (TestLogging)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{TestLogging.class}, handler);
    }

    static class InvocationHandler implements java.lang.reflect.InvocationHandler {
        private final TestLogging testLogging;
        private final Set<Method> logMethods;

        InvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            logMethods = new HashSet<>();

            Arrays.stream(testLogging.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .map(method -> logMethods.add(convertImplMethodToInterfaceMethod(method)))
                    .collect(Collectors.toSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            logger.info("invoking method:{}", method);

            if (logMethods.contains(method)) {
                logger.info("Executed method: {}, param: {}", method.getName(), Arrays.toString(args));
            }

            return method.invoke(testLogging, args);
        }

        private Method convertImplMethodToInterfaceMethod(Method method) {
            try {
                return TestLogging.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(e);
            }
        }

    }

}