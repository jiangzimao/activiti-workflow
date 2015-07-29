/**
 * workflow
 * org.jqiaofu.workflow.config
 * ActivitiConfig.java
 * 
 * 2015年7月27日-下午3:31:39
 * 2015◎讯银金融-版权所有
 *
 */
package org.jqiaofu.workflow.core.config;

import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * activiti 配置
 * @auth zimao_jiang@epaybank.com
 * @data 2015年7月27日 下午3:31:39
 *
 */
@Configuration
public class ActivitiConfig {
	
	@Autowired
	private DataSource dataSource;
	
	private ProcessEngine processEngine;
	
	@Bean
	public JpaTransactionManager transactionManager(){
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		JpaTransactionManager transactionManager = (JpaTransactionManager) context.getBean("transactionManager");
		return transactionManager;
	}
	
	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration(){
		SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
		processEngineConfiguration.setDataSource(dataSource);
		processEngineConfiguration.setTransactionManager(transactionManager());
		processEngineConfiguration.setDatabaseSchemaUpdate("true");
		processEngineConfiguration.setJobExecutorActivate(true);
		return processEngineConfiguration;
	}
	
	@Bean
	public ProcessEngine processEngine(){
		ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
		ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
		processEngineFactoryBean.setApplicationContext(applicationContext);
		processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration());
		try {
			processEngine = processEngineFactoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return processEngine;
	}
	
	@Bean
	public RepositoryService repositoryService(){
		return processEngine.getRepositoryService();
	}
	
	@Bean
	public RuntimeService runtimeService(){
		return processEngine.getRuntimeService();
	}
	
	@Bean
	public TaskService taskService(){
		return processEngine.getTaskService();
	}
	
	@Bean
	public HistoryService historyService(){
		return processEngine.getHistoryService();
	}
	
	@Bean
	public ManagementService managementService(){
		return processEngine.getManagementService();
	}
}
