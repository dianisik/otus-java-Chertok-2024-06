package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        for (HwListener<K, V> listener : listeners) {
            listener.notify(key, value, "PUT");
        }
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        if (!cache.containsKey(key)) {
            return;
        }
        for (HwListener<K, V> listener : listeners) {
            listener.notify(key, cache.get(key), "REMOVE");
        }
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        for (HwListener<K, V> listener : listeners) {
            listener.notify(key, cache.get(key), "GET");
        }
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
