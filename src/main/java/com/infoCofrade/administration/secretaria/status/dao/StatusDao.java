package com.infoCofrade.administration.secretaria.status.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.status.vo.StatusVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface StatusDao {
	public StatusVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<StatusVO> findAll(Transaction transaction, String order);
	
	public List<StatusVO> findUsingTemplate(Transaction transaction, StatusVO status, String order);
	
	public List<StatusVO> findUsingExactTemplate(Transaction transaction, StatusVO status, String order);
	
	public void createElement(Transaction transaction, StatusVO status) throws DaoException;
	
	public void deleteElement(Transaction transaction, StatusVO status);
	
	public void deleteElementByWhere(Transaction transaction, String where);
}