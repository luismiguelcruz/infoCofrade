package com.infoCofrade.mayordomia.cuotaHermano.dao.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.infoCofrade.mayordomia.cuotaHermano.dao.CuotaHermanoDao;
import com.infoCofrade.mayordomia.cuotaHermano.vo.CuotaHermanoVO;


@Component("cuotaHermanoDaoImpl")
public class CuotaHermanoDaoImpl implements CuotaHermanoDao{
	private static CuotaHermanoDao instance = new CuotaHermanoDaoImpl();
	private Session session;
	
	private CuotaHermanoDaoImpl(){}
	
	public static CuotaHermanoDao getInstance(){
		if (instance == null) {
            synchronized(CuotaHermanoDaoImpl.class) {
                if (instance == null) { 
                	instance = new CuotaHermanoDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized CuotaHermanoVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		CuotaHermanoVO cuotaHermano = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from CuotaHermanoVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				cuotaHermano = (CuotaHermanoVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return cuotaHermano;
	}
	
	public synchronized List<CuotaHermanoVO> findAll(Transaction transaction, String order){
		List<CuotaHermanoVO> list = new ArrayList<CuotaHermanoVO>();
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
					"SELECT * FROM "+schema+"CUOTA_HERMANO");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(CuotaHermanoVO.class);
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
	
	public CuotaHermanoVO findCuotaHermano(Transaction transaction, Integer edad){
		CuotaHermanoVO result = new CuotaHermanoVO();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory())
					.getSettings().getDefaultSchemaName();
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
					"SELECT * FROM "+schema+"CUOTA_HERMANO cuotaHermano");
			
			queryString.append(" WHERE cuotaHermano.`edadMinima` <= " + edad +
					" AND cuotaHermano.`edadMaxima` >= " + edad);
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(CuotaHermanoVO.class);
			result = (CuotaHermanoVO)query.uniqueResult();
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
	
		return result;
	}
	
	public List<CuotaHermanoVO> findEmbeddedItem(Transaction transaction, CuotaHermanoVO cuotaHermano, String order){
		List<CuotaHermanoVO> list = new ArrayList<CuotaHermanoVO>();
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
			
			if(cuotaHermano == null){
				throw new Exception("DAO Exception: CuotaHermano is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT cuotaHermano.* FROM "+schema+"CUOTA_HERMANO cuotaHermano");
			StringBuilder conditions = new StringBuilder();
			if(cuotaHermano.getId() != null){
				conditions.append(" cuotaHermano.`id` <> " + cuotaHermano.getId() + " AND");
			}
			if(cuotaHermano.getEdadMinima() != null){
				conditions.append(" ((cuotaHermano.`edadMinima` <= "+
						cuotaHermano.getEdadMinima()+" AND cuotaHermano.`edadMaxima` >= " +
						cuotaHermano.getEdadMinima()+") OR");
			}
			if(cuotaHermano.getEdadMaxima() != null){
				conditions.append(" (cuotaHermano.`edadMinima` <= "+
						cuotaHermano.getEdadMaxima()+" AND cuotaHermano.`edadMaxima` >= " +
						cuotaHermano.getEdadMaxima()+")) OR");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 2));
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("cuotaHermano", CuotaHermanoVO.class);
			
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
	
	public List<CuotaHermanoVO> findUsingTemplate(Transaction transaction, CuotaHermanoVO cuotaHermano, String order){
		List<CuotaHermanoVO> list = new ArrayList<CuotaHermanoVO>();
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
			
			if(cuotaHermano == null){
				throw new Exception("DAO Exception: CuotaHermano is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT cuotaHermano.* FROM "+schema+"CUOTA_HERMANO cuotaHermano");
			StringBuilder conditions = new StringBuilder();
			if (cuotaHermano.getId() != null) {
				conditions.append(" cuotaHermano.id = " + cuotaHermano.getId() + " AND");
			}
			if(cuotaHermano.getCuantia() != null){
				conditions.append(" UPPER(cuotaHermano.`cuantia`) = "+
						cuotaHermano.getCuantia()+" AND");
			}
			if(cuotaHermano.getEdadMinima() != null){
				conditions.append(" UPPER(cuotaHermano.`edadMinima`) <= "+
						cuotaHermano.getEdadMinima()+" AND");
			}
			if(cuotaHermano.getEdadMaxima() != null){
				conditions.append(" UPPER(cuotaHermano.`edadMaxima`) => "+
						cuotaHermano.getEdadMaxima()+" AND");
			}
			if (cuotaHermano.getIdCuenta() != null) {
				conditions.append(" cuotaHermano.`idCuenta` = " + cuotaHermano.getIdCuenta() + " AND");
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
			query.addEntity("cuotaHermano", CuotaHermanoVO.class);
			
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
	
	public List<CuotaHermanoVO> findUsingExactTemplate(Transaction transaction, CuotaHermanoVO cuotaHermano, String order){
		List<CuotaHermanoVO> list = new ArrayList<CuotaHermanoVO>();
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
			
			if(cuotaHermano == null){
				throw new Exception("DAO Exception: CuotaHermano is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT cuotaHermano.* FROM "+schema+"CUOTA_HERMANO cuotaHermano");
			StringBuilder conditions = new StringBuilder();
			if (cuotaHermano.getId() != null) {
				conditions.append(" cuotaHermano.id = " + cuotaHermano.getId() + " AND");
			}
			if(cuotaHermano.getCuantia() != null){
				conditions.append(" UPPER(cuotaHermano.`cuantia`) = "+
						cuotaHermano.getCuantia()+" AND");
			}
			if(cuotaHermano.getEdadMinima() != null){
				conditions.append(" UPPER(cuotaHermano.`edadMinima`) <= "+
						cuotaHermano.getEdadMinima()+" AND");
			}
			if(cuotaHermano.getEdadMaxima() != null){
				conditions.append(" UPPER(cuotaHermano.`edadMaxima`) => "+
						cuotaHermano.getEdadMaxima()+" AND");
			}
			if (cuotaHermano.getIdCuenta() != null) {
				conditions.append(" cuotaHermano.`idCuenta` = " + cuotaHermano.getIdCuenta() + " AND");
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
			query.addEntity("cuotaHermano", CuotaHermanoVO.class);
			
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
	public synchronized void createElement(Transaction transaction, CuotaHermanoVO cuotaHermano) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (cuotaHermano == null) {
				throw new DaoException("Element is null");
			}
			
			if(cuotaHermano.getId() == null){
				session.save(cuotaHermano);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(cuotaHermano);
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
	public synchronized void deleteElement(Transaction transaction, CuotaHermanoVO cuotaHermano) {
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
			
			if (cuotaHermano == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"CUOTA_HERMANO ");
			StringBuilder conditions = new StringBuilder();
			if (cuotaHermano.getId() != null) {
				conditions.append(" id = " + cuotaHermano.getId() + " AND");
			}
			if(cuotaHermano.getCuantia() != null){
				conditions.append(" `cuantia` = "+
						cuotaHermano.getCuantia()+" AND");
			}
			if(cuotaHermano.getEdadMinima() != null){
				conditions.append(" `edadMinima` = "+
						cuotaHermano.getEdadMinima()+" AND");
			}
			if(cuotaHermano.getEdadMaxima() != null){
				conditions.append(" `edadMaxima` = "+
						cuotaHermano.getEdadMaxima()+" AND");
			}
			if (cuotaHermano.getIdCuenta() != null) {
				conditions.append(" `idCuenta` = " + cuotaHermano.getIdCuenta() + " AND");
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