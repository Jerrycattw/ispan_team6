package com.config;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


//等於mvc-servlet.xml
@Configuration //<context:annotation-config /> 宣告使用annotation
@EnableWebMvc //<mvc:annotation-driven /> 
@ComponentScan(basePackages = {"com"}) //<context:component-scan base-package="tw.jerry" />
@EnableAspectJAutoProxy
public class WebAppConfig implements WebMvcConfigurer {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		
		//依這邊設定為主
		configurer.enable();
		
	}
	
	@Bean
	public InternalResourceViewResolver irViewResolver() {
		InternalResourceViewResolver irv = new InternalResourceViewResolver("/WEB-INF/pages/",".jsp");
//		irv.setPrefix("/WEB-INF/pages/");
		//irv.setSuffix(".jsp");
		irv.setOrder(1);
		return irv;
	}
	
	
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("i18n.message");
		messageSource.setDefaultEncoding("UTF8");
		messageSource.setDefaultLocale(Locale.TAIWAN);
		return messageSource;
		
	}


	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		LocaleChangeInterceptor lcInterceptor = new LocaleChangeInterceptor();
		lcInterceptor.setParamName("locale");
		registry.addInterceptor(lcInterceptor).addPathPatterns("/**");
		
	}
	
	
	@Bean
	public SessionLocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}


	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		//去哪個虛擬路徑找資源
		registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/resources/images/");
		registry.addResourceHandler("/mycss/**").addResourceLocations("/WEB-INF/resources/mycss/");
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/resources/js/");
		
		

		
        registry.addResourceHandler("/restaurantIMG/**").addResourceLocations("file:///C:/upload/restaurantIMG/");
		
		
		

		registry.addResourceHandler("/ProductImg/**").addResourceLocations("file:///C:/upload/ProductImg");

	}


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addRedirectViewController("/", "HomePage.jsp");
		
		
//		registry.addViewController("/wonderland").setViewName("loginSystem");
		registry.addViewController("/reserve/AddRestaurant").setViewName("reserve/AddRestaurant");
<<<<<<< HEAD
		registry.addViewController("/reserve/AddTableType").setViewName("reserve/AddTableType");
		registry.addViewController("/reserve/GetListRestaurants").setViewName("reserve/GetListRestaurants");
=======
		registry.addViewController("/reserve/GetListRestaurants").setViewName("/reserve/GetListRestaurants");
		registry.addViewController("/Product/AddProduct").setViewName("Product/AddProduct");
>>>>>>> shopping0920
		
	}
	
	
	@Bean
	public StandardServletMultipartResolver	multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	
	
	@Bean
	public MappingJackson2JsonView jsonView() {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setPrettyPrint(true);
		return jsonView;
	}
	
	
	
	@Bean
	public ContentNegotiatingViewResolver cViewResolver() {
		ContentNegotiatingViewResolver cViewResolver = new ContentNegotiatingViewResolver();
		ArrayList<View> list = new ArrayList<View>();
		list.add(jsonView());
		cViewResolver.setDefaultViews(list);
		return cViewResolver;
	}
	
	
	
}
