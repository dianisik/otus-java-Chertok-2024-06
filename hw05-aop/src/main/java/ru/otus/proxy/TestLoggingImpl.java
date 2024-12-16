package ru.otus.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;

public class TestLoggingImpl implements TestLogging {
    private static final Logger logger = LoggerFactory.getLogger(TestLoggingImpl.class);

    @Log
    @Override
    public void calculation(int param) {
        logger.info("Original method calculation param = {}", param);
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        logger.info("Original method calculation param1 = {}, param2 = {}", param1, param2);
    }

    @Override
    public void calculation(int param1, int param2, int param3) {
        logger.info("Original method calculation param1 = {}, param2 = {}, param3 = {}", param1, param2, param3);
    }
}
