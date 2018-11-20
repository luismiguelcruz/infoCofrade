package com.infoCofrade.administration.secretaria.metodoPago.dao.impl;

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
import com.infoCofrade.administration.secretaria.metodoPago.dao.MetodoPagoDao;
import com.infoCofrade.administration.secretaria.metodoPago.vo.MetodoPagoVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;


@Component("metodoPagoDaoImpl")
public class MetodoPagoDaoImpl implements MetodoPagoDao{
	private static MetodoPagoDao instance = new MetodoPagoDaoImpl();
	private Session session;
	
	private MetodoPagoDaoImpl(){}
	
	public static MetodoPagoDao getInstance(){
		if (instance == null) {
            synchronized(MetodoPagoDaoImpl.class) {
                if (instance == null) { 
                	instance = new MetodoPagoDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized MetodoPagoVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		MetodoPagoVO metodoPago = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from MetodoPagoVO");
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
				metodoPago = (MetodoPagoVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return metodoPago;
	}
	
	public synchronized List<MetodoPagoVO> findAll(Transaction transaction, String order){
		List<MetodoPagoVO> list = new ArrayList<MetodoPagoVO>();
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
					"SELECT * FROM "+schema+"METODO_PAGO");
			
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
			query.addEntity(MetodoPagoVO.class);
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
	
	public List<MetodoPagoVO> findUsingTemplate(Transaction transaction, MetodoPagoVO metodoPago, String order){
		List<MetodoPagoVO> list = new ArrayList<MetodoPagoVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(metodoPago == null){
				throw new Exception("DAO Exception: MetodoPago is null");
			}

			StringBuilder queryString = new StringBuilder("SELECT metodoPago.* FROM METODO_PAGO metodoPago");
			StringBuilder conditions = new StringBuilder();
			if (metodoPago.getId() != null) {
				conditions.append(" metodoPago.id = " + metodoPago.getId() + " AND");
			}
			if(metodoPago.getNombre() != null && !metodoPago.getNombre().isEmpty()){
				conditions.append(" UPPER(metodoPago.nombre) like '%"+metodoPago.getNombre().toUpperCase()+"%' AND");
			}
			if(metodoPago.getSeleccionable() != null){
				conditions.append(" metodoPago.seleccionable = '"+metodoPago.getSeleccionable()+"' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
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
			query.addEntity("metodoPago", MetodoPagoVO.class);
			
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
	
	public List<MetodoPagoVO> findUsingExactTemplate(Transaction transaction, MetodoPagoVO metodoPago, String order){
		List<MetodoPagoVO> list = new ArrayList<MetodoPagoVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(metodoPago == null){
				throw new Exception("DAO Exception: MetodoPago is null");
			}

			StringBuilder queryString = new StringBuilder("SELECT metodoPago.* FROM METODO_PAGO metodoPago");
			StringBuilder conditions = new StringBuilder();
			if (metodoPago.getId() != null) {
				conditions.append(" metodoPago.id = " + metodoPago.getId() + " AND");
			}
			if(metodoPago.getNombre() != null && !metodoPago.getNombre().isEmpty()){
				conditions.append(" UPPER(metodoPago.nombre) = '"+metodoPago.getNombre().toUpperCase()+"' AND");
			}
			if(metodoPago.getSeleccionable() != null){
				conditions.append(" metodoPago.seleccionable = '"+metodoPago.getSeleccionable()+"' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
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
			query.addEntity("metodoPago", MetodoPagoVO.class);
			
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
		} catch (SqlInjectionException e) {
			// TODO: handle exception
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	public void createAllElement(Transaction transaction, List<MetodoPagoVO> listMetodoPago) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (listMetodoPago == null || listMetodoPago.isEmpty()) {
				throw new DaoException("Element is null");
			}
			
			for(MetodoPagoVO metodoPago: listMetodoPago){
				session.merge(metodoPago);
			}
			
			if (transaction == null) {
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elementos actualizados correctamente"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se han podido crear los elementos", ex.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}
	}
	
	
	/**
	 * Create an element into the Database
	 */
	public synchronized void createElement(Transaction transaction, MetodoPagoVO metodoPago) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (metodoPago == null) {
				throw new DaoException("Element is null");
			}
			
			if(metodoPago.getId() == null){
				session.save(metodoPago);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(metodoPago);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento actualizado correctamente"));
			}
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido crear el elemento", ex.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}
	}

	/**
	 * Delete an element from the Database
	 */
	public synchronized void deleteElement(Transaction transaction, MetodoPagoVO metodoPago) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (metodoPago == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+ "METODO_PAGO ");
			StringBuilder conditions = new StringBuilder();
			if (metodoPago.getId() != null) {
				conditions.append(" id = " + metodoPago.getId() + " AND");
			}
			if(metodoPago.getNombre() != null && !metodoPago.getNombre().isEmpty()){
				conditions.append(" nombre = '"+metodoPago.getNombre()+"' AND");
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