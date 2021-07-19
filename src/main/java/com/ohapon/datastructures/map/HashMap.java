package com.ohapon.datastructures.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {

    private Bucket<K, V>[] buckets = new Bucket[10];
    private int size;

    @Override
    public V put(K key, V value) {
        Bucket<K, V> bucket = findBucket(key, true);
        return bucket.put(key, value);
    }

    @Override
    public V get(K key) {
        Bucket<K, V> bucket = findBucket(key, false);
        return bucket == null ? null : bucket.get(key);
    }

    @Override
    public boolean containsKey(K key) {
        Bucket<K, V> bucket = findBucket(key, false);
        return bucket == null ? false : bucket.containsKey(key);
    }

    @Override
    public V remove(K key) {
        Bucket<K, V> bucket = findBucket(key, false);
        return bucket == null ? null : bucket.remove(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Bucket<K, V> findBucket(K key, boolean create) {
        int code = key == null ? 0 : key.hashCode();
        int index = code % buckets.length;
        Bucket<K, V> bucket = buckets[index];
        if (bucket == null && create) {
            bucket = new Bucket(this);
            //bucket.setCode(code);
            buckets[index] = bucket;
        }
        return bucket;
    }

    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private static class Bucket<K, V> {

        private HashMap<K, V> map;

        private List<Entry<K, V>> entries = new ArrayList<>();

        public Bucket(HashMap<K, V> map) {
            this.map = map;
        }

        private Entry<K, V> findEntry(K key) {
            Entry<K, V> entry = null;
            for (int i = 0; i < entries.size(); i++) {
                entry = entries.get(i);
                if (Objects.equals(key, entry.getKey())) {
                    return entry;
                }
            }
            return null;
        }

        public V put(K key, V value) {
            Entry<K, V> entry = findEntry(key);
            V oldValue = null;
            if (entry != null) {
                oldValue = entry.getValue();
                entry.setValue(value);
            } else {
                entry = new Entry<>(key, value);
                entries.add(entry);
                map.size++;
            }
            return oldValue;
        }

        public V get(K key) {
            Entry<K, V> entry = findEntry(key);
            return entry == null ? null : entry.getValue();
        }

        public boolean containsKey(K key) {
            Entry<K, V> entry = findEntry(key);
            return entry != null;
        }

        public V remove(K key) {
            Entry<K, V> entry = findEntry(key);
            if (entry == null) {
                return null;
            }
            V oldValue = entry.getValue();
            entries.remove(entry);
            map.size--;
            return oldValue;
        }

    }
}
