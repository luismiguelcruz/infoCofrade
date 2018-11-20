package com.infoCofrade.administration.urlPermission.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Component;

import com.infoCofrade.administration.ataqueSQL.dao.impl.AtaqueSQLDaoImpl;
import com.infoCofrade.administration.urlPermission.dao.UrlPermissionDao;
import com.infoCofrade.administration.urlPermission.vo.UrlPermissionVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;

@Component("urlPermissionDaoImpl")
public class UrlPermissionDaoImpl implements UrlPermissionDao {
	private static UrlPermissionDao instance = new UrlPermissionDaoImpl();
	private Session session;

	private UrlPermissionDaoImpl() {
	}

	public static UrlPermissionDao getInstance() {
		if (instance == null) {
			synchronized (UrlPermissionDaoImpl.class) {
				if (instance == null) {
					instance = new UrlPermissionDaoImpl();
				}
			}
		}

		return instance;
	}

	public synchronized UrlPermissionVO findByPrimaryKey(Transaction transaction,
			Long id) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		UrlPermissionVO UrlPermission = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			StringBuilder queryString = new StringBuilder("from UrlPermissionVO");
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
			UrlPermission = (UrlPermissionVO) session.createQuery(queryString.toString())
					.uniqueResult();

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
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

		return UrlPermission;
	}

	public synchronized List<UrlPermissionVO> findAll(Transaction transaction,
			String order) {
		List<UrlPermissionVO> list = new ArrayList<UrlPermissionVO>();
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
					"SELECT * FROM "+schema+"URL_PERMISSION");
			
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
			query.addEntity(UrlPermissionVO.class);
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

	public synchronized List<UrlPermissionVO> findUsingTemplate(
			Transaction transaction, UrlPermissionVO urlPermission) {
		List<UrlPermissionVO> list = new ArrayList<UrlPermissionVO>();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (urlPermission == null) {
				throw new Exception("DAO Exception: UrlPermission is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT urlPermission.* FROM "+
						schema+"`URL_PERMISSION` urlPermission");
			StringBuilder conditions = new StringBuilder();
			if (urlPermission.getId() != null) {
				conditions.append(" urlPermission.`id` = '" + 
						urlPermission.getId() + "' AND");
			}
			if (urlPermission.getName() != null) {
				conditions.append(" urlPermission.`name` like '%" +
						urlPermission.getName() + "%' AND");
			}
			if (urlPermission.getUrl() != null) {
				conditions.append(" urlPermission.`url` like '%" +
						urlPermission.getUrl() + "%' AND");
			}
			if (urlPermission.getIdMenuItemGroup() != null) {
				conditions.append(" urlPermission.`idMenuItemGroup` = '" +
						urlPermission.getIdMenuItemGroup() + "' AND");
			}
			if (urlPermission.getBeanName() != null) {
				conditions.append(" urlPermission.`beanName` like '%" +
						urlPermission.getBeanName() + "%' AND");
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
			query.addEntity("urlPermission", UrlPermissionVO.class);

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

	public synchronized List<UrlPermissionVO> findUsingExactTemplate(
			Transaction transaction, UrlPermissionVO urlPermission) {
		List<UrlPermissionVO> list = new ArrayList<UrlPermissionVO>();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (urlPermission == null) {
				throw new Exception("DAO Exception: UrlPermission is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT urlPermission.* FROM "+
						schema+"`URL_PERMISSION` urlPermission");
			StringBuilder conditions = new StringBuilder();
			if (urlPermission.getId() != null) {
				conditions.append(" urlPermission.`id` = '" + 
						urlPermission.getId() + "' AND");
			}
			if (urlPermission.getName() != null) {
				conditions.append(" urlPermission.`name` = '" +
						urlPermission.getName() + "' AND");
			}
			if (urlPermission.getUrl() != null) {
				conditions.append(" urlPermission.`url` = '" +
						urlPermission.getUrl() + "' AND");
			}
			if (urlPermission.getIdMenuItemGroup() != null) {
				conditions.append(" urlPermission.`idMenuItemGroup` = '" +
						urlPermission.getIdMenuItemGroup() + "' AND");
			}
			if (urlPermission.getBeanName() != null) {
				conditions.append(" urlPermission.`beanName` = '" +
						urlPermission.getBeanName() + "' AND");
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
			query.addEntity("urlPermission", UrlPermissionVO.class);

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
								"Find Elements using exact template error", ex
										.getMessage()));
			}
		}

		return list;
	}

	/**
	 * Insert or update an element into the Database
	 */
	public synchronized Long createElement(Transaction transaction,
			UrlPermissionVO urlPermission) throws DaoException {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Long idSaved = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (urlPermission == null) {
				throw new DaoException("Element is null");
			}

			if(urlPermission.getId() == null){
				session.save(urlPermission);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(urlPermission);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento actualizado correctamente"));
			}

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Cannot create or update element", ex
										.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}

		idSaved = urlPermission.getId();
		return idSaved;
	}

	/**
	 * Delete an element from the Database
	 */
	public synchronized void deleteElement(Transaction transaction,
			UrlPermissionVO urlPermission) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (urlPermission == null) {
				throw new Exception("Element is null");
			}

			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"`URL_PERMISSION` ");
			StringBuilder conditions = new StringBuilder();
			if (urlPermission.getId() != null) {
				conditions.append(" `id` = '" + 
						urlPermission.getId() + "' AND");
			}
			if (urlPermission.getName() != null) {
				conditions.append(" `name` = '" +
						urlPermission.getName() + "' AND");
			}
			if (urlPermission.getUrl() != null) {
				conditions.append(" `url` = '" +
						urlPermission.getUrl() + "' AND");
			}
			if (urlPermission.getIdMenuItemGroup() != null) {
				conditions.append(" `idMenuItemGroup` = '" +
						urlPermission.getIdMenuItemGroup() + "' AND");
			}
			if (urlPermission.getBeanName() != null) {
				conditions.append(" `beanName` = '" +
						urlPermission.getBeanName() + "' AND");
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
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Database info: ",
										"Element deleted correctly"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Cannot delete error", ex.getMessage()));
			}
		}
	}
}