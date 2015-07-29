/**
 * workflow
 * org.jqiaofu.workflow.persistence.config
 * DataSourceConfig.java
 * 
 * 2015年7月27日-下午2:32:21
 * 2015◎讯银金融-版权所有
 *
 */
package org.jqiaofu.workflow.persistence.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 *
 * DataSourceConfig
 * @auth zimao_jiang@epaybank.com
 * @data 2015年7月27日 下午2:32:21
 *
 */
@Configuration
public class DataSourceConfig {
	
	private static final String DRIVER_CLASS_NAME = "jdbc.driverClassName";
    private static final String URL = "jdbc.url";
    private static final String USERNAME = "jdbc.username";
    private static final String PASSWORD = "jdbc.password";
    
    @Inject
    private Environment env;

    @Bean(name="dataSource")
    @Profile("dev")
    public DataSource dataSource() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(env.getProperty(DRIVER_CLASS_NAME));
        bds.setUrl(env.getProperty(URL));
        bds.setUsername(env.getProperty(USERNAME));
        bds.setPassword(env.getProperty(PASSWORD));
        return bds;
    }
}
