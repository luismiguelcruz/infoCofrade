package com.infoCofrade.administration.userView.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.userView.vo.UserViewVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface UserViewBO{
	public UserViewVO findByPrimaryKey(Transaction transaction, Long id);
	
	public UserViewVO findLoggedUser(Transaction transaction, UserViewVO userView);
	
	public List<UserViewVO> findAll(Transaction transaction, String order);
	
	public List<UserViewVO> findAllByUserType(Transaction transaction, String userViewType, String order);
	
	public List<UserViewVO> findUsingTemplate(Transaction transaction, UserViewVO userView);
	
	public void updateLastLoginDate(Transaction transaction, UserViewVO userView) throws DaoException;
}