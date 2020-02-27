package com.gasevskyV.tree;

import java.util.*;

public class Tree<E extends Comparable<E>> implements SimpleTree<E>  {
    private Node<E> root;
    private int modCount;

    public Tree(E value) {
        root = new Node<>(value);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(child)) {
                return false;
            }
            if (el.eqValue(parent)) {
                rsl = Optional.of(el);
            }
            for (Node<E> children : el.leaves()) {
                data.offer(children);
            }
        }
        if (rsl.isPresent()) {
            Node<E> temp = rsl.get();
            temp.add(new Node<>(child));
            result = true;
            modCount++;
        }
        return result;
    }


    public boolean isBinary() {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.leaves().size() > 2) {
                return false;
            }
            for (Node<E> children : el.leaves()) {
                data.offer(children);
            }
        }
        return true;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    @Override
    public Iterator<E> iterator() {
        int expectedModCount = this.modCount;
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        return new Iterator<E>() {
            Optional<Node<E>> rsl = Optional.empty();


            @Override
            public boolean hasNext() {
                return !data.isEmpty();
            }

            @Override
            public E next() {
                checkMod();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node<E> el = data.poll();
                rsl = Optional.of(el);
                for (Node<E> child : el.leaves()) {
                    data.offer(child);
                }
                return rsl.get().value;
            }

            private void checkMod() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}
