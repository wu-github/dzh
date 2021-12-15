package com.wurd.bd.internalTest2;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import java.util.Hashtable;

public class Test2 {
    public static void main(String[] args) throws NamingException {
        System.setProperty("log4j2.formatMsgNoLookups", "true");
        Logger log = LogManager.getLogger(Test2.class.getName());
        log.error("${jndi:111}");

//        NamingManager.setInitialContextFactoryBuilder(new InitialContextFactoryBuilder() {
//            @Override
//            public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> environment) throws NamingException {
//                return new InitialContextFactory() {
//                    @Override
//                    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
//                        return new InitialContext(environment);
//                    }
//                };
//            }
//        });

        Hashtable<String, String> params = new Hashtable<>();
        params.put("test", "111");
//        params.put("java.naming.factory.initial", "111");
        Context initial = new InitialContext(params);
//        initial.bind("test", "111");
        Object obj = initial.lookup("test");

        System.out.println();

    }
}
