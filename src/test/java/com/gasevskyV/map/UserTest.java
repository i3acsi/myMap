package com.gasevskyV.map;

import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class UserTest {
    private Map<User, Object> map;
    private User[] users;

    public UserTest() {
        map = new HashMap<>();
        users = new User[]{
                new User("Vasiliy", 1, new GregorianCalendar(1986, 8, 1)),
                new User("Vasiliy", 1, new GregorianCalendar(1986, 8, 1))
        };
    }

    @Test
    public void whenAddUsersThenPrint() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        System.out.println("User 0 :" + users[0]);
        System.out.println("Object 1:" + obj1);
        System.out.println("Object 2:" + obj2);
        map.put(users[0], obj1);
        map.put(users[1], obj2);
        map.keySet().forEach(System.out::println);
        map.values().forEach(System.out::println);
    }


}