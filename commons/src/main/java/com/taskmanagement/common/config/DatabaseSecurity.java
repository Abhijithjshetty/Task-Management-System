package com.taskmanagement.common.config;


import com.taskmanagement.common.security.rsa.DefaultRsaUtilFactory;
import com.taskmanagement.common.security.CipherUtils;
import com.taskmanagement.common.utils.Base64Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

public class DatabaseSecurity implements CipherUtils {
    private CipherUtils cipherUtils;

    private static final String dbRsaLocationProp = "task.security.rsa.db.location";
    private static final String dbRsaAliasProp = "task.security.rsa.db.alias";
    private static final String dbRsaPasswordProp = "task.security.rsa.db.password";

    public DatabaseSecurity(Environment environment) {
        String location = environment.getProperty(dbRsaLocationProp);
        String alias = environment.getProperty(dbRsaAliasProp);
        String password = Base64Utils.decode(environment.getProperty(dbRsaPasswordProp));
        if(!(StringUtils.isEmpty(location) && StringUtils.isEmpty(alias)
                && StringUtils.isEmpty(password))) {
            this.cipherUtils = DefaultRsaUtilFactory.getUtil(location, alias, password);
        }
    }

    @Override
    public String encrypt(String payload) {
        return cipherUtils.encrypt(payload);
    }

    @Override
    public String decrypt(String payload) {
        return cipherUtils.decrypt(payload);
    }
}
