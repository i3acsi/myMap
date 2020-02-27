package com.gasevskyV.tree;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class TreeTest {
    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        tree.add(6, 7);
        tree.add(6, 8);
        tree.add(6, 9);
        tree.add(9, 10);
        tree.add(2, 11);
        tree.add(2, 12);
        tree.add(12, 13);
        assertThat(
                tree.findBy(13).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void iteratorTest() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        Iterator iter = tree.iterator();
        int i = 1;
        while (iter.hasNext()) {
            assertThat(iter.next(), is(i++));
        }
    }

    @Test
    public void whenAddThenFalse() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assert (!tree.add(3, 4));
        assert (!tree.add(2, 1));
    }

    @Test
    public void isBinaryTrue() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(2, 5);
        tree.add(3, 6);
        tree.add(3, 7);
        tree.add(4, 8);
        tree.add(4, 9);
        tree.add(5, 10);
        tree.add(6, 11);
        tree.add(7, 12);
        assert (tree.isBinary());
    }

    @Test
    public void isBinaryFalse() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(2, 5);
        tree.add(3, 6);
        tree.add(3, 7);
        tree.add(4, 8);
        tree.add(4, 9);
        tree.add(5, 10);
        tree.add(6, 11);
        tree.add(7, 12);
        tree.add(7, 13);
        tree.add(7, 14);
        assert (!tree.isBinary());
    }
}