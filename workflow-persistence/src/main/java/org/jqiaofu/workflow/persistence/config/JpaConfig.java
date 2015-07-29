/**
 * workflow-persistence
 * org.jqiaofu.workflow.persistence.config
 * JpaConfig.java
 * 
 * 2015年7月29日-下午1:11:25
 * 2015◎讯银金融-版权所有
 *
 */
package org.jqiaofu.workflow.persistence.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.activiti.engine.identity.User;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 *
 * JpaConfig
 * @auth zimao_jiang@epaybank.com
 * @data 2015年7月29日 下午1:11:25
 *
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
//enable jpa repositories
@EnableJpaRepositories(basePackages = {"org.jqiaofu.workflow.repository"})
@EnableJpaAuditing(auditorAwareRef = "auditor")
//加载资源文件
@PropertySources(
      value = {
      		//@PropertySource({"classpath:/config/db.properties"}),
      		@PropertySource(value = "classpath:/db.properties", ignoreResourceNotFound = false)
      })
public class JpaConfig implements TransactionManagementConfigurer {
	
	private static final Logger log = LoggerFactory.getLogger(JpaConfig.class);
	
	@Inject
    private Environment env;
	@Inject
	private DataSource dataSource;
	
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setMappingResources("META-INF/orm.xml");
        emf.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        emf.setPackagesToScan("org.jqiaofu.workflow.domain");
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setJpaProperties(jpaProperties());
        emf.setJpaDialect(new HibernateJpaDialect());
        return emf;
	}
	
	private Properties jpaProperties() {
        Properties extraProperties = new Properties();
        extraProperties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        extraProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        extraProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        if (log.isInfoEnabled()) {
            log.info(" hibernate.dialect @" + env.getProperty("hibernate.dialect"));
        }
        if (env.getProperty("hibernate.dialect") != null) {
            extraProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        }

       // extraProperties.put("hibernate.hbm2ddl.import_files", "/import.sql");
        return extraProperties;
    }

    @Bean(name="transactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    @Named("auditor")
    public AuditorAware<User> auditor() {
        if (log.isInfoEnabled()) {
            log.info("get current auditor@@@@");
        }

        return new AuditorAware<User>() {

            @Override
            public User getCurrentAuditor() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth == null) {
                    return null;
                }
                if (auth instanceof AnonymousAuthenticationToken) {
                    return null;
                }
                return (User) auth.getPrincipal();
            }
        };
    }

}
