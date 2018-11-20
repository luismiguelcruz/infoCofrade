package com.infoCofrade.secretaria.statusPorHermano.dao.impl;

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
import com.infoCofrade.secretaria.statusPorHermano.dao.StatusPorHermanoDao;
import com.infoCofrade.secretaria.statusPorHermano.vo.StatusPorHermanoVO;


@Component("statusPorHermanoDaoImpl")
public class StatusPorHermanoDaoImpl implements StatusPorHermanoDao{
	private static StatusPorHermanoDao instance = new StatusPorHermanoDaoImpl();
	private Session session;
	
	private StatusPorHermanoDaoImpl(){}
	
	public static StatusPorHermanoDao getInstance(){
		if (instance == null) {
            synchronized(StatusPorHermanoDaoImpl.class) {
                if (instance == null) { 
                	instance = new StatusPorHermanoDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized StatusPorHermanoVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		StatusPorHermanoVO statusPorHermano = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from StatusPorHermanoVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				statusPorHermano = (StatusPorHermanoVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return statusPorHermano;
	}
	
	public synchronized List<StatusPorHermanoVO> findAll(Transaction transaction, String order){
		List<StatusPorHermanoVO> list = new ArrayList<StatusPorHermanoVO>();
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
					"SELECT * FROM "+schema+"STATUS_POR_HERMANO");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(StatusPorHermanoVO.class);
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
	
	public List<StatusPorHermanoVO> findUsingTemplate(Transaction transaction, StatusPorHermanoVO statusPorHermano, String order){
		List<StatusPorHermanoVO> list = new ArrayList<StatusPorHermanoVO>();
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
			
			if(statusPorHermano == null){
				throw new Exception("DAO Exception: StatusPorHermano is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT statusPorHermano.* FROM "+schema+"STATUS_POR_HERMANO statusPorHermano");
			StringBuilder conditions = new StringBuilder();
			if (statusPorHermano.getId() != null) {
				conditions.append(" statusPorHermano.id = " + statusPorHermano.getId() + " AND");
			}
			if (statusPorHermano.getIdStatus() != null) {
				conditions.append(" statusPorHermano.`idStatus` = " + statusPorHermano.getIdStatus() + " AND");
			}
			if (statusPorHermano.getIdHermano() != null) {
				conditions.append(" statusPorHermano.`idHermano` = " + statusPorHermano.getIdHermano() + " AND");
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
			query.addEntity("statusPorHermano", StatusPorHermanoVO.class);
			
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
	
	public List<StatusPorHermanoVO> findUsingExactTemplate(Transaction transaction, StatusPorHermanoVO statusPorHermano, String order){
		List<StatusPorHermanoVO> list = new ArrayList<StatusPorHermanoVO>();
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
			
			if(statusPorHermano == null){
				throw new Exception("DAO Exception: StatusPorHermano is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT statusPorHermano.* FROM "+schema+"STATUS_POR_HERMANO statusPorHermano");
			StringBuilder conditions = new StringBuilder();
			if (statusPorHermano.getId() != null) {
				conditions.append(" statusPorHermano.id = " + statusPorHermano.getId() + " AND");
			}
			if (statusPorHermano.getIdStatus() != null) {
				conditions.append(" statusPorHermano.`idStatus` = " + statusPorHermano.getIdStatus() + " AND");
			}
			if (statusPorHermano.getIdHermano() != null) {
				conditions.append(" statusPorHermano.`idHermano` = " + statusPorHermano.getIdHermano() + " AND");
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
			query.addEntity("statusPorHermano", StatusPorHermanoVO.class);
			
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
	public synchronized void createElement(Transaction transaction, StatusPorHermanoVO statusPorHermano) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (statusPorHermano == null) {
				throw new DaoException("Element is null");
			}
			
			if(statusPorHermano.getId() == null){
				session.save(statusPorHermano);
			} else {
				session.merge(statusPorHermano);
			}
			
			if (transaction == null) {
				currentTransaction.commit();
				if(statusPorHermano.getId() == null){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento actualizado correctamente"));
				}
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
	public synchronized void deleteElement(Transaction transaction, StatusPorHermanoVO statusPorHermano) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (statusPorHermano == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"STATUS_POR_HERMANO ");
			StringBuilder conditions = new StringBuilder();
			if (statusPorHermano.getId() != null) {
				conditions.append(" id = " + statusPorHermano.getId() + " AND");
			}
			if (statusPorHermano.getIdStatus() != null) {
				conditions.append(" `idStatus` = " + statusPorHermano.getIdStatus() + " AND");
			}
			if (statusPorHermano.getIdHermano() != null) {
				conditions.append(" `idHermano` = " + statusPorHermano.getIdHermano() + " AND");
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
	
	public synchronized void deleteElementByWhere(Transaction transaction, String where){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
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
					"DELETE FROM "+schema+"STATUS_POR_HERMANO ");
						
			if (where.length() > 0) {
				queryString.append(where);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.executeUpdate();
			
			if(transaction == null){
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Database info: ", "Elementos borrados correctamente"));
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
			if(transaction == null){
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error borrando status", ex.getMessage()));
			}
		}
	}
}