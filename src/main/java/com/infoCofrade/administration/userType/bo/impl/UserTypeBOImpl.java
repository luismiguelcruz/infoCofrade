package com.infoCofrade.administration.userType.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.userType.bo.UserTypeBO;
import com.infoCofrade.administration.userType.dao.UserTypeDao;
import com.infoCofrade.administration.userType.dao.impl.UserTypeDaoImpl;
import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.exceptions.DaoException;


public class UserTypeBOImpl implements UserTypeBO{
	private UserTypeDao userTypeDao = UserTypeDaoImpl.getInstance();
	
	public UserTypeVO findByPrimaryKey(Transaction transaction, Long id){
		return userTypeDao.findByPrimaryKey(transaction, id);
	}
	
	public List<UserTypeVO> findAll(Transaction transaction, String order){
		return userTypeDao.findAll(transaction, order);
	}
	
	public List<UserTypeVO> findByWhere(Transaction transaction, String where, String order){
		return userTypeDao.findByWhere(transaction, where, order);
	}
	
	public List<UserTypeVO> findUsingTemplate(Transaction transaction, UserTypeVO userType){
		return userTypeDao.findUsingTemplate(transaction, userType);
	}
	
	public void createElement(Transaction transaction, UserTypeVO userType) throws DaoException{
		this.userTypeDao.createElement(transaction, userType);
	}
	
	public void deleteElement(Transaction transaction, UserTypeVO userType){
		this.userTypeDao.deleteElement(transaction, userType);
	}
}