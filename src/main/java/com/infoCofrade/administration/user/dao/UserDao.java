package com.infoCofrade.administration.user.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.user.vo.UserVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface UserDao {
	public UserVO findByPrimaryKey(Transaction transaction, Long id);
	
	public UserVO findLoggedUser(Transaction transaction, UserVO user);
	
	public List<UserVO> findAll(Transaction transaction, String order);
	
	public List<UserVO> findAllByUserType(Transaction transaction, String userType, String order);	
	
	public List<UserVO> findUsingTemplate(Transaction transaction, UserVO user);
	
	public void createElement(Transaction transaction, UserVO user) throws DaoException;
	
	public void updateLastLoginDate(Transaction transaction, UserVO user) throws DaoException;
	
	public void deleteElement(Transaction transaction, UserVO user);
}