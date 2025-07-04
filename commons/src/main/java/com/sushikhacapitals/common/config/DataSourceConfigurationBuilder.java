package com.sushikhacapitals.common.config;

import com.sushikhacapitals.common.security.CipherUtils;
import org.springframework.core.env.Environment;

public class DataSourceConfigurationBuilder {
    private static final String dbUrlProp = "sushikha.database.url";
    private static final String dbUserProp = "sushikha.database.username";
    private static final String dbPasswordProp = "sushikha.database.password";

    private final CipherUtils cipherUtils;
    private final Environment environment;

    public DataSourceConfigurationBuilder(CipherUtils cipherUtils, Environment environment) {
        this.cipherUtils = cipherUtils;
        this.environment = environment;
    }

    public DataSourceConfiguration build() {
        DataSourceConfiguration config = null;
        if(this.cipherUtils != null && this.environment != null) {
            String url = environment.getProperty(dbUrlProp);
            String user = environment.getProperty(dbUserProp);
            String password = environment.getProperty(dbPasswordProp);

            config = new DataSourceConfiguration(url, user, password, cipherUtils);
        }
        return config;
    }
}
