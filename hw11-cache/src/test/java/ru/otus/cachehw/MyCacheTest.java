package ru.otus.cachehw;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("java:S2925")
@DisplayName("Самодельный кэш должен")
class MyCacheTest {

    @Test
    @DisplayName("очищаться при недостатке памяти")
    void cacheShouldBePurgedAfterGCIsCalled() throws Exception {
        MyCache<String, BigObject> cache = new MyCache<>();
        for (int i = 0; i < 1000; i++) {
            cache.put("key" + i, new BigObject());
        }

        Field declaredField = MyCache.class.getDeclaredField("cache");
        declaredField.setAccessible(true);
        Map<?, ?> map = (Map<?, ?>) declaredField.get(cache);

        assertTrue(!map.isEmpty() && map.size() < 1000);

        System.gc();
        TimeUnit.MILLISECONDS.sleep(500);
        assertTrue(map.isEmpty());
    }

    private static class BigObject {
        private int[] array;

        public BigObject() {
            this.array = new int[1024 * 1024];
        }
    }
}
