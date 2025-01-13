package ru.otus.cachehw;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("java:S125")
public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) throws InterruptedException {
        new HWCacheDemo().demo();
    }

    private void demo() throws InterruptedException {
        HwCache<String, Integer> cache = new MyCache<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        @SuppressWarnings("java:S1604")
        HwListener<String, Integer> listener = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        for (int i = 0; i < 10; i++) {

            cache.put("key" + i, i);
        }

        // System.gc();
        TimeUnit.SECONDS.sleep(1);
        logger.info("key2:{}", cache.get("key2"));
        cache.remove("key2");
        logger.info("key2: {}", cache.get("key2"));
        cache.removeListener(listener);
    }
}
