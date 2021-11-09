package com.wurd.bd.internalTest1;

public class Test2 {
    public static void main(String[] args) {
        String a = "fdsafa|fdafsa|231321";
        System.out.println(a.split("\\|").length + "");
        for(int i=0;i<10;i++){
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        testSync(index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void testSync(int i) throws InterruptedException {
//        synchronized (new String("11").intern()){
        synchronized (Test2.class){
            System.out.println("start" + i);
            Thread.sleep(2000);
            System.out.println("end" + i);
        }
    }

}
