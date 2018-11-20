package com.infoCofrade.administration.hermandad.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.hermandad.vo.HermandadVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface HermandadDao {
	public HermandadVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<HermandadVO> findAll(Transaction transaction, String order);
	
	public List<HermandadVO> findUsingTemplate(Transaction transaction, HermandadVO hermandad, String order);
	
	public List<HermandadVO> findUsingExactTemplate(Transaction transaction, HermandadVO hermandad, String order);
	
	public void createElement(Transaction transaction, HermandadVO hermandad) throws DaoException;
	
	public void deleteElement(Transaction transaction, HermandadVO hermandad);
}