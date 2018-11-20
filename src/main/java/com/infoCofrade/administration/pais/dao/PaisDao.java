package com.infoCofrade.administration.pais.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.pais.vo.PaisVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface PaisDao {
	public PaisVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<PaisVO> findAll(Transaction transaction, String order);
	
	public List<PaisVO> findUsingTemplate(Transaction transaction, PaisVO pais, String order);
	
	public List<PaisVO> findUsingExactTemplate(Transaction transaction, PaisVO pais, String order);
	
	public void createElement(Transaction transaction, PaisVO pais) throws DaoException;
	
	public void deleteElement(Transaction transaction, PaisVO pais);
	
	public void deleteElementByWhere(Transaction transaction, String where);
}