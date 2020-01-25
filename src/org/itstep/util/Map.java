package org.itstep.util;

public class Map<K, V> {
	private List<K> keys = new List<>();
	private List<V> values = new List<>();

	public void put(K key, V value) {
		boolean exists = false;
		int i;
		for(i = 0; i < keys.size(); i++) {
			if(keys.get(i).equals(key)) {
				exists = true;
				break;
			}
		}
		if(exists) {
			values.set(i, value);
		} else {
			keys.add(key);
			values.add(value);
		}
	}

	public V get(K key) {
		for(int i = 0; i < keys.size(); i++) {
			if(keys.get(i).equals(key)) {
				return values.get(i);
			}
		}
		return null;
	}

	public List<K> getKeys() {
		return keys;
	}
}
