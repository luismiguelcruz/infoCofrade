package com.infoCofrade.administration.provincia.dao.impl;

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

import com.infoCofrade.administration.ataqueSQL.dao.impl.AtaqueSQLDaoImpl;
import com.infoCofrade.administration.provincia.dao.ProvinciaDao;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;


@Component("provinciaDaoImpl")
public class ProvinciaDaoImpl implements ProvinciaDao{
	private static ProvinciaDao instance = new ProvinciaDaoImpl();
	private Session session;
	
	private ProvinciaDaoImpl(){}
	
	public static ProvinciaDao getInstance(){
		if (instance == null) {
            synchronized(ProvinciaDaoImpl.class) {
                if (instance == null) { 
                	instance = new ProvinciaDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized ProvinciaVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		ProvinciaVO provincia = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from ProvinciaVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				// Probamos si el usuario esta intentando hacer sql injection
				if(AbstractBean.preventSqlInjection(queryString)){
					currentTransaction.rollback();
					AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
							.getCurrentInstance().getELContext().getELResolver()
							.getValue(FacesContext.getCurrentInstance().getELContext(),
									null, "ataqueSQLDaoImpl");
					
					ataqueSQLDao.createElement(queryString);
					
					throw new SqlInjectionException("Wrong statements for the query!!");
				}
				provincia = (ProvinciaVO)session.createQuery(queryString.toString()).uniqueResult();
			}
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex){
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}
		
		return provincia;
	}
	
	public synchronized List<ProvinciaVO> findAll(Transaction transaction, String order){
		List<ProvinciaVO> list = new ArrayList<ProvinciaVO>();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).
				getSettings().getDefaultSchemaName();
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
					"SELECT * FROM "+schema+"PROVINCIA");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			// Probamos si el usuario esta intentando hacer sql injection
			if(AbstractBean.preventSqlInjection(queryString)){
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance().getELContext().getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "ataqueSQLDaoImpl");
				
				ataqueSQLDao.createElement(queryString);
				
