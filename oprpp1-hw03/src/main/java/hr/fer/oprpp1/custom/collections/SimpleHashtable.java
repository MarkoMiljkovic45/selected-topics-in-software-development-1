package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * An implementation of a hash table that uses a hash function to compute an index.
 * It uses separate chaining to solve collisions. Keys can't be null but values can.
 * @param <K> type of key
 * @param <V> type ov value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

    /**
     * Internal array used to store table entries
     */
    private TableEntry<K, V>[] table;
    /**
     * Current number of entries in SimpleHashtable
     */
    private int size;
    /**
     * Number of times the SimpleHashtable has been modified.
     * Used to achieve expected iterator behaviour.
     */
    private int modificationCount;

    /**
     * Maximum allowed load in SimpleHashtable
     */
    private static final double maxLoadFactor = 0.75;

    /**
     * Default constructor creates table of capacity 16
     */
    public SimpleHashtable() {
        this(16);
    }

    /**
     * Finds the nearest power of two that is bigger than capacity and creates
     * a SimpleHashtable of that size
     * @param capacity the desired capacity of SimpleHashtable
     * @throws IllegalArgumentException if capacity is less than 1
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException("Capacity can't be less than 1");

        int nearestPowerOfTwo = 1;
        while(nearestPowerOfTwo < capacity) {
            nearestPowerOfTwo *= 2;
        }

        table = (TableEntry<K, V>[]) new TableEntry[nearestPowerOfTwo];
        size = 0;
        modificationCount = 0;
    }

    /**
     * Helper method used to calculate table index based on key
     * @param key of entry to be searched
     * @return table index for key
     * @throws NullPointerException if key is <code>null</code>
     */
    private int index(Object key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Helper method used to make accessing table entries easier
     * @param key of the table entry
     * @return table entry with key if present, <code>null</code> otherwise
     */
    private TableEntry<K, V> table(Object key) {
        if (key == null) return null;

        TableEntry<K, V> currentEntry = table[index(key)];

        while (currentEntry != null) {
            if (key.equals(currentEntry.getKey())) {
                return currentEntry;
            }
            currentEntry = currentEntry.next;
        }

        return null;
    }

    /**
     * Helper method used to double table array size once maxLoadFactor is reached
     */
    @SuppressWarnings("unchecked")
    private void resizeTable() {
        int length = table.length * 2;

        TableEntry<K, V>[] newTable     = (TableEntry<K, V>[]) new TableEntry[length];
        TableEntry<K, V>[] tableEntries = toArray();

        table = newTable;
        size = 0;

        for(var entry: tableEntries) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Puts a new value in to the hash table. If key is already assigned a value,
     * the old value is overwritten with the new value and the old value is returned.
     * If key is not assigned, a new TableEntry is created and added to the table, <code>null</code> is returned.
     * If the slot in the table is occupied, the new TableEntry is added to the end of the linked list in that
     * slot.
     * @param key to be associated with value
     * @param value to be stored
     * @return old value if present, <code>null</code> otherwise
     * @throws NullPointerException is key is null
     */
    public V put (K key, V value) {
        if (key == null) throw new NullPointerException("Key can't be null.");

        TableEntry<K, V> oldEntry = table(key);

        if (oldEntry == null) {
            boolean isTableOverloaded = (double) size() / table.length >= maxLoadFactor;

            if (isTableOverloaded) {                                //Resize table if necessary
                resizeTable();
            }

            TableEntry<K, V> newEntry = new TableEntry<>(key, value, null);

            int idx = index(key);                                   //Add new entry
            TableEntry<K, V> currentEntry = table[idx];

            if (currentEntry == null) {
                table[idx] = newEntry;
            } else {
                while (currentEntry.next != null) {
                    currentEntry = currentEntry.next;
                }
                currentEntry.next = newEntry;
            }

            modificationCount++;
            size++;
            return null;
        } else {
            V oldValue = oldEntry.getValue();                      //Update old entry
            oldEntry.setValue(value);
            return oldValue;
        }
    }

    /**
     * Used to get the value associated with key. If key is not associated with any value in
     * the table <code>null</code> is returned. It is not possible to discern if the method returned
     * <code>null</code> because the value associated with key is null or if the key wasn't assigned.
     * @param key used to find the associated value
     * @return the value associated with key if present, null otherwise
     */
    public V get(Object key) {
        TableEntry<K, V> entry = table(key);

        if (entry == null) {
            return null;
        } else {
            return entry.getValue();
        }
    }

    /**
     * Used to get the number of table entries in the table
     * @return number of table entries
     */
    public int size() {
        return size;
    }

    /**
     * Tests if table contains table entry with key.
     * Complexity is O(m) where m is table capacity (table.length)
     * @param key to be tested
     * @return true if table contains entry with key, false otherwise
     */
    public boolean containsKey(Object key) {
        TableEntry<K, V> entry = table(key);

        return entry != null;
    }

    /**
     * Tests if table contains table entry with value
     * Complexity is O(n)
     * @param value to be tested
     * @return true if table contains entry with value, false otherwise
     */
    public boolean containsValue(Object value) {
        for(var entry: this) {
            if (Objects.equals(entry.getValue(), value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Removes table entry with key if present and returns associated value, if there isn't a
     * table entry with key <code>null</code> is returned.
     * @param key of entry to be removed
     * @return value associated with key if present, <code>null</code> otherwise
     */
    public V remove(Object key) {
        if (key == null) return null;

        int length = table.length;

        for (int i = 0; i < length; i++) {                  //Find entry
            TableEntry<K, V> entry = table[i];
            TableEntry<K, V> prevEntry = null;

            while(entry != null) {                          //Search linked list
                if (entry.getKey().equals(key)) {
                    V value = entry.getValue();

                    if (prevEntry == null) {                //Delete entry
                        table[i] = entry.next;
                    } else {
                        prevEntry.next = entry.next;
                    }

                    modificationCount++;
                    size--;
                    return value;
                }

                prevEntry = entry;
                entry = entry.next;
            }
        }

        return null;
    }

    /**
     * Used to test if table has any entries
     * @return true if table is empty, false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public String toString() {                              //"[key1=value1, key2=value2, key3=value3]" format
        StringBuilder sb = new StringBuilder("[");

        Iterator<TableEntry<K, V>> iterator = iterator();

        while(iterator.hasNext()) {
            TableEntry<K, V> entry = iterator.next();

            sb.append(entry.toString());

            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * Used to create an array of all the table entries of size equal to size()
     * @return an array of all the table entries of size equal to size()
     */
    @SuppressWarnings("unchecked")
    public TableEntry<K,V>[] toArray() {
        TableEntry<K, V>[] arr = (TableEntry<K, V>[]) new TableEntry[size()];
        int index = 0;

        for(var entry: this) {
            arr[index++] = entry;
        }

        return arr;
    }

    /**
     * Removes all entries from SimpleHashtable while keeping the same table capacity
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        TableEntry<K, V>[] emptyTable = (TableEntry<K, V>[]) new TableEntry[table.length];

        modificationCount++;
        size = 0;
        table = emptyTable;
    }

    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * A helper class used to represent a table entry
     * @param <K> type of key
     * @param <V> type of value
     */
    public static class TableEntry<K, V> {

        /**
         * Key of the table entry used to represent value
         */
        private final K key;
        /**
         * The value of the table entry
         */
        private V value;
        /**
         * Points to the next table entry that is in the same table slot
         */
        private TableEntry<K, V> next;

        /**
         * The main constructor used to create a TableEntry
         * @param key used to represent value
         * @param value to be stored
         * @param next table entry in the same table slot
         */
        public TableEntry(K key, V value, TableEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Getter method for key
         * @return key
         */
        public K getKey() {
            return key;
        }

        /**
         * Getter method for value
         * @return value
         */
        public V getValue() {
            return value;
        }

        /**
         * Setter method for value
         * @param value new value
         */
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key.toString() + "=" + value.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableEntry<?, ?> that = (TableEntry<?, ?>) o;
            return key.equals(that.key) && Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    /**
     * An implementation of the iterator interface for the SimpleHashtable class
     */
    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {

        /**
         * Index of the current entry chain being visited in array table
         */
        private int currentIndex;
        /**
         * Current entry being visited. This entry gets deleted if remove() is called
         */
        private TableEntry<K, V> currentEntry;
        /**
         * Number of entries visited. Used to implement hasNext()
         */
        private int entryCount;
        /**
         * Saved modification count used for tracking if ConcurrentModificationException needs to be thrown
         */
        private int savedModificationCount;
        /**
         * Used to prevent consecutive remove calls or calling remove() before next()
         */
        private boolean canRemove;

        public IteratorImpl() {
            currentIndex = 0;
            currentEntry = null;
            entryCount = 0;
            savedModificationCount = modificationCount;
            canRemove = false;
        }

        @Override
        public boolean hasNext() {
            if (savedModificationCount != modificationCount) throw new ConcurrentModificationException();

            return entryCount < size();
        }

        @Override
        public TableEntry<K, V> next() {
            if (savedModificationCount != modificationCount) throw new ConcurrentModificationException();

            if (!hasNext()) throw new NoSuchElementException();

            if (currentEntry != null) {
                currentEntry = currentEntry.next;
            }
            while(currentEntry == null) {
                currentEntry = table[currentIndex++];
            }

            canRemove = true;
            entryCount++;
            return currentEntry;
        }

        @Override
        public void remove() {
            if (savedModificationCount != modificationCount) throw new ConcurrentModificationException();

            if(!canRemove) throw new IllegalStateException();

            SimpleHashtable.this.remove(currentEntry.getKey());
            savedModificationCount++;
            entryCount--;
            canRemove = false;
        }
    }
}
