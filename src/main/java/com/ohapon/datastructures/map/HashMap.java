package com.ohapon.datastructures.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;

    private Bucket<K, V>[] buckets = new Bucket[DEFAULT_INITIAL_CAPACITY];
    private int size;

    @Override
    public V put(K key, V value) {
        Bucket<K, V> bucket = findBucketWithCreate(key);
        return put(bucket.entries, key, value);
    }

    @Override
    public V get(K key) {
        Bucket<K, V> bucket = findBucket(key);
        return bucket == null ? null : get(bucket.entries, key);
    }

    @Override
    public boolean containsKey(K key) {
        Bucket<K, V> bucket = findBucket(key);
        return bucket == null ? false : containsKey(bucket.entries, key);
    }

    @Override
    public V remove(K key) {
        Bucket<K, V> bucket = findBucket(key);
        return bucket == null ? null : remove(bucket.entries, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int getIndex(K key) {
        int hash = key == null ? 0 : key.hashCode();
        return Math.abs(hash) % buckets.length;
    }

    private Bucket<K, V> findBucket(K key) {
        int index = getIndex(key);
        return buckets[index];
    }

    private Bucket<K, V> findBucketWithCreate(K key) {
        int index = getIndex(key);
        Bucket<K, V> bucket = buckets[index];
        if (bucket == null) {
            bucket = new Bucket();
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

    private class Bucket<K, V> {

        private List<Entry<K, V>> entries = new ArrayList<>();

        public Bucket() {
        }

    }

    private Entry<K, V> findEntry(List<Entry<K, V>> entries, K key) {
        Entry<K, V> entry = null;
        for (int i = 0; i < entries.size(); i++) {
            entry = entries.get(i);
            if (Objects.equals(key, entry.getKey())) {
                return entry;
            }
        }
        return null;
    }

    private V put(List<Entry<K, V>> entries, K key, V value) {
        Entry<K, V> entry = findEntry(entries, key);
        V oldValue = null;
        if (entry != null) {
            oldValue = entry.getValue();
            entry.setValue(value);
        } else {
            entry = new Entry<>(key, value);
            entries.add(entry);
            size++;
        }
        return oldValue;
    }

    private V get(List<Entry<K, V>> entries, K key) {
        Entry<K, V> entry = findEntry(entries, key);
        return entry == null ? null : entry.getValue();
    }

    private boolean containsKey(List<Entry<K, V>> entries, K key) {
        Entry<K, V> entry = findEntry(entries, key);
        return entry != null;
    }

    private V remove(List<Entry<K, V>> entries, K key) {
        Entry<K, V> entry = findEntry(entries, key);
        if (entry == null) {
            return null;
        }
        V oldValue = entry.getValue();
        entries.remove(entry);
        size--;
        return oldValue;
    }


}
