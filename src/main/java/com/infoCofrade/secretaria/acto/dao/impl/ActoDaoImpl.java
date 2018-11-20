package com.infoCofrade.secretaria.acto.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Component;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.hibernate.ConfHibernate;
import com.infoCofrade.secretaria.acto.dao.ActoDao;
import com.infoCofrade.secretaria.acto.vo.ActoVO;


@Component("actoDaoImpl")
public class ActoDaoImpl implements ActoDao{
	private static ActoDao instance = new ActoDaoImpl();
	private Session session;
	
	private ActoDaoImpl(){}
	
	public static ActoDao getInstance(){
		if (instance == null) {
            synchronized(ActoDaoImpl.class) {
                if (instance == null) { 
                	instance = new ActoDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized ActoVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		ActoVO acto = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from ActoVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				acto = (ActoVO)session.createQuery(queryString.toString()).uniqueResult();
			}
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex){
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}
		
		return acto;
	}
	
	public synchronized List<ActoVO> findAll(Transaction transaction, String order){
		List<ActoVO> list = new ArrayList<ActoVO>();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"SELECT * FROM "+schema+"ACTO");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(ActoVO.class);
			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}

		return list;
	}
	
	public List<ActoVO> findActoByYear(Transaction transaction, Integer anyo, String order){
		List<ActoVO> list = new ArrayList<ActoVO>();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(anyo == null){
				throw new Exception("DAO Exception: Año is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"SELECT * FROM "+schema+"ACTO acto where fecha IS NOT NULL and fecha >= '"+
							anyo+"-01-01' and fecha <= '"+anyo+"12-31'");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(ActoVO.class);
			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}

		return list;
	}
	
	public List<ActoVO> findUsingTemplate(Transaction transaction, ActoVO acto, String order){
		List<ActoVO> list = new ArrayList<ActoVO>();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(acto == null){
				throw new Exception("DAO Exception: Acto is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT acto.* FROM "+schema+"ACTO acto");
			StringBuilder conditions = new StringBuilder();
			if (acto.getId() != null) {
				conditions.append(" acto.id = " + acto.getId() + " AND");
			}
			if(acto.getTitulo() != null && !acto.getTitulo().isEmpty()){
				conditions.append(" UPPER(acto.`titulo`) like '%"+acto.getTitulo().toUpperCase()+"%' AND");
			}
			if(acto.getFecha() != null){
				conditions.append(" acto.`fecha` = '"+acto.getFecha()+"' AND");
			}
			if(acto.getLugar() != null && !acto.getLugar().isEmpty()){
				conditions.append(" UPPER(acto.`lugar`) like '%"+acto.getLugar().toUpperCase()+"%' AND");
			}
			if(acto.getIdLocalidad() != null){
				conditions.append(" acto.`idLocalidad` = '"+acto.getIdLocalidad()+"' AND");
			}
			if(acto.getDescripcion() != null && !acto.getDescripcion().isEmpty()){
				conditions.append(" UPPER(acto.`descripcion`) like '%"+acto.getDescripcion().toUpperCase()+"%' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("acto", ActoVO.class);
			
			list = query.list();
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se han encontrado elementos", ex.getMessage()));
			}
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	public List<ActoVO> findUsingExactTemplate(Transaction transaction, ActoVO acto, String order){
		List<ActoVO> list = new ArrayList<ActoVO>();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(acto == null){
				throw new Exception("DAO Exception: Acto is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT acto.* FROM "+schema+"ACTO acto");
			StringBuilder conditions = new StringBuilder();
			if (acto.getId() != null) {
				conditions.append(" acto.id = " + acto.getId() + " AND");
			}
			if(acto.getTitulo() != null && !acto.getTitulo().isEmpty()){
				conditions.append(" UPPER(acto.`titulo`) = '"+acto.getTitulo().toUpperCase()+"' AND");
			}
			if(acto.getFecha() != null){
				conditions.append(" acto.`fecha` = '"+acto.getFecha()+"' AND");
			}
			if(acto.getLugar() != null && !acto.getLugar().isEmpty()){
				conditions.append(" UPPER(acto.`lugar`) = '"+acto.getLugar().toUpperCase()+"' AND");
			}
			if(acto.getIdLocalidad() != null){
				conditions.append(" acto.`idLocalidad` = '"+acto.getIdLocalidad()+"' AND");
			}
			if(acto.getDescripcion() != null && !acto.getDescripcion().isEmpty()){
				conditions.append(" UPPER(acto.`descripcion`) = '"+acto.getDescripcion().toUpperCase()+"' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("acto", ActoVO.class);
			
			list = query.list();
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se encontraron elementos", ex.getMessage()));
			}
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	
	/**
	 * Create an element into the Database
	 */
	public synchronized void createElement(Transaction transaction, ActoVO acto) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (acto == null) {
				throw new DaoException("Element is null");
			}
			
			if(acto.getId() == null){
				session.save(acto);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(acto);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento actualizado correctamente"));
			}
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cannot create element", ex.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}
	}

	/**
	 * Delete an element from the Database
	 */
	public synchronized void deleteElement(Transaction transaction, ActoVO acto) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (acto == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"ACTO ");
			StringBuilder conditions = new StringBuilder();
			if (acto.getId() != null) {
				conditions.append(" id = " + acto.getId() + " AND");
			}
			if(acto.getTitulo() != null && !acto.getTitulo().isEmpty()){
				conditions.append(" UPPER(`titulo`) = '"+acto.getTitulo().toUpperCase()+"' AND");
			}
			if(acto.getFecha() != null){
				conditions.append(" `fecha` = '"+acto.getFecha()+"' AND");
			}
			if(acto.getLugar() != null && !acto.getLugar().isEmpty()){
				conditions.append(" UPPER(`lugar`) = '"+acto.getLugar().toUpperCase()+"' AND");
			}
			if(acto.getIdLocalidad() != null){
				conditions.append(" `idLocalidad` = '"+acto.getIdLocalidad()+"' AND");
			}
			if(acto.getDescripcion() != null && !acto.getDescripcion().isEmpty()){
				conditions.append(" UPPER(`descripcion`) = '"+acto.getDescripcion().toUpperCase()+"' AND");
			}
			
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.executeUpdate();
			
			if (transaction == null) {
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento borrado correctamente"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error borrando el elemento", ""));
			}
		}
	}
}