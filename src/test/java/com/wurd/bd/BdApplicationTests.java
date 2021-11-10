package com.wurd.bd;

import com.wurd.bd.ldap.TestLdap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BdApplicationTests {

    @Autowired
    private TestLdap testLdap;

    @Test
    void contextLoads() {
        testLdap.query();
    }

}
