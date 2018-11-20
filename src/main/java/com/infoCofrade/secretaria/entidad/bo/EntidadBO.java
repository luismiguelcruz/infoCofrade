package com.infoCofrade.secretaria.entidad.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.entidad.vo.EntidadVO;


public interface EntidadBO{
	public EntidadVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<EntidadVO> findAll(Transaction transaction, String order);
	
	public List<EntidadVO> findUsingTemplate(Transaction transaction, EntidadVO Entidad, String order);
	
	public List<EntidadVO> findUsingExactTemplate(Transaction transaction, EntidadVO Entidad, String order);
	
	public void createElement(Transaction transaction, EntidadVO Entidad) throws DaoException;
	
	public void deleteElement(Transaction transaction, EntidadVO Entidad);
}