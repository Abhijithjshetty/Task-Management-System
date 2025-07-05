package com.taskmanagement.common.security.rsa;


import com.taskmanagement.common.utils.RSAUtil;

public class DefaultRsaUtilFactory {

    public static RSAUtil getUtil(String path, String alias, String password) {
        return new PKCSRsaUtilFactory(path, alias, password).getRSAUtil();
    }
}
