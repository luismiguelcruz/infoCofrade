package com.infoCofrade.administration.userView.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.userView.bo.UserViewBO;
import com.infoCofrade.administration.userView.dao.UserViewDao;
import com.infoCofrade.administration.userView.dao.impl.UserViewDaoImpl;
import com.infoCofrade.administration.userView.vo.UserViewVO;
import com.infoCofrade.common.exceptions.DaoException;


public class UserViewBOImpl implements UserViewBO{
	private UserViewDao userViewDao = UserViewDaoImpl.getInstance();
	
	public UserViewVO findByPrimaryKey(Transaction transaction, Long id){
		return userViewDao.findByPrimaryKey(transaction, id);
	}
	
	public UserViewVO findLoggedUser(Transaction transaction, UserViewVO userView){
		return userViewDao.findLoggedUser(transaction, userView);
	}
	
	public List<UserViewVO> findAll(Transaction transaction, String order){
		return userViewDao.findAll(transaction, order);
	}
	
	public List<UserViewVO> findAllByUserType(Transaction transaction, String userViewType, String order){
		return userViewDao.findAllByUserType(transaction, userViewType, order);
	}
	
	public List<UserViewVO> findUsingTemplate(Transaction transaction, UserViewVO userView){
		return userViewDao.findUsingTemplate(transaction, userView);
	}

	public void updateLastLoginDate(Transaction transaction, UserViewVO userView) throws DaoException{
		this.userViewDao.updateLastLoginDate(transaction, userView);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public UserViewDao getUserViewDao() {
		return userViewDao;
	}

	public void setUserViewDao(UserViewDao userViewDao) {
		this.userViewDao = userViewDao;
	}
}