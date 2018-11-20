package com.infoCofrade.administration.rolUrlPermissionView.dao.impl;

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
import com.infoCofrade.administration.rolUrlPermissionView.dao.RolUrlPermissionViewDao;
import com.infoCofrade.administration.rolUrlPermissionView.vo.RolUrlPermissionViewVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;

@Component("rolUrlPermissionViewDaoImpl")
public class RolUrlPermissionViewDaoImpl implements RolUrlPermissionViewDao {
	private static RolUrlPermissionViewDao instance = new RolUrlPermissionViewDaoImpl();
	private Session session;

	private RolUrlPermissionViewDaoImpl() {
	}

	public static RolUrlPermissionViewDao getInstance() {
		if (instance == null) {
			synchronized (RolUrlPermissionViewDaoImpl.class) {
				if (instance == null) {
					instance = new RolUrlPermissionViewDaoImpl();
				}
			}
		}

		return instance;
	}

	public synchronized RolUrlPermissionViewVO findByPrimaryKey(
			Transaction transaction, Long id) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		RolUrlPermissionViewVO RolUrlPermissionView = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			StringBuilder queryString = new StringBuilder(
					"from RolUrlPermissionViewVO");
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
			RolUrlPermissionView = (RolUrlPermissionViewVO) session
					.createQuery(queryString.toString()).uniqueResult();

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

