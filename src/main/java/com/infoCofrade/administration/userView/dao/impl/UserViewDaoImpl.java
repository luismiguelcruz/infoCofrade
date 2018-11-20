package com.infoCofrade.administration.userView.dao.impl;

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
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.administration.userView.dao.UserViewDao;
import com.infoCofrade.administration.userView.vo.UserViewVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;

@Component("userViewDaoImpl")
public class UserViewDaoImpl implements UserViewDao {
	private static UserViewDao instance = new UserViewDaoImpl();
	private Session session;

	private UserViewDaoImpl() {
	}

	public static UserViewDao getInstance() {
		if (instance == null) {
			synchronized (UserViewDaoImpl.class) {
				if (instance == null) {
					instance = new UserViewDaoImpl();
				}
			}
		}

		return instance;
	}

	public synchronized UserViewVO findByPrimaryKey(Transaction transaction,
			Long id) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		UserViewVO userView = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			StringBuilder queryString = new StringBuilder("from UserViewVO");
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
			userView = (UserViewVO) session.createQuery(queryString.toString())
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

		return userView;
	}

	public synchronized UserViewVO findLoggedUser(Transaction transaction,
			UserViewVO userView) {
		UserViewVO foundedUserView = null;
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor) ConfHibernate
				.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (userView == null) {
				throw new Exception("DAO Exception: UserView is null");
			}

			if (schema == null) {
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("from UserViewVO us");
			StringBuilder conditions = new StringBuilder();
			if (userView.getId() != null) {
				conditions.append(" us.id = :id AND");
			}
			if (userView.getName() != null) {
				conditions.append(" UPPER(us.name) like :name AND");
			}
			if (userView.getSurname() != null) {
				conditions.append(" UPPER(us.surname) like :surname AND");
			}
			if (userView.getDni() != null) {
				conditions.append(" UPPER(us.dni) like :dni AND");
			}
			if (userView.getEmail() != null) {
				conditions.append(" UPPER(us.email) like :email AND");
			}
			if (userView.getIdUserType() != null) {
				conditions.append(" us.`idUserType` = :idUserType AND");
			}
			if (userView.getUsername() != null) {
				conditions.append(" us.username = :username AND");
			}
			if (userView.getPassword() != null) {
				conditions.append(" us.password = :password AND");
			}
			if (userView.getPhone() != null) {
				conditions.append(" UPPER(us.phone) like :phone AND");
			}
			if (userView.getLastLogin() != null) {
				conditions.append(" us.`lastLogin` = :lastLogin AND");
			}
			if (userView.getInitDate() != null) {
				conditions.append(" us.`initDate` = :initDate AND");
			}
			if (userView.getEndDate() != null) {
				conditions.append(" us.`endDate` = :endDate AND");
			}
			if (userView.getIdHermandad() != null) {
				conditions.append(" us.`idHermandad` = :idHermandad AND");
			}
			if (userView.getFechaUltimoPagoHermandad() != null) {
				conditions.append(" us.`fechaUltimoPagoHermandad` = :fechaUltimoPagoHermandad AND");
			}
			if (userView.getFechaBajaHermandad() != null) {
				conditions.append(" us.`fechaBajaHermandad` = :fechaBajaHermandad AND");
			}
			if (userView.getDataSourceName() != null
					&& !userView.getDataSourceName().isEmpty()) {
				conditions.append(" us.`dataSourceName` like :dataSourceName AND");
			}
			if (userView.getIdCssProfile() != null) {
				conditions.append(" us.`idCssProfile` = :idCssProfile AND");
			}
			if (userView.getUrlCssProfilePath() != null) {
				conditions.append(" us.`urlCssProfilePath` = :urlCssProfilePath AND");
			}

			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}

			queryString.append(conditions);

			// Probamos si el usuario esta intentando hacer sql injection
			if (AbstractBean.preventSqlInjection(queryString)) {
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance()
						.getELContext()
						.getELResolver()
						.getValue(
								FacesContext.getCurrentInstance()
										.getELContext(), null,
								"ataqueSQLDaoImpl");

				ataqueSQLDao.createElement(queryString);

				throw new SqlInjectionException(
						"Wrong statements for the query!!");
			}
			Query query = session.createQuery(queryString.toString());
			if (userView.getId() != null) {
				query.setParameter("id", userView.getId());
			}
			if (userView.getUsername() != null) {
				query.setParameter("username", userView.getUsername());
			}
			if (userView.getPassword() != null) {
				query.setParameter("password", userView.getPassword());	
			}
			if (userView.getDataSourceName() != null
					&& !userView.getDataSourceName().isEmpty()) {
				query.setParameter("dataSourceName", "%"+userView.getDataSourceName()+"%");
			}

			foundedUserView = (UserViewVO) query.uniqueResult();
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

		return foundedUserView;
	}

	public synchronized List<UserViewVO> findAll(Transaction transaction,
			String order) {
		List<UserViewVO> list = new ArrayList<UserViewVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			StringBuilder queryString = new StringBuilder("from UserViewVO");
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
								"Find all elements error", ex.getMessage()));
			}
		}

		return list;
	}

	public synchronized List<UserViewVO> findAllByUserType(
			Transaction transaction, String userTypeSelected, String order) {
		List<UserViewVO> list = new ArrayList<UserViewVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (userTypeSelected == null || userTypeSelected.isEmpty()) {
				throw new Exception("DAO Exception: UserView type is null");
			}

			// First of all search the identifier of the userView type selected
			StringBuilder queryString = new StringBuilder(
					"from UserTypeVO WHERE type = '" + userTypeSelected + "'");
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
			UserTypeVO userType = (UserTypeVO) session.createQuery(
					queryString.toString()).uniqueResult();

			// Search of the userView with the type selelected
			queryString = new StringBuilder("from UserViewVO");
			if (userType != null && userType.getId() != null) {
				queryString.append(" WHERE idUserType = " + userType.getId());
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

	public List<UserViewVO> findUsingTemplate(Transaction transaction, UserViewVO userView){
		List<UserViewVO> list = new ArrayList<UserViewVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(userView == null){
				throw new Exception("DAO Exception: UserView is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT us.* FROM "+schema+"`USER_VIEW` us");
			StringBuilder conditions = new StringBuilder();
			if (userView.getId() != null) {
				conditions.append(" us.id = " + userView.getId() + " AND");
			}
			if(userView.getName() != null){
				conditions.append(" UPPER(us.name) like '%"+userView.getName().toUpperCase()+"%' AND");
			}
			if(userView.getSurname() != null){
				conditions.append(" UPPER(us.surname) like '%"+userView.getSurname().toUpperCase()+"%' AND");
			}
			if(userView.getDni() != null){
				conditions.append(" UPPER(us.dni) like '%"+userView.getDni().toUpperCase()+"%' AND");
			}
			if(userView.getEmail() != null){
				conditions.append(" UPPER(us.email) like '%"+userView.getEmail().toUpperCase()+"%' AND");
			}
			if(userView.getIdUserType() != null){
				conditions.append(" us.`idUserType` = '"+userView.getIdUserType()+"' AND");
			}
			if(userView.getUsername() != null){
				conditions.append(" us.username = '"+userView.getUsername()+"' AND");
			}
			if(userView.getPassword() != null){
				conditions.append(" us.password = '"+userView.getPassword()+"' AND");
			}
			if(userView.getPhone() != null){
				conditions.append(" UPPER(us.phone) like '%"+userView.getPhone().toUpperCase()+"%' AND");
			}
			if(userView.getLastLogin() != null){
				conditions.append(" us.`lastLogin` = '"+userView.getLastLogin()+"' AND");
			}
			if(userView.getInitDate() != null){
				conditions.append(" us.`initDate` = '"+userView.getInitDate()+"' AND");
			}
			if(userView.getEndDate() != null){
				conditions.append(" us.`endDate` = '"+userView.getEndDate()+"' AND");
			}
			if(userView.getIdHermandad() != null){
				conditions.append(" us.`idHermandad` = '"+userView.getIdHermandad()+"' AND");
			}
			if(userView.getFechaUltimoPagoHermandad() != null){
				conditions.append(" us.`fechaUltimoPagoHermandad` = '"+userView.getFechaUltimoPagoHermandad()+"' AND");
			}
			if(userView.getFechaBajaHermandad() != null){
				conditions.append(" us.`fechaBajaHermandad` = '"+userView.getFechaBajaHermandad()+"' AND");
			}
			if(userView.getDataSourceName() != null && !userView.getDataSourceName().isEmpty()){
				conditions.append(" us.`dataSourceName` = '"+userView.getDataSourceName()+"' AND");
			}
			if(userView.getIdCssProfile() != null){
				conditions.append(" us.`idCssProfile` = '"+userView.getIdCssProfile()+"' AND");
			}
			if(userView.getUrlCssProfilePath() != null){
				conditions.append(" us.`urlCssProfilePath` = '"+userView.getUrlCssProfilePath()+"' AND");
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
			query.addEntity("us", UserViewVO.class);
			
			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex){
			ex.printStackTrace();
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not elements found", ex.getMessage()));
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

	/**
	 * Update the last login date for the userView
	 */
	public void updateLastLoginDate(Transaction transaction, UserViewVO userView)
			throws DaoException {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (userView == null) {
				throw new DaoException("Element is null");
			}

			if (userView.getId() == null) {
				throw new Exception();
			} else {
				session.merge(userView);

				if (transaction == null) {
					currentTransaction.commit();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Cannot update userView last login", ex
										.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}
	}
}