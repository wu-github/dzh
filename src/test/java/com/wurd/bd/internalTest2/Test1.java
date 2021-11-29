package com.wurd.bd.internalTest2;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<String> list = new ArrayList<String>();
//        FutureTask futureTask = new FutureTask(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("future task run");
//                list.add("1");
//            }
//        },list);
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add("1");
                return "12";
            }
        });
        futureTask.run();
        System.out.println(futureTask.get());
        System.out.println(list.size());

//        byte[] bytes = {'1', '2', '3'};
//        char[] chars = {1, 2, 3};
//        String str = "123";
//        byte[] strB = str.getBytes(StandardCharsets.UTF_8);
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
//        for (byte b : strB) {
//            byte[] bytes1 = {'#', ':', ' ', b};
//            System.out.println(new String(bytes1, StandardCharsets.UTF_8));
//        }
    }
}
