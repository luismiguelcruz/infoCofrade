package com.infoCofrade.administration.user.dao.impl;

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
import com.infoCofrade.administration.user.dao.UserDao;
import com.infoCofrade.administration.user.vo.UserVO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;


@Component("userDaoImpl")
public class UserDaoImpl implements UserDao{
	private static UserDao instance = new UserDaoImpl();
	private Session session;
	
	private UserDaoImpl(){}
	
	public static UserDao getInstance(){
		if (instance == null) {
            synchronized(UserDaoImpl.class) {
                if (instance == null) { 
                	instance = new UserDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized UserVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		UserVO user = null;
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
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
			
			StringBuilder queryString = new StringBuilder("SELECT us.* FROM "+schema+"`USER` us");
			if(id != null) {
				queryString.append(" WHERE us.id = " + id);
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
			query.addEntity("us", UserVO.class);
			
			user = (UserVO)query.uniqueResult();
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
		
		return user;
	}
	
	public synchronized UserVO findLoggedUser(Transaction transaction, UserVO user){
		UserVO foundedUser = null;
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;
		
		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(user == null){
				throw new Exception("DAO Exception: User is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT us.* FROM "+schema+"`USER` us");
			StringBuilder conditions = new StringBuilder();
			if (user.getId() != null) {
				conditions.append(" us.id = " + user.getId() + " AND");
			}
			if(user.getName() != null){
				conditions.append(" UPPER(us.name) like '%"+user.getName().toUpperCase()+"%' AND");
			}
			if(user.getSurname() != null){
				conditions.append(" UPPER(us.surname) like '%"+user.getSurname().toUpperCase()+"%' AND");
			}
			if(user.getDni() != null){
				conditions.append(" UPPER(us.dni) like '%"+user.getDni().toUpperCase()+"%' AND");
			}
			if(user.getEmail() != null){
				conditions.append(" UPPER(us.email) like '%"+user.getEmail().toUpperCase()+"%' AND");
			}
			if(user.getIdUserType() != null){
				conditions.append(" us.`idUserType` = '"+user.getIdUserType()+"' AND");
			}
			if(user.getUsername() != null){
				conditions.append(" us.username = '"+user.getUsername()+"' AND");
			}
			if(user.getPassword() != null){
				conditions.append(" us.password = '"+user.getPassword()+"' AND");
			}
			if(user.getPhone() != null){
				conditions.append(" UPPER(us.phone) like '%"+user.getPhone().toUpperCase()+"%' AND");
			}
			if(user.getLastLogin() != null){
				conditions.append(" us.`lastLogin` = '"+user.getLastLogin()+"' AND");
			}
			if(user.getInitDate() != null){
				conditions.append(" us.`initDate` = '"+user.getInitDate()+"' AND");
			}
			if(user.getEndDate() != null){
				conditions.append(" us.`endDate` = '"+user.getEndDate()+"' AND");
			}
			if(user.getIdHermandad() != null){
				conditions.append(" us.`idHermandad` = '"+user.getIdHermandad()+"' AND");
			}
			if(user.getProfileImagePath() != null && !user.getProfileImagePath().isEmpty()){
				conditions.append(" us.`profileImagePath` = '"+user.getProfileImagePath()+"' AND");
			}
			if(user.getIdCssProfile() != null){
				conditions.append(" us.`idCssProfile` = '"+user.getIdCssProfile()+"' AND");
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
			query.addEntity("us", UserVO.class);
			
			foundedUser = (UserVO)query.uniqueResult();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex){
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not elements found", ex.getMessage()));
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex){
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return foundedUser;
	}
	
	public synchronized List<UserVO> findAll(Transaction transaction, String order){
		List<UserVO> list = new ArrayList<UserVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from UserVO");
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
			Query query = session.createQuery(queryString.toString());
			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex){
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not elements found", ex.getMessage()));
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex){
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find all elements error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	public synchronized List<UserVO> findAllByUserType(Transaction transaction, String userTypeSelected, String order){
		List<UserVO> list = new ArrayList<UserVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(userTypeSelected == null || userTypeSelected.isEmpty()){
				throw new Exception("DAO Exception: User type is null");
			}
			
			// First of all search the identifier of the user type selected
			StringBuilder queryString = new StringBuilder("from UserTypeVO WHERE type = '"+userTypeSelected+"'");
			if(order != null && !order.isEmpty()){
				queryString.append(" ORDER BY "+order);
			}
			
			UserTypeVO userType = (UserTypeVO)session.createQuery(queryString.toString()).uniqueResult();
			
			// Search of the user with the type selelected
			queryString = new StringBuilder("from UserVO");
			if(userType != null && userType.getId() != null){
				queryString.append(" WHERE idUserType = "+userType.getId());
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
			
			if(transaction == null){
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex){
			if(transaction == null){
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not elements found", ex.getMessage()));
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex){
			if(transaction == null){
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	public List<UserVO> findUsingTemplate(Transaction transaction, UserVO user){
		List<UserVO> list = new ArrayList<UserVO>();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(user == null){
				throw new Exception("DAO Exception: User is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT us.* FROM "+schema+"`USER` us");
			StringBuilder conditions = new StringBuilder();
			if (user.getId() != null) {
				conditions.append(" us.id = " + user.getId() + " AND");
			}
			if(user.getName() != null){
				conditions.append(" UPPER(us.name) like '%"+user.getName().toUpperCase()+"%' AND");
			}
			if(user.getSurname() != null){
				conditions.append(" UPPER(us.surname) like '%"+user.getSurname().toUpperCase()+"%' AND");
			}
			if(user.getDni() != null){
				conditions.append(" UPPER(us.dni) like '%"+user.getDni().toUpperCase()+"%' AND");
			}
			if(user.getEmail() != null){
				conditions.append(" UPPER(us.email) like '%"+user.getEmail().toUpperCase()+"%' AND");
			}
			if(user.getIdUserType() != null){
				conditions.append(" us.`idUserType` = '"+user.getIdUserType()+"' AND");
			}
			if(user.getUsername() != null){
				conditions.append(" us.username = '"+user.getUsername()+"' AND");
			}
			if(user.getPassword() != null){
				conditions.append(" us.password = '"+user.getPassword()+"' AND");
			}
			if(user.getPhone() != null){
				conditions.append(" UPPER(us.phone) like '%"+user.getPhone().toUpperCase()+"%' AND");
			}
			if(user.getLastLogin() != null){
				conditions.append(" us.`lastLogin` = '"+user.getLastLogin()+"' AND");
			}
			if(user.getInitDate() != null){
				conditions.append(" us.`initDate` = '"+user.getInitDate()+"' AND");
			}
			if(user.getEndDate() != null){
				conditions.append(" us.`endDate` = '"+user.getEndDate()+"' AND");
			}
			if(user.getIdHermandad() != null){
				conditions.append(" us.`idHermandad` = '"+user.getIdHermandad()+"' AND");
			}
			if(user.getProfileImagePath() != null && !user.getProfileImagePath().isEmpty()){
				conditions.append(" us.`profileImagePath` = '"+user.getProfileImagePath()+"' AND");
			}
			if(user.getIdCssProfile() != null){
				conditions.append(" us.`idCssProfile` = '"+user.getIdCssProfile()+"' AND");
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
			query.addEntity("us", UserVO.class);
			
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
	public synchronized void createElement(Transaction transaction, UserVO user) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (user == null) {
				throw new DaoException("Element is null");
			}
			
			if(user.getId() == null){
				// Comprobamos que no existe ningún usuario con el mismo nombre de usuario en la aplicación y password
				UserVO userSearch = new UserVO();
				userSearch.setUsername(user.getUsername());
				//userSearch.setPassword(user.getPassword());
				List<UserVO> listAux = this.findUsingTemplate(currentTransaction, userSearch);
				if(listAux != null && listAux.size() > 0){
					throw new Exception("Ya existe un usuario con el mismo nombre de usuario en la base de datos");
				}
				
				session.save(user);
				
				if (transaction == null) {
					FacesContext.getCurrentInstance().addMessage(null, 
							new FacesMessage(FacesMessage.SEVERITY_INFO,"Database info: ",
									"Usuario creado correctamente"));
					currentTransaction.commit();
				}
			} else {
				session.merge(user);
				
				if (transaction == null) {
					FacesContext.getCurrentInstance().addMessage(null, 
							new FacesMessage(FacesMessage.SEVERITY_INFO,"Database info: ", 
									"Se ha actualizado el perfil del usuario"));
					currentTransaction.commit();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo crear el elemento", ex.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}
	}
	
	/**
	 * Update the last login date for the user
	 */
	public void updateLastLoginDate(Transaction transaction, UserVO user) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (user == null) {
				throw new DaoException("Element is null");
			}
			
			if(user.getId() == null){
				throw new Exception();
			} else {
				session.merge(user);
				
				if (transaction == null) {
					currentTransaction.commit();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cannot update user last login", ex.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}
	}

	/**
	 * Delete an element from the Database
	 */
	public synchronized void deleteElement(Transaction transaction, UserVO user) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (user == null) {
				throw new Exception("Element is null");
			}

			session.delete(user);
			currentTransaction.commit();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Database info: ", "Element deleted correctly"));
		} catch (Exception ex) {
			ex.printStackTrace();
			currentTransaction.rollback();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cannot delete error", ex.getMessage()));
		}
	}
}