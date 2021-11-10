package com.wurd.bd.ldap;

import lombok.ToString;
import org.springframework.ldap.odm.annotations.Entry;

@Entry(base = "ou=Policies,dc=wurd,dc=com", objectClasses = {"organizationalRole","pwdPolicy"})
@ToString
public class TestEntry {
}
