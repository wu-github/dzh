package com.wurd.bd.internalTest;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.SystemPropertyUtils;

import java.util.HashMap;
import java.util.Map;

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