package com.infoCofrade.administration.rolUrlPermission.dao.impl;

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
import com.infoCofrade.administration.rolUrlPermission.dao.RolUrlPermissionDao;
import com.infoCofrade.administration.rolUrlPermission.vo.RolUrlPermissionVO;
import com.infoCofrade.administration.rolUrlPermissionModel.vo.RolUrlPermissionModelVO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;

@Component("rolUrlPermissionDaoImpl")
public class RolUrlPermissionDaoImpl implements RolUrlPermissionDao {
	private static RolUrlPermissionDao instance = new RolUrlPermissionDaoImpl();
	private Session session;

	private RolUrlPermissionDaoImpl() {
	}

	public static RolUrlPermissionDao getInstance() {
		if (instance == null) {
			synchronized (RolUrlPermissionDaoImpl.class) {
				if (instance == null) {
					instance = new RolUrlPermissionDaoImpl();
				}
			}
		}

		return instance;
	}

	public synchronized RolUrlPermissionVO findByPrimaryKey(Transaction transaction,
			Long id) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		RolUrlPermissionVO RolUrlPermission = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			StringBuilder queryString = new StringBuilder("from RolUrlPermissionVO");
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
			RolUrlPermission = (RolUrlPermissionVO) session.createQuery(queryString.toString())
					.uniqueResult();

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException e) {
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

		return RolUrlPermission;
	}

	public synchronized List<RolUrlPermissionVO> findAll(Transaction transaction,
			String order) {
		List<RolUrlPermissionVO> list = new ArrayList<RolUrlPermissionVO>();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT rolUrlPermission.* FROM "+schema+"`rol_url_permission` rolUrlPermission");

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
			query.addEntity("rolUrlPermission", RolUrlPermissionVO.class);

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

	public synchronized Long countAll(Transaction transaction) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		return null;
	}

	public synchronized List<RolUrlPermissionVO> findUsingTemplate(
			Transaction transaction, RolUrlPermissionVO rolUrlPermission) {
		List<RolUrlPermissionVO> list = new ArrayList<RolUrlPermissionVO>();
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

			if (rolUrlPermission == null) {
				throw new Exception("DAO Exception: RolUrlPermission is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT rolUrlPermission.* FROM "+schema+"`rol_url_permission` rolUrlPermission");
			StringBuilder conditions = new StringBuilder();
			if (rolUrlPermission.getId() != null) {
				conditions.append(" rolUrlPermission.id = '" + 
						rolUrlPermission.getId() + "' AND");
			}
			if (rolUrlPermission.getIdUserType() != null) {
				conditions.append(" rolUrlPermission.`idUserType` = '" +
						rolUrlPermission.getIdUserType() + "' AND");
			}
			if (rolUrlPermission.getIdUrlPermission() != null) {
				conditions.append(" rolUrlPermission.`idUrlPermission` = '" +
						rolUrlPermission.getIdUrlPermission() + "' AND");
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
			query.addEntity("rolUrlPermission", RolUrlPermissionVO.class);

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

	public synchronized List<RolUrlPermissionVO> findUsingExactTemplate(
			Transaction transaction, RolUrlPermissionVO rolUrlPermission) {
		List<RolUrlPermissionVO> list = new ArrayList<RolUrlPermissionVO>();
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

			if (rolUrlPermission == null) {
				throw new Exception("DAO Exception: RolUrlPermission is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT rolUrlPermission.* FROM "+schema+"`rol_url_permission` rolUrlPermission");
			StringBuilder conditions = new StringBuilder();
			if (rolUrlPermission.getId() != null) {
				conditions.append(" rolUrlPermission.id = '" + 
						rolUrlPermission.getId() + "' AND");
			}
			if (rolUrlPermission.getIdUserType() != null) {
				conditions.append(" rolUrlPermission.`idUserType` = '" +
						rolUrlPermission.getIdUserType() + "' AND");
			}
			if (rolUrlPermission.getIdUrlPermission() != null) {
				conditions.append(" rolUrlPermission.`idUrlPermission` = '" +
						rolUrlPermission.getIdUrlPermission() + "' AND");
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
			query.addEntity("rolUrlPermission", RolUrlPermissionVO.class);

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
			RolUrlPermissionVO RolUrlPermission) {
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
			RolUrlPermissionVO rolUrlPermission) throws DaoException {
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

			if (rolUrlPermission == null) {
				throw new DaoException("Element is null");
			}

			if (rolUrlPermission.getId() == null) {
				session.save(rolUrlPermission);
			} else {
				session.merge(rolUrlPermission);
				idSaved = rolUrlPermission.getId();
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

		idSaved = rolUrlPermission.getId();
		return idSaved;
	}

	/**
	 * Insert or update an element into the Database
	 */
	public synchronized Long createElement(Transaction transaction,
			UserTypeVO userType, List<RolUrlPermissionModelVO> listRolUrlPermissionsModel)
			throws DaoException {
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
			
			if (userType == null) {
				throw new DaoException("Element is null");
			}
			
			StringBuilder ids = new StringBuilder();
			List<RolUrlPermissionModelVO> listItemsToSave = new ArrayList<RolUrlPermissionModelVO>();
			
			if(listRolUrlPermissionsModel.size() > 0){
				for(RolUrlPermissionModelVO item: listRolUrlPermissionsModel){
					if(item.getSelected() != null && item.getSelected()){
						ids.append(item.getIdUrlPermission()+",");
						listItemsToSave.add(item);
					}
				}
				
				if(ids.length() > 0){
					ids.deleteCharAt(ids.length()-1);
				}
			}
			
			// 1.- Delete all the url permissions for the current User Type that has been removed
			String where = "WHERE `idUserType` = "+userType.getId();
			if(ids.length() > 0){
				where += " AND `idUrlPermission` NOT IN ("+ids+")";
			}
			this.deleteElementByWhere(currentTransaction, where);
			
			// 2.- Save all the url permissions setted for the current user type selected
			RolUrlPermissionVO elem;
			for(RolUrlPermissionModelVO item: listItemsToSave){
				elem = new RolUrlPermissionVO();
				elem.setId(item.getId());
				elem.setIdUserType(userType.getId());
				elem.setIdUrlPermission(item.getIdUrlPermission());
				if (item.getId() == null) {
					session.save(elem);
				} else {
					session.merge(elem);
					idSaved = elem.getId();
				}
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

		idSaved = userType.getId();
		return idSaved;
	}

	/**
	 * Delete an element from the Database
	 */
	public synchronized void deleteElement(Transaction transaction,
			RolUrlPermissionVO rolUrlPermission) {
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

			if (rolUrlPermission == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"`rol_url_permission` ");
			StringBuilder conditions = new StringBuilder();
			if (rolUrlPermission.getId() != null) {
				conditions.append(" id = '" + 
						rolUrlPermission.getId() + "' AND");
			}
			if (rolUrlPermission.getIdUserType() != null) {
				conditions.append(" `idUserType` = '" +
						rolUrlPermission.getIdUserType() + "' AND");
			}
			if (rolUrlPermission.getIdUrlPermission() != null) {
				conditions.append(" `idUrlPermission` = '" +
						rolUrlPermission.getIdUrlPermission() + "' AND");
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
	
	public synchronized void deleteElementByWhere(Transaction transaction,
			String where) {
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
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"`ROL_URL_PERMISSION` ");

			if (where.length() > 0) {
				queryString.append(where);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.executeUpdate();

			if (transaction == null) {
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Database info: ",
								"Element/s deleted correctly"));
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