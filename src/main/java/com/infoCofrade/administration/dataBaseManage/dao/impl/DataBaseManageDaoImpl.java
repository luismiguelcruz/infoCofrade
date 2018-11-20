package com.infoCofrade.administration.dataBaseManage.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.infoCofrade.administration.dataBaseManage.dao.DataBaseManageDao;
import com.infoCofrade.common.hibernate.ConfHibernate;


@Component("dataBaseManageDaoImpl")
public class DataBaseManageDaoImpl implements DataBaseManageDao{
	private static DataBaseManageDao instance = new DataBaseManageDaoImpl();
	private Session session;
	
	private DataBaseManageDaoImpl(){}
	
	public static DataBaseManageDao getInstance(){
		if (instance == null) {
            synchronized(DataBaseManageDaoImpl.class) {
                if (instance == null) { 
                	instance = new DataBaseManageDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized void createViews(Transaction transaction, Boolean hermandadSelected){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;
		List<String> list = new ArrayList<String>();

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("select database()");
			
			Query query = session.createSQLQuery(queryString.toString());
			list = query.list();
			if(list.size() > 0){
				String schema = list.get(0);
				
				String relativePath = "/resources/SQLFiles/"+schema.toLowerCase()+"/views.sql"; 
				ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
		                .getExternalContext().getContext();
		    	String webContentRoot = servletContext.getRealPath("/");
		    	File f = new File(webContentRoot + relativePath);
		    	
		    	Scanner scanner = new Scanner(f);
		    	scanner.useDelimiter(";");
		    	String line;
		    	
		    	while(scanner.hasNextLine()){
		    		try{
		    			line = scanner.next().trim();
			    		if(!line.startsWith("--") && !line.equalsIgnoreCase("")){
			    			if(line.startsWith("DROP TABLE") || line.startsWith("DROP VIEW")){
			    				try{
				    				System.out.println("----- Ejecutando query: \n"+line+";");
					    			
					    			query = session.createSQLQuery(line);
							    	query.executeUpdate();
			    				} catch (Exception e) {
			    					System.out.println("***** Imposible eliminar la tabla, ya que no existe");
								}
			    			} else {
			    				System.out.println("----- Ejecutando query: \n"+line+";");
				    			
				    			query = session.createSQLQuery(line);
						    	query.executeUpdate();
			    			}
			    		}
		    		} catch(NoSuchElementException e){
		    		}
		    	}

		    	currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Comandos SQL creados: ",
								"Se han creado las vistas en la base de datos correctamente"));	
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			currentTransaction.rollback();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido resetear la base de datos", ex.getMessage()));
		}
	}
	
	public synchronized void createInserts(Transaction transaction, Boolean hermandadSelected){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;
		List<String> list = new ArrayList<String>();

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("select database()");
			
			Query query = session.createSQLQuery(queryString.toString());
			list = query.list();
			if(list.size() > 0){
				String schema = list.get(0);
				
				String relativePath = "/resources/SQLFiles/"+schema.toLowerCase()+"/inserts.sql"; 
				ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
		                .getExternalContext().getContext();
		    	String webContentRoot = servletContext.getRealPath("/");
		    	File f = new File(webContentRoot + relativePath);
		    	
		    	Scanner scanner = new Scanner(f);
		    	scanner.useDelimiter(";");
		    	String line;
		    	
		    	while(scanner.hasNextLine()){
		    		try{
		    			line = scanner.next().trim();
		    			if(!line.startsWith("--") && !line.equalsIgnoreCase("")){
			    			System.out.println("----- Ejecutando query: \n"+line+";");
				    			
				    		query = session.createSQLQuery(line);
						    query.executeUpdate();
			    		}
		    		} catch(NoSuchElementException e){
		    		}
		    	}

		    	currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Comandos SQL creados: ",
								"Se han insertado los elementos en la base de datos correctamente"));	
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			currentTransaction.rollback();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido resetear la base de datos", ex.getMessage()));
		}
	}
	
	public synchronized void resetDataBase(Transaction transaction){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;
		List<String> list = new ArrayList<String>();

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("select database()");
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			list = query.list();
			if(list.size() > 0){
				String schema = list.get(0);
				
				queryString = new StringBuilder("Select concat('DROP TABLE "+schema+".', table_name,';') from information_schema.TABLES WHERE table_schema = '"+schema+"'");
								
				query = session.createSQLQuery(queryString.toString());
				list = query.list();
				
				for(String it: list){
					session.createSQLQuery(it);
				}
				
				list = query.list();
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se ha reseteado la base de datos correctamente"));	
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			currentTransaction.rollback();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido resetear la base de datos", ex.getMessage()));
		}
	}
}
