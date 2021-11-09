package com.wurd.bd.internalTest1;

import org.springframework.util.SystemPropertyUtils;

public class Test {
    public static void main(String[] args) throws Exception{
        Object str = "123";
        System.out.println();
    }

    public static ITest get(){
        return (str) -> {
            if (str instanceof String) {
                System.out.println(str);
            }
        };
    }

    private static void testPlaceholderResolver() {
        System.setProperty("spring.home", "spring.home");
        System.setProperty("SPRING_HOME", "SPRING_HOME");
        String home = SystemPropertyUtils
                .resolvePlaceholders("${spring.home:${SPRING_HOME:.}}", true);
        System.out.println(home);
        String home1 = SystemPropertyUtils
                .resolvePlaceholders("${spring.home}", false);
        System.out.println(home1);
    }
}
interface ITest{
    void get(Object str);
}