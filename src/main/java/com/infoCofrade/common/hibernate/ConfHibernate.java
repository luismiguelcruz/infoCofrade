package com.infoCofrade.common.hibernate;

import java.io.File;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.bean.Constants;

public class ConfHibernate {
		private static SessionFactory sessionFactory;
		private static ServiceRegistry serviceRegistry;
		
		static {
			try {
				Configuration configuration = new Configuration();
				configuration.configure();
				
			    serviceRegistry = new ServiceRegistryBuilder().applySettings(
			    						configuration.getProperties()).buildServiceRegistry();
			    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (HibernateException e) {
				System.err.println("Error in creating SessionFactory object."
	                    + e.getMessage());
	            throw new HibernateException(e);
			}
		}
		
		public static void changeSessionFactory(){
			Configuration configuration = new Configuration();
			
			try {
				if(AbstractBean.hibernateConfFileName == null || AbstractBean.hibernateConfFileName.isEmpty()){
					configuration.configure();
			    } else {
			    	ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
			                .getExternalContext().getContext();
			    	String webContentRoot = servletContext.getRealPath("/");
			    	File f = new File(webContentRoot + "/WEB-INF/classes/" + AbstractBean.hibernateConfFileName);
			    	configuration.configure(f);
			    	//configuration.configure(AbstractBean.hibernateConfFileName);
			    }
			} catch (HibernateException e) {
				System.err.println("Error in creating SessionFactory object."
	                    + e.getMessage());
				AbstractBean.hibernateConfFileName = null;
				configuration.configure();
				
	            throw new HibernateException(e);
			} finally {
				serviceRegistry = new ServiceRegistryBuilder().applySettings(
						configuration.getProperties()).buildServiceRegistry();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			}
		}
		
		public static SessionFactory getSessionFactory(){
			if(sessionFactory == null){
				changeSessionFactory();
			}
			
		    return sessionFactory;
		}
		
		public static void shutdown() {
			// Close caches and connection pools
			getSessionFactory().close();
		}
}