package com.infoCofrade.secretaria.acto.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.acto.vo.ActoVO;


public interface ActoDao {
	public ActoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<ActoVO> findAll(Transaction transaction, String order);
	
	public List<ActoVO> findActoByYear(Transaction transaction, Integer anyo, String order);
	
	public List<ActoVO> findUsingTemplate(Transaction transaction, ActoVO acto, String order);
	
	public List<ActoVO> findUsingExactTemplate(Transaction transaction, ActoVO acto, String order);
	
	public void createElement(Transaction transaction, ActoVO acto) throws DaoException;
	
	public void deleteElement(Transaction transaction, ActoVO acto);
}