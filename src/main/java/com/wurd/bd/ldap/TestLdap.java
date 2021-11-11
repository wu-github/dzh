package com.wurd.bd.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestLdap {

    @Autowired
    private LdapTemplate ldapTemplate;

    public void auth() {
        try {
            String base = "ou=People";
            ldapTemplate.authenticate(LdapQueryBuilder.query()
                            .base(base)
                            .where("uid").is("test3")
                    , "111");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public void search() {

        List<Object> res = new ArrayList<Object>();
        String base = "ou=People";

        ldapTemplate.search(base, "(&(objectClass=top)(uid=test3))"
//                ,
//                "(objectClass=*)",
//                SearchControls.OBJECT_SCOPE,
//                new String[]{"objectClass"}
                , new AttributesMapper<Object>() {
                    @Override
                    public Object mapFromAttributes(Attributes attributes) throws NamingException {
                        res.add(attributes);
                        return null;
                    }
                });
        System.out.println(res.size());

    }

    public void query() {
        List<Object> res = new ArrayList<Object>();
        String base = "ou=People";
        ldapTemplate.search(LdapQueryBuilder.query()
                        .base(base)
                        .attributes("*")
//                        .where("uid").is("test1")
                        .where("uid").is("test3")
//                        .where("objectClass").is("organizationalUnit")
//                        .and("cn").is("pwdDefault")
                , new AttributesMapper<Object>() {
                    @Override
                    public Object mapFromAttributes(Attributes attributes) throws NamingException {
                        res.add(attributes);
                        return null;
                    }
                });
        System.out.println(res.size());
    }

}
