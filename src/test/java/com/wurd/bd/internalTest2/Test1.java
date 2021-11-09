package com.wurd.bd.internalTest2;

import java.nio.charset.StandardCharsets;

public class Test1 {
    public static void main(String[] args) {
        byte[] bytes = {'1', '2', '3'};
        char[] chars = {1, 2, 3};
        String str = "123";
        byte[] strB = str.getBytes(StandardCharsets.UTF_8);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        for (byte b : strB) {
            byte[] bytes1 = {'#', ':', ' ', b};
            System.out.println(new String(bytes1, StandardCharsets.UTF_8));
        }
    }
}
