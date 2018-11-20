package com.infoCofrade.administration.pais.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.pais.vo.PaisVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface PaisBO{
	public PaisVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<PaisVO> findAll(Transaction transaction, String order);
	
	public List<PaisVO> findUsingTemplate(Transaction transaction, PaisVO Pais, String order);
	
	public List<PaisVO> findUsingExactTemplate(Transaction transaction, PaisVO Pais, String order);
	
	public void createElement(Transaction transaction, PaisVO Pais) throws DaoException;
	
	public void deleteElement(Transaction transaction, PaisVO Pais);
	
	public void deleteElementByWhere(Transaction transaction, String where);
}