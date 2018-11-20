package com.infoCofrade.administration.localidad.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.stereotype.Component;

import com.infoCofrade.administration.ataqueSQL.dao.impl.AtaqueSQLDaoImpl;
import com.infoCofrade.administration.localidad.dao.LocalidadDao;
import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;


@Component("localidadDaoImpl")
public class LocalidadDaoImpl implements LocalidadDao{
	private static LocalidadDao instance = new LocalidadDaoImpl();
	private Session session;
	
	private LocalidadDaoImpl(){}
	
	public static LocalidadDao getInstance(){
		if (instance == null) {
            synchronized(LocalidadDaoImpl.class) {
                if (instance == null) { 
                	instance = new LocalidadDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized LocalidadVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		LocalidadVO localidad = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from LocalidadVO");
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
				localidad = (LocalidadVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return localidad;
	}
	
	public synchronized List<LocalidadVO> findAll(Transaction transaction, String order){
		List<LocalidadVO> list = new ArrayList<LocalidadVO>();
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
					"SELECT * FROM "+schema+"LOCALIDAD");
			
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
			query.addEntity(LocalidadVO.class);
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
	
	public List<LocalidadVO> findUsingTemplate(Transaction transaction, LocalidadVO localidad, String order){
		List<LocalidadVO> list = new ArrayList<LocalidadVO>();
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
			
			if(localidad == null){
				throw new Exception("DAO Exception: Localidad is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT localidad.* FROM "+schema+"LOCALIDAD localidad");
			StringBuilder conditions = new StringBuilder();
			if (localidad.getId() != null) {
				conditions.append(" localidad.id = " + localidad.getId() + " AND");
			}
			if (localidad.getIdProvincia() != null) {
				conditions.append(" localidad.`idProvincia` = " + localidad.getIdProvincia() + " AND");
			}
			if(localidad.getLocalidad() != null && !localidad.getLocalidad().isEmpty()){
				conditions.append(" UPPER(localidad.`localidad`) like '%"+
						localidad.getLocalidad().toUpperCase()+"%' AND");
			}
			if(localidad.getLocalidadSeo() != null && !localidad.getLocalidadSeo().isEmpty()){
				conditions.append(" UPPER(localidad.`localidadSeo`) like '%"+
						localidad.getLocalidadSeo().toUpperCase()+"%' AND");
			}
			if(localidad.getCodigoPostal() != null){
				conditions.append(" localidad.`codigoPostal` = '"+localidad.getCodigoPostal()+"' AND");
			}
			if(localidad.getLatitud() != null){
				conditions.append(" localidad.`latitud` = '"+localidad.getLatitud()+"' AND");
			}
			if(localidad.getLongitud() != null){
				conditions.append(" localidad.`longitud` = '"+localidad.getLongitud()+"' AND");
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
			query.addEntity("localidad", LocalidadVO.class);
			
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
	
	public List<LocalidadVO> findUsingExactTemplate(Transaction transaction, LocalidadVO localidad, String order){
		List<LocalidadVO> list = new ArrayList<LocalidadVO>();
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
			
			if(localidad == null){
				throw new Exception("DAO Exception: Localidad is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT localidad.* FROM "+schema+"LOCALIDAD localidad");
			StringBuilder conditions = new StringBuilder();
			if (localidad.getId() != null) {
				conditions.append(" localidad.id = " + localidad.getId() + " AND");
			}
			if (localidad.getIdProvincia() != null) {
				conditions.append(" localidad.`idProvincia` = " + localidad.getIdProvincia() + " AND");
			}
			if(localidad.getLocalidad() != null && !localidad.getLocalidad().isEmpty()){
				conditions.append(" UPPER(localidad.`localidad`) = '"+
						localidad.getLocalidad().toUpperCase()+"' AND");
			}
			if(localidad.getLocalidadSeo() != null && !localidad.getLocalidadSeo().isEmpty()){
				conditions.append(" UPPER(localidad.`localidadSeo`) = '"+
						localidad.getLocalidadSeo().toUpperCase()+"' AND");
			}
			if(localidad.getCodigoPostal() != null){
				conditions.append(" localidad.`codigoPostal` = '"+localidad.getCodigoPostal()+"' AND");
			}
			if(localidad.getLatitud() != null){
				conditions.append(" localidad.`latitud` = '"+localidad.getLatitud()+"' AND");
			}
			if(localidad.getLongitud() != null){
				conditions.append(" localidad.`longitud` = '"+localidad.getLongitud()+"' AND");
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
			query.addEntity("localidad", LocalidadVO.class);
			
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
	
	public List<LocalidadVO> findAutoComplete(Transaction transaction, LocalidadVO localidad, String order){
		List<LocalidadVO> list = new ArrayList<LocalidadVO>();
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
			
			if(localidad == null){
				throw new Exception("DAO Exception: Localidad is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT localidad.* FROM "+schema+"LOCALIDAD localidad");
			StringBuilder conditions = new StringBuilder();
			if (localidad.getId() != null) {
				conditions.append(" localidad.id = " + localidad.getId() + " AND");
			}
			if (localidad.getIdProvincia() != null) {
				conditions.append(" localidad.`idProvincia` = " + localidad.getIdProvincia() + " AND");
			}
			if(localidad.getLocalidad() != null && !localidad.getLocalidad().isEmpty()){
				conditions.append(" UPPER(localidad.`localidad`) like '"+
						localidad.getLocalidad().toUpperCase()+"%' AND");
			}
			if(localidad.getLocalidadSeo() != null && !localidad.getLocalidadSeo().isEmpty()){
				conditions.append(" UPPER(localidad.`localidadSeo`) like '"+
						localidad.getLocalidadSeo().toUpperCase()+"%' AND");
			}
			if(localidad.getCodigoPostal() != null){
				conditions.append(" localidad.`codigoPostal` = '"+localidad.getCodigoPostal()+"' AND");
			}
			if(localidad.getLatitud() != null){
				conditions.append(" localidad.`latitud` = '"+localidad.getLatitud()+"' AND");
			}
			if(localidad.getLongitud() != null){
				conditions.append(" localidad.`longitud` = '"+localidad.getLongitud()+"' AND");
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
			query.addEntity("localidad", LocalidadVO.class);
			
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
	public synchronized void createElement(Transaction transaction, LocalidadVO localidad) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (localidad == null) {
				throw new DaoException("Element is null");
			}
			
			if(localidad.getId() == null){
				session.save(localidad);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(localidad);
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
	public synchronized void deleteElement(Transaction transaction, LocalidadVO localidad) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (localidad == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+ "LOCALIDAD ");
			StringBuilder conditions = new StringBuilder();
			if (localidad.getId() != null) {
				conditions.append(" localidad.id = " + localidad.getId() + " AND");
			}
			if (localidad.getIdProvincia() != null) {
				conditions.append(" localidad.`idProvincia` = " + localidad.getIdProvincia() + " AND");
			}
			if(localidad.getLocalidad() != null && !localidad.getLocalidad().isEmpty()){
				conditions.append(" UPPER(localidad.`localidad`) = '"+
						localidad.getLocalidad().toUpperCase()+"' AND");
			}
			if(localidad.getLocalidadSeo() != null && !localidad.getLocalidadSeo().isEmpty()){
				conditions.append(" UPPER(localidad.`localidadSeo`) = '"+
						localidad.getLocalidadSeo().toUpperCase()+"' AND");
			}
			if(localidad.getCodigoPostal() != null){
				conditions.append(" localidad.`codigoPostal` = '"+localidad.getCodigoPostal()+"' AND");
			}
			if(localidad.getLatitud() != null){
				conditions.append(" localidad.`latitud` = '"+localidad.getLatitud()+"' AND");
			}
			if(localidad.getLongitud() != null){
				conditions.append(" localidad.`longitud` = '"+localidad.getLongitud()+"' AND");
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