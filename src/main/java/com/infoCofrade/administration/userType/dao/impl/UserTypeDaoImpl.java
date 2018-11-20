package com.infoCofrade.administration.userType.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Component;

import com.infoCofrade.administration.ataqueSQL.dao.impl.AtaqueSQLDaoImpl;
import com.infoCofrade.administration.userType.dao.UserTypeDao;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;

@Component("userTypeDaoImpl")
public class UserTypeDaoImpl implements UserTypeDao {
	private static UserTypeDao instance = new UserTypeDaoImpl();
	private Session session;

	private UserTypeDaoImpl() {
	}

	public static UserTypeDao getInstance() {
		if (instance == null) {
			synchronized (UserTypeDaoImpl.class) {
				if (instance == null) {
					instance = new UserTypeDaoImpl();
				}
			}
		}

		return instance;
	}

	public synchronized UserTypeVO findByPrimaryKey(Transaction transaction,
			Long id) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		UserTypeVO userType = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			StringBuilder queryString = new StringBuilder("from UserTypeVO");
			if (id != null) {
				queryString.append(" where id = " + id);
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
			userType = (UserTypeVO) session.createQuery(queryString.toString())
					.uniqueResult();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Database error: ", ex.getMessage()));
			}
		}

		return userType;
	}

	public synchronized List<UserTypeVO> findAll(Transaction transaction,
			String order) {
		List<UserTypeVO> list = new ArrayList<UserTypeVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			StringBuilder queryString = new StringBuilder("from UserTypeVO");
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
			Query query = session.createQuery(queryString.toString());
			list = query.list();

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Database error: ", ex.getMessage()));
			}
		}

		return list;
	}
	
	public List<UserTypeVO> findByWhere(Transaction transaction, String where, String order){
		List<UserTypeVO> list = new ArrayList<UserTypeVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();

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
					"SELECT userType.* FROM "+schema+"`USER_TYPE` userType ");

			if (where != null && where.length() > 0) {
				queryString.append(where);
			}
			
			if(order != null && !order.isEmpty()){
				queryString.append(" ORDER BY "+order);
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
			query.addEntity("userType", UserTypeVO.class);

			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Not elements found", ex.getMessage()));
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Find Elements using template error", ex
										.getMessage()));
			}
		}

		return list;
	}

	public synchronized List<UserTypeVO> findUsingTemplate(
			Transaction transaction, UserTypeVO userType) {
		List<UserTypeVO> list = new ArrayList<UserTypeVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (userType == null) {
				throw new Exception("DAO Exception: User Type is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT ust.* FROM "+schema+"`USER_TYPE` ust");
			StringBuilder conditions = new StringBuilder();
			if (userType.getId() != null) {
				conditions.append(" ust.id = " + userType.getId() + " AND");
			}
			if (userType.getType() != null) {
				queryString.append(" UPPER(ust.type) like '%"
						+ userType.getType().toUpperCase() + "%'");
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
			query.addEntity("ust", UserTypeVO.class);

			list = query.list();

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
			}
		}

		return list;
	}

	public synchronized Long countUsingTemplate(Transaction transaction,
			UserTypeVO userType) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		return null;
	}

	public synchronized Long countByQuery(Transaction transaction, String query) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		return null;
	}

	/**
	 * Create an element into the Database
	 */
	public synchronized void createElement(Transaction transaction,
			UserTypeVO userType) throws DaoException {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (userType == null) {
				throw new DaoException("Element is null");
			}
			
			if(userType.getId() == null){
				session.save(userType);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"",
						"Elemento creado correctamente"));
			} else {
				session.merge(userType);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"",
						"Elemento actualizado correctamente"));
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
	public synchronized void deleteElement(Transaction transaction,
			UserTypeVO userType) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (userType == null) {
				throw new Exception("Element is null");
			}

			session.delete(userType);
			
			if (transaction == null) {
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Database info: ", "Element deleted correctly"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Database error: ", ex.getMessage()));
			}
		}
	}
}