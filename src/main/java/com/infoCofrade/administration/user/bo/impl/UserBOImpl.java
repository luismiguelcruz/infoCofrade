package com.infoCofrade.administration.user.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.user.bo.UserBO;
import com.infoCofrade.administration.user.dao.UserDao;
import com.infoCofrade.administration.user.dao.impl.UserDaoImpl;
import com.infoCofrade.administration.user.vo.UserVO;
import com.infoCofrade.common.exceptions.DaoException;


public class UserBOImpl implements UserBO{
	private UserDao userDao = UserDaoImpl.getInstance();
	
	public UserVO findByPrimaryKey(Transaction transaction, Long id){
		return userDao.findByPrimaryKey(transaction, id);
	}
	
	public UserVO findLoggedUser(Transaction transaction, UserVO user){
		return userDao.findLoggedUser(transaction, user);
	}
	
	public List<UserVO> findAll(Transaction transaction, String order){
		return userDao.findAll(transaction, order);
	}
	
	public List<UserVO> findAllByUserType(Transaction transaction, String userType, String order){
		return userDao.findAllByUserType(transaction, userType, order);
	}
	
	public List<UserVO> findUsingTemplate(Transaction transaction, UserVO user){
		return userDao.findUsingTemplate(transaction, user);
	}
	
	public void createElement(Transaction transaction, UserVO user) throws DaoException{
		this.userDao.createElement(transaction, user);
	}
	
	public void updateLastLoginDate(Transaction transaction, UserVO user) throws DaoException{
		this.userDao.updateLastLoginDate(transaction, user);
	}
	
	public void deleteElement(Transaction transaction, UserVO user){
		this.userDao.deleteElement(transaction, user);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}