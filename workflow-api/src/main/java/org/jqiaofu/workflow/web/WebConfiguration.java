/**
 * workflow-api
 * org.jqiaofu.workflow.web
 * WebConfiguration.java
 * 
 * 2015年7月29日-下午5:05:17
 * 2015◎讯银金融-版权所有
 *
 */
package org.jqiaofu.workflow.web;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * WebConfiguration
 * @auth zimao_jiang@epaybank.com
 * @data 2015年7月29日 下午5:05:17
 *
 */
@Configuration
@EnableWebMvc
//@EnableSpringConfigured
@ComponentScan(basePackages={"org.jqiaofu.workflow"})
public class WebConfiguration extends WebMvcConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(WebConfiguration.class);
	
	@Bean
	public UrlBasedViewResolver viewResolver() {
	    UrlBasedViewResolver resolver = new UrlBasedViewResolver();
//	    resolver = new InternalResourceViewResolver();
	    resolver.setPrefix("/WEB-INF/pages/");
	    resolver.setSuffix(".jsp");
	    resolver.setViewClass(JstlView.class);
	    return resolver;
	}

	@Bean
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    //messageSource.setBasename("messages");
	    messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("classpath:i18n/messages", "classpath:i18n/errors");
	    return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	    CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
	    cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
	    return cookieLocaleResolver;
	}
	
	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub
		
	}
	

	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		log.debug("init convert is start !!!!!");
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
	    stringConverter.setWriteAcceptCharset(false);
	    converters.add(new ByteArrayHttpMessageConverter());
	    converters.add(stringConverter);
	    converters.add(new ResourceHttpMessageConverter());
	    converters.add(new SourceHttpMessageConverter());
	    converters.add(new AllEncompassingFormHttpMessageConverter());
	    converters.add(new Jaxb2RootElementHttpMessageConverter());
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    converter.setObjectMapper(new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
	    converters.add(converter);
	}

	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		
	}

	public Validator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).useJaf(false)  
        .favorParameter(true).parameterName("mediaType")  
        .ignoreAcceptHeader(true).  
        defaultContentType(MediaType.APPLICATION_JSON).  
        mediaType("xml", MediaType.APPLICATION_XML).  
        mediaType("json", MediaType.APPLICATION_JSON); 
	}

	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		// TODO Auto-generated method stub
		
	}

	public void configurePathMatch(PathMatchConfigurer configurer) {
		// TODO Auto-generated method stub
		
	}

	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		
	}

	public void addReturnValueHandlers(
			List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub
		
	}

	public void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> exceptionResolvers) {
		// TODO Auto-generated method stub
		
	}

	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	    localeChangeInterceptor.setParamName("lang");
	    registry.addInterceptor(localeChangeInterceptor);
	}

	public MessageCodesResolver getMessageCodesResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
		
	}

	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/rest/api-docs/**").addResourceLocations("/swagger-ui/");  
//        registry.addResourceHandler("**/*.html").addResourceLocations("/"); 
	}

	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		
	}
}
