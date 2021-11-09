package com.wurd.bd.internalTest1;

import java.io.*;

public class Test1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User();
        user.setName("name");
        user.setAge(11);
//        ObjectOutputStream objectOutputStream =
//                new ObjectOutputStream(new FileOutputStream(
//                        new File("C:\\Users\\len\\Desktop\\user.txt")));
//        objectOutputStream.writeObject(user);
//        objectOutputStream.close();
//        ObjectInputStream objectInputStream =
//                new ObjectInputStream(new FileInputStream(
//                        new File("C:\\Users\\len\\Desktop\\user.txt")));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oOut = new ObjectOutputStream(out);
        oOut.writeObject(user);
        ObjectInputStream objectInputStream =
                new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
        User u = (User)objectInputStream.readObject();
        oOut.close();
        objectInputStream.close();
        System.out.printf(u.getName() + ", " + u.getAge());

    }
}

class User implements Serializable{
    private /*transient*/ String name;
    private transient int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}