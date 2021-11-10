package com.wurd.bd.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

@Component
public class TestLdap {

    @Autowired
    private LdapTemplate ldapTemplate;

    public void query() {
        String dn = "dc=wurd,dc=com";
        String filter = "ou=Policies";
        ldapTemplate.search(dn, filter, new AttributesMapper<Object>() {

            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return null;
            }
        });
    }

}
