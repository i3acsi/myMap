package com.gasevskyV.map;

import java.util.Calendar;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public int getChildren() {
        return children;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.name.hashCode();
        result = prime * result + this.children;
        result = prime * result + this.birthday.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (!name.equals(other.name)) {
            return false;
        }
        if (children != other.children) {
            return false;
        }
        if (!birthday.equals(other.birthday)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Children: %d, Birthday: %s", name, children, birthday.getTime());
    }
}
