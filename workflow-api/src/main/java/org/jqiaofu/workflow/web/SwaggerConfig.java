/**
 * workflow-api
 * org.jqiaofu.workflow.web
 * SwaggerConfig.java
 * 
 * 2015年7月29日-下午5:14:28
 * 2015◎讯银金融-版权所有
 *
 */
package org.jqiaofu.workflow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 *
 * SwaggerConfig
 * @auth zimao_jiang@epaybank.com
 * @data 2015年7月29日 下午5:14:28
 *
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {
	
	private SpringSwaggerConfig springSwaggerConfig;

	/**
	 * Required to autowire SpringSwaggerConfig
	 */
	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}

	/**
	 * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
	 * framework - allowing for multiple swagger groups i.e. same code base
	 * multiple swagger resource listings. 
	 */
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
				.apiVersion("V1.0")
				.apiInfo(apiInfo())
				.includePatterns(".*?");
	}
	
	private ApiInfo apiInfo()
    {
        ApiInfo apiInfo = new ApiInfo(
                "My Apps API Title", 
                "My Apps API Description",
                "My Apps API terms of service", 
                "My Apps API Contact Email", 
                "My Apps API Licence Type",
                "My Apps API License URL");
        return apiInfo;
    }
}