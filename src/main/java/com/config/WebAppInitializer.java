package com.config;

import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.Filter;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration.Dynamic;


//等於web.xml
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override //設定註冊=beans.config.xml 整個應用程式的相關設定
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootAppConfig.class};
//		return null;
	}

	@Override //設定註冊=mvc.servlet.xml Spring MVC的設定
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class[] {WebAppConfig.class};
//		return null;
		
	}

	@Override //設定註冊=Servlet mapping url-pattern
	protected String[] getServletMappings() {
		return new String[] {"/"};
//		return null;
	}

	@Override //設定註冊Filter
	protected Filter[] getServletFilters() {
		
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter("UTF-8",true);
//		OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
		return new Filter[] {characterEncodingFilter};	
	}

	
	
	/*
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement("c:/temp/upload/"));
	}
	*/
	
	
	
	
	
	

}
