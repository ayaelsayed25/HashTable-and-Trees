
public interface IDictionary<K, V> {
	/**
	* Retrieves the value corresponding to the specified key.
	* Returns null if the key doesn’t exist in the dictionary.
	* @param key: key.
	* @throws: Throws Exception if the key is null.
	* */
	public V get(K key);
	/**
	* Inserts a new pair of the key and value in the dictionary.
	* If the key already exists, the old value is returned.
	* If the key doesn't exist the, null is returned,
	* @param key: key.
	* @param value: value.
	* @throws: Throws an exception if the key or the value is
	null.
	* */
	public V set(K key, V value);
	/**
	* Removes the key and returns its value.
	* @param key: key
	* @throws: throws exception if the key is null
	*
	* */
	public V remove(K key);
	/**
	* Returns true if the dictionary is empty and false otherwise.
	* */
	public boolean isEmpty();
}