				throw new SqlInjectionException("Wrong statements for the query!!");
			}
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(ProvinciaVO.class);
			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}

		return list;
	}
	
	public List<ProvinciaVO> findUsingTemplate(Transaction transaction, ProvinciaVO provincia, String order){
		List<ProvinciaVO> list = new ArrayList<ProvinciaVO>();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).
					getSettings().getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(provincia == null){
				throw new Exception("DAO Exception: Provincia is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT provincia.* FROM "+schema+"PROVINCIA provincia");
			StringBuilder conditions = new StringBuilder();
			if (provincia.getId() != null) {
				conditions.append(" provincia.id = " + provincia.getId() + " AND");
			}
			if (provincia.getProvincia() != null && !provincia.getProvincia().isEmpty()){
				conditions.append(" UPPER(provincia.`provincia`) like '%"+
						provincia.getProvincia().toUpperCase()+"%' AND");
			}
			if (provincia.getProvinciaSeo() != null && !provincia.getProvinciaSeo().isEmpty()){
				conditions.append(" UPPER(provincia.`provinciaSeo`) like '%"+
						provincia.getProvinciaSeo().toUpperCase()+"%' AND");
			}
			if (provincia.getCodigoProvincia() != null && !provincia.getCodigoProvincia().isEmpty()){
				conditions.append(" UPPER(provincia.`codigoProvincia`) = '"+
						provincia.getCodigoProvincia().toUpperCase()+"' AND");
			}
			if (provincia.getIdPais() != null){
				conditions.append(" UPPER(provincia.`idPais`) = '"+ provincia.getIdPais()+"' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			// Probamos si el usuario esta intentando hacer sql injection
			if(AbstractBean.preventSqlInjection(queryString)){
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance().getELContext().getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "ataqueSQLDaoImpl");
				
				ataqueSQLDao.createElement(queryString);
				
				throw new SqlInjectionException("Wrong statements for the query!!");
			}
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("provincia", ProvinciaVO.class);
			
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
		} catch (SqlInjectionException ex) {
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	public List<ProvinciaVO> findUsingExactTemplate(Transaction transaction, ProvinciaVO provincia, String order){
		List<ProvinciaVO> list = new ArrayList<ProvinciaVO>();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).
				getSettings().getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(provincia == null){
				throw new Exception("DAO Exception: Provincia is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT provincia.* FROM "+schema+"PROVINCIA provincia");
			StringBuilder conditions = new StringBuilder();
			if (provincia.getId() != null) {
				conditions.append(" provincia.id = " + provincia.getId() + " AND");
			}
			if(provincia.getProvincia() != null && !provincia.getProvincia().isEmpty()){
				conditions.append(" UPPER(provincia.`provincia`) like '%"+
						provincia.getProvincia().toUpperCase()+"%' AND");
			}
			if(provincia.getProvinciaSeo() != null && !provincia.getProvinciaSeo().isEmpty()){
				conditions.append(" UPPER(provincia.`provinciaSeo`) like '%"+
						provincia.getProvinciaSeo().toUpperCase()+"%' AND");
			}
			if(provincia.getCodigoProvincia() != null && !provincia.getCodigoProvincia().isEmpty()){
				conditions.append(" UPPER(provincia.`codigoProvincia`) = '"+
						provincia.getCodigoProvincia().toUpperCase()+"' AND");
			}
			if(provincia.getIdPais() != null){
				conditions.append(" UPPER(provincia.`idPais`) = '"+ provincia.getIdPais()+"' AND");
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			// Probamos si el usuario esta intentando hacer sql injection
			if(AbstractBean.preventSqlInjection(queryString)){
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance().getELContext().getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "ataqueSQLDaoImpl");
				
				ataqueSQLDao.createElement(queryString);
				
				throw new SqlInjectionException("Wrong statements for the query!!");
			}
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("provincia", ProvinciaVO.class);
			
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
		} catch (SqlInjectionException ex) {
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	
	public List<ProvinciaVO> findAutoComplete(Transaction transaction, ProvinciaVO provincia, String order){
		List<ProvinciaVO> list = new ArrayList<ProvinciaVO>();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).
				getSettings().getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(provincia == null){
				throw new Exception("DAO Exception: Provincia is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT provincia.* FROM "+schema+"PROVINCIA provincia");
			StringBuilder conditions = new StringBuilder();
			if (provincia.getId() != null) {
				conditions.append(" provincia.id = " + provincia.getId() + " AND");
			}
			if(provincia.getProvincia() != null && !provincia.getProvincia().isEmpty()){
				conditions.append(" UPPER(provincia.`provincia`) like '"+
						provincia.getProvincia().toUpperCase()+"%' AND");
			}
			if(provincia.getProvinciaSeo() != null && !provincia.getProvinciaSeo().isEmpty()){
				conditions.append(" UPPER(provincia.`provinciaSeo`) like '"+
						provincia.getProvinciaSeo().toUpperCase()+"%' AND");
			}
			if(provincia.getCodigoProvincia() != null && !provincia.getCodigoProvincia().isEmpty()){
				conditions.append(" UPPER(provincia.`codigoProvincia`) = '"+
						provincia.getCodigoProvincia().toUpperCase()+"' AND");
			}
			if(provincia.getIdPais() != null){
				conditions.append(" UPPER(provincia.`idPais`) = '"+ provincia.getIdPais()+"' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			// Probamos si el usuario esta intentando hacer sql injection
			if(AbstractBean.preventSqlInjection(queryString)){
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance().getELContext().getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "ataqueSQLDaoImpl");
				
				ataqueSQLDao.createElement(queryString);
				
				throw new SqlInjectionException("Wrong statements for the query!!");
			}
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("provincia", ProvinciaVO.class);
			
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
		} catch (SqlInjectionException ex) {
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
	public synchronized void createElement(Transaction transaction, ProvinciaVO provincia) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (provincia == null) {
				throw new DaoException("Element is null");
			}
			
			if(provincia.getId() == null){
				session.save(provincia);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(provincia);
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
	public synchronized void deleteElement(Transaction transaction, ProvinciaVO provincia) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (provincia == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+ "PROVINCIA ");
			StringBuilder conditions = new StringBuilder();
			if (provincia.getId() != null) {
				conditions.append(" provincia.id = " + provincia.getId() + " AND");
			}
			if(provincia.getProvincia() != null && !provincia.getProvincia().isEmpty()){
				conditions.append(" UPPER(provincia.`provincia`) like '%"+
						provincia.getProvincia().toUpperCase()+"%' AND");
			}
			if(provincia.getProvinciaSeo() != null && !provincia.getProvinciaSeo().isEmpty()){
				conditions.append(" UPPER(provincia.`provinciaSeo`) like '%"+
						provincia.getProvinciaSeo().toUpperCase()+"%' AND");
			}
			if(provincia.getCodigoProvincia() != null && !provincia.getCodigoProvincia().isEmpty()){
				conditions.append(" UPPER(provincia.`codigoProvincia`) = '"+
						provincia.getCodigoProvincia().toUpperCase()+"' AND");
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