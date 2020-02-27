package com.gasevskyV.map;


import java.util.*;

public class SimpleMap<K, V> implements Iterable {
    private static final int DEFAULT_SIZE = 1 << 4; //16
    private int currentSize;
    private Node<K, V>[] table;
    private int modCount;

    public SimpleMap() {
        this.table = new Node[DEFAULT_SIZE];
        this.currentSize = 0;
        this.modCount = 0;
    }

    public boolean insert(K key, V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        boolean result = false;
        if (currentSize == table.length) {
            reHash();
        }
        int hash = hash(key);
        int pos = indexFor(hash, table.length);
        Node<K, V> temp = table[pos];
        while (temp != null) {
            if (hash == temp.hash & Objects.equals(key, temp.key)) {
                temp.value = value;
                modCount++;
                return true;
            }
            temp = temp.next;
        }
        temp = table[pos];
        table[pos] = new Node<>(hash, key, value, temp);
        result = true;
        modCount++;
        currentSize++;
        return result;
    }

    public V get(K key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if ((node.hash == hash) && Objects.equals(node.key, key)) {
                return node.value;
            }
        }
        return null;
    }

    public boolean delete(K key) {
        boolean result = false;
        int hash = hash(key);
        int pos = indexFor(hash, table.length);
        Node<K, V> temp = table[pos];
        Node<K, V> prev = null;
        while (temp != null) {
            if (hash == temp.hash & Objects.equals(temp.key, key)) {
                if (prev != null) {
                    prev.next = temp.next;
                    currentSize--;
                    modCount++;
                    return true;
                } else {
                    table[pos] = temp.next;
                    currentSize--;
                    modCount++;
                    return true;
                }
            }
            prev = temp;
            temp = temp.next;
        }
        return result;
    }

    private void reHash() {
        int oldLenght = table.length;
        int newLength = oldLenght << 1;
        Node<K, V>[] newTable = new Node[newLength];
        Node<K, V>[] oldTable = table;
        this.table = newTable;
        for (Node<K, V> aTable : table) {
            Node<K, V> temp = aTable;
            while (temp != null) {
                this.insert(temp.key, temp.value);
                temp = temp.next;
            }
        }
        modCount++;
        oldTable = null;
        newTable = null;
    }

    static final int hash(Object key) {
        return (key == null) ? 0 : key.hashCode() ^ (key.hashCode() >>> 16);
    }

    static final int indexFor(int hash, int length) {
        return (length - 1) & hash;
    }

    @Override
    public Iterator iterator() {
        int expectedModCount = this.modCount;
        return new Iterator() {
            int innerIndex = 0;
            Node<K, V> current = table[0];

            @Override
            public boolean hasNext() {
                boolean result = false;
                if (current == null) {
                    for (; innerIndex < table.length; innerIndex++) {
                        current = table[innerIndex];
                        if (current != null) {
                            result = true;
                            break;
                        }
                    }
                } else {
                    result = true;
                }
                return result;
            }

            @Override
            public V next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                checkMod();
                V result = current.value;
                current = current.next;
                innerIndex++;
                return result;
            }

            private void checkMod() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    private static class Node<K, V> {
        int hash;
        K key;
        V value;
        Node<K, V> next;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node<?, ?> node = (Node<?, ?>) o;
            return Objects.equals(key, node.key)
                    && Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
