package com.gasevskyV.map;

import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class SimpleMapTest {
    private SimpleMap<User, Integer> map;
    private User[] users;

    @Before
    public void init() {
        this.map = new SimpleMap<>();
        users = new User[]{
                new User("Vasiliy", 1, new GregorianCalendar(1986, 8, 1)),
                new User("Vasiliy", 1, new GregorianCalendar(1986, 8, 1)),
                new User("Masha", 0, new GregorianCalendar(1990, 0, 10)),
                new User("User3", 3, new GregorianCalendar(2000, 3, 6))
        };
    }

    @Test
    public void whenAddSameKeyThenMapHaveLast() {
        map.insert(users[0], 0);
        map.insert(users[1], 1);
        assertThat(map.get(users[0]), is(1));
    }

    @Test
    public void iteratorTest() {
        map.insert(users[0], 0);
        map.insert(users[1], 1);
        map.insert(users[2], 2);
        map.insert(users[3], 3);
        map.insert(null, 4);
        map.insert(null, 5);
        Iterator iterator = map.iterator();
        assert (iterator.hasNext());
        assertThat(iterator.next(), is(5));
        assert (iterator.hasNext());
    }

    @Test
    public void whenDeleteItemThenMapHaveNoItem() {
        map.insert(users[1], 1);
        map.insert(users[2], 2);
        map.insert(users[3], 3);
        map.insert(null, 4);
        assertThat(map.get(users[1]), is(1));
        assert (map.delete(users[0]));
        assert (!map.delete(users[1]));
        assert (map.get(users[0]) == null);
    }

    @Test
    public void whenOutOfLimitThenGrow() {
        for (int i = 0; i < 16; i++) {
            map.insert(new User(String.format("name %d", i), i, new GregorianCalendar(1999, 0, i)), i);
        }
        assert (map.insert(users[0], 1));
    }

    @Test
    public void whenAddNull() {
        assert (map.insert(null, 0));
        assert (map.insert(null, 1));
        assert (map.get(null) == 1);
        boolean flag = false;
        try {
            map.insert(users[0], null);
        } catch (NullPointerException e) {
            flag = true;
        }
        assert (flag);
        System.out.println(map.delete(null));
    }

    @Test
    public void whenDeleteNull() {
        map.insert(null, 1);
        assert (map.delete(null));
        assert (!map.delete(null));
    }
}