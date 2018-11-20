package com.infoCofrade.secretaria.statusPorHermano.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.statusPorHermano.vo.StatusPorHermanoVO;


public interface StatusPorHermanoDao {
	public StatusPorHermanoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<StatusPorHermanoVO> findAll(Transaction transaction, String order);
	
	public List<StatusPorHermanoVO> findUsingTemplate(Transaction transaction, StatusPorHermanoVO statusPorHermano, String order);
	
	public List<StatusPorHermanoVO> findUsingExactTemplate(Transaction transaction, StatusPorHermanoVO statusPorHermano, String order);
	
	public void createElement(Transaction transaction, StatusPorHermanoVO statusPorHermano) throws DaoException;
	
	public void deleteElement(Transaction transaction, StatusPorHermanoVO statusPorHermano);
	
	public void deleteElementByWhere(Transaction transaction, String where);
}