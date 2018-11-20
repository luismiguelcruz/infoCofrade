package com.infoCofrade.tesoreria.ingreso.dao.impl;

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
import com.infoCofrade.tesoreria.ingreso.dao.IngresoDao;
import com.infoCofrade.tesoreria.ingreso.vo.IngresoVO;


@Component("ingresoDaoImpl")
public class IngresoDaoImpl implements IngresoDao{
	private static IngresoDao instance = new IngresoDaoImpl();
	private Session session;
	
	private IngresoDaoImpl(){}
	
	public static IngresoDao getInstance(){
		if (instance == null) {
            synchronized(IngresoDaoImpl.class) {
                if (instance == null) { 
                	instance = new IngresoDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized IngresoVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		IngresoVO ingreso = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from IngresoVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				ingreso = (IngresoVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return ingreso;
	}
	
	public synchronized List<IngresoVO> findAll(Transaction transaction, String order){
		List<IngresoVO> list = new ArrayList<IngresoVO>();
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
					"SELECT * FROM "+schema+"INGRESO");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(IngresoVO.class);
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
	
	public List<IngresoVO> findUsingTemplate(Transaction transaction, IngresoVO ingreso, String order){
		List<IngresoVO> list = new ArrayList<IngresoVO>();
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
			
			if(ingreso == null){
				throw new Exception("DAO Exception: Ingreso is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT ingreso.* FROM "+schema+"INGRESO ingreso");
			StringBuilder conditions = new StringBuilder();
			if (ingreso.getId() != null) {
				conditions.append(" ingreso.id = " + ingreso.getId() + " AND");
			}
			if (ingreso.getIdCuenta() != null) {
				conditions.append(" ingreso.`idCuenta` = " + ingreso.getIdCuenta() + " AND");
			}
			if (ingreso.getCuantia() != null) {
				conditions.append(" ingreso.`cuantia` = " + ingreso.getCuantia() + " AND");
			}
			if (ingreso.getConcepto() != null && !ingreso.getConcepto().isEmpty()) {
				conditions.append(" ingreso.`concepto` like '%" + ingreso.getConcepto() + "%' AND");
			}
			if (ingreso.getAnyo() != null) {
				conditions.append(" ingreso.`anyo` = " + ingreso.getAnyo() + " AND");
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
			query.addEntity("ingreso", IngresoVO.class);
			
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
	
	public List<IngresoVO> findUsingExactTemplate(Transaction transaction, IngresoVO ingreso, String order){
		List<IngresoVO> list = new ArrayList<IngresoVO>();
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
			
			if(ingreso == null){
				throw new Exception("DAO Exception: Ingreso is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT ingreso.* FROM "+schema+"INGRESO ingreso");
			StringBuilder conditions = new StringBuilder();
			if (ingreso.getId() != null) {
				conditions.append(" ingreso.id = " + ingreso.getId() + " AND");
			}
			if (ingreso.getIdCuenta() != null) {
				conditions.append(" ingreso.`idCuenta` = " + ingreso.getIdCuenta() + " AND");
			}
			if (ingreso.getCuantia() != null) {
				conditions.append(" ingreso.`cuantia` = " + ingreso.getCuantia() + " AND");
			}
			if (ingreso.getConcepto() != null && !ingreso.getConcepto().isEmpty()) {
				conditions.append(" ingreso.`concepto` = '" + ingreso.getConcepto() + "' AND");
			}
			if (ingreso.getAnyo() != null) {
				conditions.append(" ingreso.`anyo` = " + ingreso.getAnyo() + " AND");
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
			query.addEntity("ingreso", IngresoVO.class);
			
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
	public synchronized void createElement(Transaction transaction, IngresoVO ingreso) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (ingreso == null) {
				throw new DaoException("Element is null");
			}
			
			if(ingreso.getId() == null){
				session.save(ingreso);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(ingreso);
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
	public synchronized void deleteElement(Transaction transaction, IngresoVO ingreso) {
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
			
			if (ingreso == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"INGRESO ");
			StringBuilder conditions = new StringBuilder();
			if (ingreso.getId() != null) {
				conditions.append(" id = " + ingreso.getId() + " AND");
			}
			if (ingreso.getIdCuenta() != null) {
				conditions.append(" `idCuenta` = " + ingreso.getIdCuenta() + " AND");
			}
			if (ingreso.getCuantia() != null) {
				conditions.append(" `cuantia` = " + ingreso.getCuantia() + " AND");
			}
			if (ingreso.getConcepto() != null && !ingreso.getConcepto().isEmpty()) {
				conditions.append(" `concepto` = '" + ingreso.getConcepto() + "' AND");
			}
			if (ingreso.getAnyo() != null) {
				conditions.append(" `anyo` = " + ingreso.getAnyo() + " AND");
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