package com.sushikhacapitals.common.config;

import com.sushikhacapitals.common.security.CipherUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Slf4j
public class DataSourceConfiguration {
    private final String url;
    private final String user;
    private final String password;
    private final CipherUtils cipherUtils;

    public DataSourceConfiguration(String url, String user, String password, CipherUtils cipherUtils) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.cipherUtils = cipherUtils;
    }
    public DataSourceConfiguration(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        cipherUtils=null;
    }

    public DataSource getDataSource() {
        log.info("Generating DataSource Instance.");
        return DataSourceBuilder.create()
            .url(this.url)
            .username(this.user)
            .password(this.getDecryptedPassword())
            .build();
    }

    private String getDecryptedPassword() {
        log.info("Decrypting encrypted database password.");
        return this.cipherUtils!=null?cipherUtils.decrypt(password):password;
    }

    private String getPassword() {
        log.info(" password.");
        return password;
    }

    public static DataSourceConfigurationBuilder builder(CipherUtils cipherUtils, Environment environment) {
        return new DataSourceConfigurationBuilder(cipherUtils, environment);
    }

    public static DataSourceConfigurationBuilder builder(Environment environment) {
        return new DataSourceConfigurationBuilder(new DatabaseSecurity(environment), environment);
    }
}
