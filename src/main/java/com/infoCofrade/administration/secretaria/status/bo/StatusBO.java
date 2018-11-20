package com.infoCofrade.administration.secretaria.status.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.status.vo.StatusVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface StatusBO{
	public StatusVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<StatusVO> findAll(Transaction transaction, String order);
	
	public List<StatusVO> findUsingTemplate(Transaction transaction, StatusVO Status, String order);
	
	public List<StatusVO> findUsingExactTemplate(Transaction transaction, StatusVO Status, String order);
	
	public void createElement(Transaction transaction, StatusVO Status) throws DaoException;
	
	public void deleteElement(Transaction transaction, StatusVO Status);
	
	public void deleteElementByWhere(Transaction transaction, String where);
}