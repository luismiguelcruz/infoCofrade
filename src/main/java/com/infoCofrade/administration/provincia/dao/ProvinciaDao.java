package com.infoCofrade.administration.provincia.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface ProvinciaDao {
	public ProvinciaVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<ProvinciaVO> findAll(Transaction transaction, String order);
	
	public List<ProvinciaVO> findUsingTemplate(Transaction transaction, ProvinciaVO provincia, String order);
	
	public List<ProvinciaVO> findUsingExactTemplate(Transaction transaction, ProvinciaVO provincia, String order);
	
	public List<ProvinciaVO> findAutoComplete(Transaction transaction, ProvinciaVO provincia, String order);
	
	public void createElement(Transaction transaction, ProvinciaVO provincia) throws DaoException;
	
	public void deleteElement(Transaction transaction, ProvinciaVO provincia);
}