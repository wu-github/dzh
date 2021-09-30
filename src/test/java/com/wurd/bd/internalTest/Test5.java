package com.wurd.bd.internalTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Test5 {
    public static void main(String[] args) {
        Test51 test51_1 = new Test51();
        test51_1.setName("a");
        Test51 test51_2 = new Test51();
        test51_2.setName("b");
        Map<Test51, Integer> map = new HashMap<>();
        map.put(test51_1, 111);
        map.put(test51_2, 222);
        System.out.println(map.size());
    }
}

class Test51 {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Test51 test51 = (Test51) o;
//        return Objects.equals(name, test51.name);
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash("name");
    }
}
