package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * An implementation of a simple Dictionary collection
 * @param <K> key type
 * @param <V> value type
 * @author Marko Miljković
 */
public class Dictionary<K, V> {

    /**
     * Collection used to store dictionary entries
     */
    private final ArrayIndexedCollection<DictionaryEntry<K, V>> entries;

    /**
     * The main constructor
     */
    public Dictionary() {
        entries = new ArrayIndexedCollection<>();
    }

    /**
     * Used to test if the dictionary is empty.
     * @return true if the size of the dictionary is 0, false otherwise
     */
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * Used to get the size of the dictionary.
     * @return the number of dictionary entries
     */
    public int size() {
        return entries.size();
    }

    /**
     * Used to remove all entries from the dictionary.
     */
    public void clear() {
        entries.clear();
    }

    /**
     * Helper method used to access dictionary entries more easily
     * @param key of entry
     * @return entry if present, null otherwise
     */
    private DictionaryEntry<K, V> entries(Object key) {
        int index = entries.indexOf(new DictionaryEntry<>(key, null));

        if (index != -1) {
            return entries.get(index);
        } else {
            return null;
        }
    }

    /**
     * Adds an entry to the dictionary. If an entry with key already exists it overrides the old value
     * and returns the old value. Otherwise, it returns <code>null</code>.
     * @param key for the entry
     * @param value associated with the key
     * @return If an entry with key already exists it returns the old value. Otherwise, it returns <code>null</code>.
     * @throws NullPointerException if key is <code>null</code>.
     */
    public V put(K key, V value) {
        if (key == null) throw new NullPointerException("Key can't be null.");

        DictionaryEntry<K, V> oldEntry = entries(key);

        if (oldEntry != null) {
            V oldValue = oldEntry.getValue();

            oldEntry.setValue(value);

            return oldValue;
        } else {
            entries.add(new DictionaryEntry<>(key, value));
            return null;
        }
    }

    /**
     * Used to get the value associated with key. Returns <code>null</code> if entry doesn't exist or if the value
     * is <code>null</code>.
     * @param key of the associated value
     * @return value associated with key if entry exists, <code>null</code> otherwise.
     */
    public V get(Object key) {
        DictionaryEntry<K, V> entry = entries(key);

        if (entry != null) return entry.getValue();

        return null;
    }

    /**
     * Used to remove a dictionary entry associated with key. If the entry exits returns the associated value,
     * <code>null</code> otherwise.
     * @param key of the entry to be removed
     * @return value associated with key if the entry exists, <code>null</code> otherwise
     */
    public V remove(K key) {
        DictionaryEntry<K, V> oldEntry = entries(key);

        if (oldEntry != null) {
            entries.remove(oldEntry);
            return oldEntry.getValue();
        } else {
            return null;
        }
    }

    /**
     * A helper class used to represent a dictionary entry
     * @param <K> key type
     * @param <V> value type
     */
    private static class DictionaryEntry<K, V> {
        private K key;
        private V value;

        public DictionaryEntry(K key, V value) {
            setKey(key);
            setValue(value);
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(K key) {
            if (key == null) throw new NullPointerException("Key can't be null.");
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DictionaryEntry<?, ?> that = (DictionaryEntry<?, ?>) o;
            return key.equals(that.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
}
