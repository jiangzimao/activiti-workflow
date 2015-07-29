/**
 * workflow
 * org.jqiaofu.workflow.config
 * AppInitializer.java
 * 
 * 2015年7月27日-下午2:57:29
 * 2015◎讯银金融-版权所有
 *
 */
package org.jqiaofu.workflow.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

/**
 *
 * AppInitializer
 * 
 * @auth zimao_jiang@epaybank.com
 * @data 2015年7月27日 下午2:57:29
 *
 */

@Order(0)
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
		// 使用InputStream得到一个资源文件
		InputStream inputstream = null;
		try {
			inputstream = this.getClass().getResourceAsStream("/sysconfig.properties");
			// new 一个Properties
			Properties properties = new Properties();
			properties.load(inputstream);
			servletContext.setInitParameter(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, properties.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME)); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(inputstream != null){
				try {
					inputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//Log4jConfigListener 
		servletContext.setInitParameter("log4jConfigLocation", "classpath:log4j.properties"); 
        servletContext.addListener(Log4jConfigListener.class); 
        
        servletContext.addListener(new RequestContextListener());
        
        super.onStartup(servletContext);
    }
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return new Filter[]{encodingFilter};
    }
	
}
