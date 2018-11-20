package com.infoCofrade.secretaria.statusPorHermano.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.statusPorHermano.vo.StatusPorHermanoVO;


public interface StatusPorHermanoBO{
	public StatusPorHermanoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<StatusPorHermanoVO> findAll(Transaction transaction, String order);
	
	public List<StatusPorHermanoVO> findUsingTemplate(Transaction transaction, StatusPorHermanoVO StatusPorHermano, String order);
	
	public List<StatusPorHermanoVO> findUsingExactTemplate(Transaction transaction, StatusPorHermanoVO StatusPorHermano, String order);
	
	public void createElement(Transaction transaction, StatusPorHermanoVO StatusPorHermano) throws DaoException;
	
	public void deleteElement(Transaction transaction, StatusPorHermanoVO StatusPorHermano);
	
	public void deleteElementByWhere(Transaction transaction, String where);
}