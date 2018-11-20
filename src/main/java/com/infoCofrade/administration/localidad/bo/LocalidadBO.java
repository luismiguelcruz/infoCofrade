package com.infoCofrade.administration.localidad.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface LocalidadBO{
	public LocalidadVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<LocalidadVO> findAll(Transaction transaction, String order);
	
	public List<LocalidadVO> findUsingTemplate(Transaction transaction, LocalidadVO Localidad, String order);
	
	public List<LocalidadVO> findUsingExactTemplate(Transaction transaction, LocalidadVO Localidad, String order);
	
	public List<LocalidadVO> findAutoComplete(Transaction transaction, LocalidadVO localidad, String order);
	
	public void createElement(Transaction transaction, LocalidadVO Localidad) throws DaoException;
	
	public void deleteElement(Transaction transaction, LocalidadVO Localidad);
}