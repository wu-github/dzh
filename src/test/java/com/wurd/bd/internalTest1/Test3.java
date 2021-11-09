package com.wurd.bd.internalTest1;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Predicate;

public class Test3 {
    public static void main(String[] args) {

        long s1 = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            String s=String.format("%010d", i);
//            System.out.println(s);
        }
        System.out.println(System.currentTimeMillis() - s1);
        long s2 = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            String ss = StringUtils.leftPad(i + "", 10, "0");
//            System.out.println(ss);
        }
        System.out.println(System.currentTimeMillis() - s2);

//        StringTokenizer st = new StringTokenizer("这个是", "个");
//        while (st.hasMoreElements()) {
//            System.out.println(st.nextElement());
//        }

//        Test3 test3 = new Test3();
//        test3.testLambd();
    }

    private void testLambd() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "a1");
        map.put("a", "a2");
        map.put("b", "b");
        map.forEach((k, v) -> System.out.println(k + v));
        System.out.println(map.values().stream()
                .filter(matchAll()));
        System.out.println(map.size());
    }

    private Predicate<String> matchAll() {
        return (name) -> name.equals("a");
    }

    private static void testRandom() {
        Random random = new Random();
        int max = 100000;
        List<Integer> rands = new ArrayList<>();
        int repeat = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            int rand = random.nextInt(max);
            if (rands.contains(rand)) {
                repeat = repeat + 1;
            } else {
                rands.add(rand);
            }
            System.out.println(rand);
        }
        long end = System.currentTimeMillis();
        System.out.println("#######" + (end - start));
        System.out.println("#######" + repeat);
    }
}
