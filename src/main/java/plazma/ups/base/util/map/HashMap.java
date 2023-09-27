package plazma.ups.base.util.map;

import java.util.*;

public class HashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 10;
    private List<Entry<K, V>>[] buckets = new List[DEFAULT_CAPACITY];
    private int size;

    @Override
    public V put(K key, V value) {
        List<Entry<K, V>> bucket = findBucketWithCreate(key);
        return put(bucket, key, value);
    }

    @Override
    public V get(K key) {
        List<Entry<K, V>> bucket = findBucket(key);
        return bucket == null ? null : get(bucket, key);
    }

    @Override
    public boolean containsKey(K key) {
        List<Entry<K, V>> bucket = findBucket(key);
        return bucket == null ? false : containsKey(bucket, key);
    }

    @Override
    public V remove(K key) {
        List<Entry<K, V>> bucket = findBucket(key);
        return bucket == null ? null : remove(bucket, key);
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

    private List<Map.Entry<K, V>> getBucket(int index) {
        return buckets[index];
    }

    private List<Entry<K, V>> findBucket(K key) {
        int index = getIndex(key);
        return buckets[index];
    }

    private List<Entry<K, V>> findBucketWithCreate(K key) {
        int index = getIndex(key);
        List<Entry<K, V>> bucket = buckets[index];
        if (bucket == null) {
            bucket = new ArrayList<Entry<K, V>>();
            buckets[index] = bucket;
        }
        return bucket;
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
            entry = new EntryImpl<>(key, value);
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

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new MapIterator();
    }

    private static class EntryImpl<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public EntryImpl(K key, V value) {
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

    private class MapIterator implements Iterator<Entry<K, V>> {

        int bucketIndex = -1;
        int entryIndex = -1;
        int index = -1;
        List<Entry<K, V>> bucket;
        Entry<K, V> entry;

        @Override
        public boolean hasNext() {
            return index + 1 < size;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (entryIndex + 1 >= (bucket == null ? 0: bucket.size())) {
                nextBucket();
            }
            entryIndex++;


            entry = bucket.get(entryIndex);
            index++;
            return entry;
        }

        @Override
        public void remove() {
            if (index < 0) {
                throw new IllegalStateException();
            }
            HashMap.this.remove(entry.getKey());
            index--;
        }

        private void nextBucket() {
            while (bucketIndex < buckets.length) {
                bucketIndex++;
                entryIndex = -1;
                bucket = getBucket(bucketIndex);

                if (bucket != null && !bucket.isEmpty()) {
                    break;
                }
            }
        }

    }

}
