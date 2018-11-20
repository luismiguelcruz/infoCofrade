package com.infoCofrade.administration.provincia.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface ProvinciaBO{
	public ProvinciaVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<ProvinciaVO> findAll(Transaction transaction, String order);
	
	public List<ProvinciaVO> findUsingTemplate(Transaction transaction, ProvinciaVO Provincia, String order);
	
	public List<ProvinciaVO> findUsingExactTemplate(Transaction transaction, ProvinciaVO Provincia, String order);
	
	public List<ProvinciaVO> findAutoComplete(Transaction transaction, ProvinciaVO provincia, String order);
	
	public void createElement(Transaction transaction, ProvinciaVO Provincia) throws DaoException;
	
	public void deleteElement(Transaction transaction, ProvinciaVO Provincia);
}