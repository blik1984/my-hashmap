package util;

public interface Map<K,V> {
	
	boolean put(K key, V value);
	V get(K key);
	V remove(K key);

}
