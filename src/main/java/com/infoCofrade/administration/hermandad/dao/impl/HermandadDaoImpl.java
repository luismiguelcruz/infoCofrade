package com.infoCofrade.administration.hermandad.dao.impl;

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
import com.infoCofrade.administration.hermandad.dao.HermandadDao;
import com.infoCofrade.administration.hermandad.vo.HermandadVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;


@Component("hermandadDaoImpl")
public class HermandadDaoImpl implements HermandadDao{
	private static HermandadDao instance = new HermandadDaoImpl();
	private Session session;
	
	private HermandadDaoImpl(){}
	
	public static HermandadDao getInstance(){
		if (instance == null) {
            synchronized(HermandadDaoImpl.class) {
                if (instance == null) { 
                	instance = new HermandadDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized HermandadVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		HermandadVO hermandad = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from HermandadVO");
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
				hermandad = (HermandadVO)session.createQuery(queryString.toString()).uniqueResult();
			}
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex){
		} catch (Exception ex){
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}
		
		return hermandad;
	}
	
	public synchronized List<HermandadVO> findAll(Transaction transaction, String order){
		List<HermandadVO> list = new ArrayList<HermandadVO>();
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
					"SELECT * FROM "+schema+"HERMANDAD");
			
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
			query.addEntity(HermandadVO.class);
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
	
	public List<HermandadVO> findUsingTemplate(Transaction transaction, HermandadVO hermandad, String order){
		List<HermandadVO> list = new ArrayList<HermandadVO>();
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
			
			if(hermandad == null){
				throw new Exception("DAO Exception: Hermandad is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT hermandad.* FROM "+schema+"HERMANDAD hermandad");
			StringBuilder conditions = new StringBuilder();
			if (hermandad.getId() != null) {
				conditions.append(" hermandad.id = " + hermandad.getId() + " AND");
			}
			if(hermandad.getCodigo() != null && !hermandad.getCodigo().isEmpty()){
				conditions.append(" UPPER(hermandad.`codigo`) like '%"+hermandad.getCodigo().toUpperCase()+"%' AND");
			}
			if(hermandad.getNombreCorto() != null && !hermandad.getNombreCorto().isEmpty()){
				conditions.append(" UPPER(hermandad.`nombreCorto`) like '%"+hermandad.getNombreCorto().toUpperCase()+"%' AND");
			}
			if(hermandad.getNombreLargo() != null && !hermandad.getNombreLargo().isEmpty()){
				conditions.append(" UPPER(hermandad.`nombreLargo`) like '%"+hermandad.getNombreLargo().toUpperCase()+"%' AND");
			}
			if(hermandad.getDataSourceName() != null && !hermandad.getDataSourceName().isEmpty()){
				conditions.append(" UPPER(hermandad.`dataSourceName`) like '%"+hermandad.getDataSourceName().toUpperCase()+"%' AND");
			}
			if (hermandad.getFechaAlta() != null) {
				conditions.append(" hermandad.`fechaAlta` = " + hermandad.getFechaAlta() + " AND");
			}
			if (hermandad.getFechaUltimoPago() != null) {
				conditions.append(" hermandad.`fechaUltimoPago` = " + hermandad.getFechaUltimoPago() + " AND");
			}
			if (hermandad.getFechaBaja() != null) {
				conditions.append(" hermandad.`fechaBaja` = " + hermandad.getFechaBaja() + " AND");
			}
			if (hermandad.getIdLocalidad() != null) {
				conditions.append(" hermandad.`idLocalidad` = " + hermandad.getIdLocalidad() + " AND");
			}
			if(hermandad.getObservaciones() != null && !hermandad.getObservaciones().isEmpty()){
				conditions.append(" UPPER(hermandad.`observaciones`) like '%"+hermandad.getObservaciones().toUpperCase()+"%' AND");
			}
			if(hermandad.getUrlWeb() != null && !hermandad.getUrlWeb().isEmpty()){
				conditions.append(" UPPER(hermandad.`urlWeb`) like '%"+hermandad.getUrlWeb().toUpperCase()+"%' AND");
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
			query.addEntity("hermandad", HermandadVO.class);
			
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
		} catch (SqlInjectionException ex){
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	public List<HermandadVO> findUsingExactTemplate(Transaction transaction, HermandadVO hermandad, String order){
		List<HermandadVO> list = new ArrayList<HermandadVO>();
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
			
			if(hermandad == null){
				throw new Exception("DAO Exception: Hermandad is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT hermandad.* FROM "+schema+"HERMANDAD hermandad");
			StringBuilder conditions = new StringBuilder();
			if (hermandad.getId() != null) {
				conditions.append(" hermandad.id = " + hermandad.getId() + " AND");
			}
			if(hermandad.getCodigo() != null && !hermandad.getCodigo().isEmpty()){
				conditions.append(" UPPER(hermandad.`codigo`) = '"+hermandad.getCodigo().toUpperCase()+"' AND");
			}
			if(hermandad.getNombreCorto() != null && !hermandad.getNombreCorto().isEmpty()){
				conditions.append(" UPPER(hermandad.`nombreCorto`) = '"+hermandad.getNombreCorto().toUpperCase()+"' AND");
			}
			if(hermandad.getNombreLargo() != null && !hermandad.getNombreLargo().isEmpty()){
				conditions.append(" UPPER(hermandad.`nombreLargo`) = '"+hermandad.getNombreLargo().toUpperCase()+"' AND");
			}
			if(hermandad.getDataSourceName() != null && !hermandad.getDataSourceName().isEmpty()){
				conditions.append(" UPPER(hermandad.`dataSourceName`) = '"+hermandad.getDataSourceName().toUpperCase()+"' AND");
			}
			if (hermandad.getFechaAlta() != null) {
				conditions.append(" hermandad.`fechaAlta` = " + hermandad.getFechaAlta() + " AND");
			}
			if (hermandad.getFechaUltimoPago() != null) {
				conditions.append(" hermandad.`fechaUltimoPago` = " + hermandad.getFechaUltimoPago() + " AND");
			}
			if (hermandad.getFechaBaja() != null) {
				conditions.append(" hermandad.`fechaBaja` = " + hermandad.getFechaBaja() + " AND");
			}
			if (hermandad.getIdLocalidad() != null) {
				conditions.append(" hermandad.`idLocalidad` = " + hermandad.getIdLocalidad() + " AND");
			}
			if(hermandad.getObservaciones() != null && !hermandad.getObservaciones().isEmpty()){
				conditions.append(" UPPER(hermandad.`observaciones`) = '"+hermandad.getObservaciones().toUpperCase()+"' AND");
			}
			if(hermandad.getUrlWeb() != null && !hermandad.getUrlWeb().isEmpty()){
				conditions.append(" UPPER(hermandad.`urlWeb`) = '"+hermandad.getUrlWeb().toUpperCase()+"' AND");
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
			query.addEntity("hermandad", HermandadVO.class);
			
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
	
	
	/**
	 * Create an element into the Database
	 */
	public synchronized void createElement(Transaction transaction, HermandadVO hermandad) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (hermandad == null) {
				throw new DaoException("Element is null");
			}
			
			if(hermandad.getId() == null){
				session.save(hermandad);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(hermandad);
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
	public synchronized void deleteElement(Transaction transaction, HermandadVO hermandad) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (hermandad == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"HERMANDAD ");
			StringBuilder conditions = new StringBuilder();
			if (hermandad.getId() != null) {
				conditions.append(" id = " + hermandad.getId() + " AND");
			}
			if(hermandad.getCodigo() != null && !hermandad.getCodigo().isEmpty()){
				conditions.append(" UPPER(`codigo`) = '"+hermandad.getCodigo().toUpperCase()+"' AND");
			}
			if(hermandad.getNombreCorto() != null && !hermandad.getNombreCorto().isEmpty()){
				conditions.append(" UPPER(`nombreCorto`) = '"+hermandad.getNombreCorto().toUpperCase()+"' AND");
			}
			if(hermandad.getNombreLargo() != null && !hermandad.getNombreLargo().isEmpty()){
				conditions.append(" UPPER(`nombreLargo`) = '"+hermandad.getNombreLargo().toUpperCase()+"' AND");
			}
			if(hermandad.getDataSourceName() != null && !hermandad.getDataSourceName().isEmpty()){
				conditions.append(" UPPER(`dataSourceName`) = '"+hermandad.getDataSourceName().toUpperCase()+"' AND");
			}
			if (hermandad.getFechaAlta() != null) {
				conditions.append(" `fechaAlta` = " + hermandad.getFechaAlta() + " AND");
			}
			if (hermandad.getFechaUltimoPago() != null) {
				conditions.append(" hermandad.`fechaUltimoPago` = " + hermandad.getFechaUltimoPago() + " AND");
			}
			if (hermandad.getFechaBaja() != null) {
				conditions.append(" `fechaBaja` = " + hermandad.getFechaBaja() + " AND");
			}
			if (hermandad.getIdLocalidad() != null) {
				conditions.append(" `idLocalidad` = " + hermandad.getIdLocalidad() + " AND");
			}
			if(hermandad.getObservaciones() != null && !hermandad.getObservaciones().isEmpty()){
				conditions.append(" UPPER(`observaciones`) = '"+hermandad.getObservaciones().toUpperCase()+"' AND");
			}
			if(hermandad.getUrlWeb() != null && !hermandad.getUrlWeb().isEmpty()){
				conditions.append(" UPPER(`urlWeb`) = '"+hermandad.getUrlWeb().toUpperCase()+"' AND");
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