		return RolUrlPermissionView;
	}

	public synchronized List<RolUrlPermissionViewVO> findAll(
			Transaction transaction, String order) {
		List<RolUrlPermissionViewVO> list = new ArrayList<RolUrlPermissionViewVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			StringBuilder queryString = new StringBuilder("from RolUrlPermissionViewVO");
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
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Not elements found", ex.getMessage()));
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex) {
			ex.printStackTrace();
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Find all elements error", ex.getMessage()));
			}
		}

		return list;
	}

	public synchronized List<RolUrlPermissionViewVO> findUsingTemplate(
			Transaction transaction, RolUrlPermissionViewVO rolUrlPermissionView) {
		List<RolUrlPermissionViewVO> list = new ArrayList<RolUrlPermissionViewVO>();
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

			if (rolUrlPermissionView == null) {
				throw new Exception(
						"DAO Exception: RolUrlPermissionView is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT rolUrlPermissionView.* FROM "+schema+"`ROL_URL_PERMISSION_VIEW` rolUrlPermissionView");
			StringBuilder conditions = new StringBuilder();
			if (rolUrlPermissionView.getId() != null) {
				conditions.append(" rolUrlPermissionView.id = '"
						+ rolUrlPermissionView.getId() + "' AND");
			}
			if (rolUrlPermissionView.getIdUserType() != null) {
				conditions.append(" rolUrlPermissionView.`idUserType` = '"
						+ rolUrlPermissionView.getIdUserType() + "' AND");
			}
			if (rolUrlPermissionView.getIdUrlPermission() != null) {
				conditions
						.append(" rolUrlPermissionView.`idUrlPermission` = '"
								+ rolUrlPermissionView.getIdUrlPermission()
								+ "' AND");
			}
			if (rolUrlPermissionView.getName() != null) {
				conditions.append(" rolUrlPermissionView.`name` like '%"
						+ rolUrlPermissionView.getName() + "%' AND");
			}
			if (rolUrlPermissionView.getUrl() != null) {
				conditions.append(" rolUrlPermissionView.`url` like '%"
						+ rolUrlPermissionView.getUrl() + "%' AND");
			}
			if (rolUrlPermissionView.getIdMenuItemGroup() != null) {
				conditions
						.append(" rolUrlPermissionView.`idMenuItemGroup` = '"
								+ rolUrlPermissionView.getIdMenuItemGroup()
								+ "' AND");
			}
			if (rolUrlPermissionView.getBeanName() != null) {
				conditions.append(" rolUrlPermissionView.`beanName` like '%" +
						rolUrlPermissionView.getBeanName() + "%' AND");
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
			query.addEntity("rolUrlPermissionView",
					RolUrlPermissionViewVO.class);

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

	public synchronized List<RolUrlPermissionViewVO> findUsingExactTemplate(
			Transaction transaction, RolUrlPermissionViewVO rolUrlPermissionView) {
		List<RolUrlPermissionViewVO> list = new ArrayList<RolUrlPermissionViewVO>();
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

			if (rolUrlPermissionView == null) {
				throw new Exception(
						"DAO Exception: RolUrlPermissionView is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT rolUrlPermissionView.* FROM "+schema+"`ROL_URL_PERMISSION_VIEW` rolUrlPermissionView");
			StringBuilder conditions = new StringBuilder();
			if (rolUrlPermissionView.getId() != null) {
				conditions.append(" rolUrlPermissionView.id = '"
						+ rolUrlPermissionView.getId() + "' AND");
			}
			if (rolUrlPermissionView.getIdUserType() != null) {
				conditions.append(" rolUrlPermissionView.`idUserType` = '"
						+ rolUrlPermissionView.getIdUserType() + "' AND");
			}
			if (rolUrlPermissionView.getIdUrlPermission() != null) {
				conditions.append(" rolUrlPermissionView.`idUrlPermission` = '"
						+ rolUrlPermissionView.getIdUrlPermission() + "' AND");
			}
			if (rolUrlPermissionView.getName() != null) {
				conditions.append(" rolUrlPermissionView.`name` = '"
						+ rolUrlPermissionView.getName() + "' AND");
			}
			if (rolUrlPermissionView.getUrl() != null) {
				conditions.append(" rolUrlPermissionView.`url` = '"
						+ rolUrlPermissionView.getUrl() + "' AND");
			}
			if (rolUrlPermissionView.getIdMenuItemGroup() != null) {
				conditions
						.append(" rolUrlPermissionView.`idMenuItemGroup` = '"
								+ rolUrlPermissionView.getIdMenuItemGroup()
								+ "' AND");
			}
			if (rolUrlPermissionView.getBeanName() != null) {
				conditions.append(" rolUrlPermissionView.`beanName` = '" +
						rolUrlPermissionView.getBeanName() + "' AND");
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
			query.addEntity("rolUrlPermissionView",
					RolUrlPermissionViewVO.class);

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

	public synchronized Long countUsingTemplate(Transaction transaction,
			RolUrlPermissionViewVO RolUrlPermissionView) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		return null;
	}

	public synchronized Long countByQuery(Transaction transaction, String query) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		return null;
	}

	/**
	 * Insert or update an element into the Database
	 */
	public synchronized Long createElement(Transaction transaction,
			RolUrlPermissionViewVO rolUrlPermissionView) throws DaoException {
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

			if (rolUrlPermissionView == null) {
				throw new DaoException("Element is null");
			}

			if (rolUrlPermissionView.getId() == null) {
				session.save(rolUrlPermissionView);
			} else {
				session.merge(rolUrlPermissionView);
				idSaved = rolUrlPermissionView.getId();
			}

			if (transaction == null) {
				currentTransaction.commit();

				// Add the messages
				if (idSaved == null) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Database info: ",
									"Element created correctly"));
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Database info: ",
									"Element updated correctly"));
				}
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

		idSaved = rolUrlPermissionView.getId();
		return idSaved;
	}


	/**
	 * Delete an element from the Database
	 */
	public synchronized void deleteElement(Transaction transaction,
			RolUrlPermissionViewVO rolUrlPermissionView) {
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

			if (rolUrlPermissionView == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"`ROL_URL_PERMISSION_VIEW` ");
			StringBuilder conditions = new StringBuilder();
			if (rolUrlPermissionView.getId() != null) {
				conditions.append(" `id` = '"
						+ rolUrlPermissionView.getId() + "' AND");
			}
			if (rolUrlPermissionView.getIdUserType() != null) {
				conditions.append(" `idUserType` = '"
						+ rolUrlPermissionView.getIdUserType() + "' AND");
			}
			if (rolUrlPermissionView.getIdUrlPermission() != null) {
				conditions
						.append(" `idUrlPermission` = '"
								+ rolUrlPermissionView.getIdUrlPermission()
								+ "' AND");
			}
			if (rolUrlPermissionView.getName() != null) {
				conditions.append(" `name` = '"
						+ rolUrlPermissionView.getName() + "' AND");
			}
			if (rolUrlPermissionView.getUrl() != null) {
				conditions.append(" `url` = '"
						+ rolUrlPermissionView.getUrl() + "' AND");
			}
			if (rolUrlPermissionView.getIdMenuItemGroup() != null) {
				conditions
						.append(" `idMenuItemGroup` = '"
								+ rolUrlPermissionView.getIdMenuItemGroup()
								+ "' AND");
			}
			if (rolUrlPermissionView.getBeanName() != null) {
				conditions.append(" `beanName` = '" +
						rolUrlPermissionView.getBeanName() + "' AND");
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