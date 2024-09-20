package com.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


// = beans.config.xml
@Configuration //宣告為java組態檔
@ComponentScan(basePackages = {"com"})
@EnableTransactionManagement //<tx:annotation-driven transaction-manager="transactionManager"/>
public class RootAppConfig {
	
	
	@Bean
	public DataSource dataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean jndiBean = new JndiObjectFactoryBean();
		//jndiBean.setJndiName("java:comp/env/connectMySQLConn/OrderSystem");
		jndiBean.setJndiName("java:comp/env/hibernate/EEIT187-6");
		jndiBean.afterPropertiesSet();
		DataSource dataSource = (DataSource)jndiBean.getObject();
		return dataSource;
	}
	
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() throws IllegalArgumentException, NamingException {
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com");
		
		Properties properties = new Properties();
		//properties.put("hibernate.dialect", org.hibernate.dialect.MySQLDialect.class);
		properties.put("hibernate.dialect", org.hibernate.dialect.SQLServerDialect.class);
		properties.put("hibernate.show_sql", Boolean.TRUE);
		properties.put("hibernate.format_sql", Boolean.TRUE);
		//properties.put("hibernate.current_session_context_class", "thread");
		//properties.put("hibernate.allow_update_outside_transaction", Boolean.TRUE);
		
		sessionFactory.setHibernateProperties(properties);
		//sessionFactory.setHibernateProperties(additionalProperties());
		
		return sessionFactory;
		
	}
	
	/*
	private Properties additionalProperties() {
		Properties properties = new Properties();
		//properties.put("hibernate.dialect", org.hibernate.dialect.MySQLDialect.class);
		properties.put("hibernate.dialect", org.hibernate.dialect.SQLServerDialect.class);
		properties.put("hibernate.show_sql", Boolean.TRUE);
		properties.put("hibernate.format_sql", Boolean.TRUE);
		//properties.put("hibernate.current_session_context_class", "thread");
		return properties;
	}
	*/
	
	@Bean
	public HibernateTransactionManager transactionManager() throws IllegalArgumentException, NamingException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
		
	}
	
//	@Bean
//	public Session session() throws HibernateException, IllegalArgumentException, NamingException {
//		return sessionFactory().getObject().getCurrentSession();
//	}
	
	
	
}
