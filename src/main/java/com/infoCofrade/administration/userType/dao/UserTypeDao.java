package com.infoCofrade.administration.userType.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.userType.vo.UserTypeVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface UserTypeDao {
	public UserTypeVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<UserTypeVO> findAll(Transaction transaction, String order);
	
	public List<UserTypeVO> findByWhere(Transaction transaction, String where, String order);
	
	public List<UserTypeVO> findUsingTemplate(Transaction transaction, UserTypeVO userType);
	
	public Long countUsingTemplate(Transaction transaction, UserTypeVO userType);
	
	public Long countByQuery(Transaction transaction, String query);
	
	public void createElement(Transaction transaction, UserTypeVO userType) throws DaoException;
	
	public void deleteElement(Transaction transaction, UserTypeVO userType);
}