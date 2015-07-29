/**
 * workflow
 * org.jqiaofu.workflow.config
 * AppConfig.java
 * 
 * 2015年7月27日-下午2:31:05
 * 2015◎讯银金融-版权所有
 *
 */
package org.jqiaofu.workflow.web;

import org.jqiaofu.workflow.core.config.ActivitiConfig;
import org.jqiaofu.workflow.persistence.config.JpaConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySources;

/**
 *
 * AppConfig
 * @auth zimao_jiang@epaybank.com
 * @data 2015年7月27日 下午2:31:05
 *
 */

@Configuration
@ComponentScan(basePackages={"org.jqiaofu.workflow"},
excludeFilters=@ComponentScan.Filter(type=FilterType.REGEX, pattern={"org.jqiaofu.workflow.web.*"}))
//属性文件导入配置
@PropertySources(
        value = {
        		//@PropertySource(value = "classpath:sysconfig.properties", ignoreResourceNotFound = false)
        })

@Import(value = {
		JpaConfig.class, //JPA配置 
		//DataSourceConfig.class,  //数据源配置 
		ActivitiConfig.class, //activiti配置
		WebConfiguration.class 
		})
public class AppConfig {
	
}
