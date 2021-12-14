package com.wurd.bd.internalTest2;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Test2 {
    public static void main(String[] args) throws NamingException {
        Logger log = LogManager.getLogger(Test2.class.getName());
        log.error("111");

        Context initial = new InitialContext();
        initial.lookup("java:");

        System.out.println();

    }
}
