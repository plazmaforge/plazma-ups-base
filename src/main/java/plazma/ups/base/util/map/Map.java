package plazma.ups.base.util.map;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>>{

    V put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    V remove(K key);

    int size();

    boolean isEmpty();

    interface Entry<K, V> {

        K getKey();

        V getValue();

        void setValue(V value);
    }


}
