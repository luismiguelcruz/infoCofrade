package com.infoCofrade.secretaria.entidad.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.entidad.vo.EntidadVO;


public interface EntidadDao {
	public EntidadVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<EntidadVO> findAll(Transaction transaction, String order);
	
	public List<EntidadVO> findUsingTemplate(Transaction transaction, EntidadVO entidad, String order);
	
	public List<EntidadVO> findUsingExactTemplate(Transaction transaction, EntidadVO entidad, String order);
	
	public void createElement(Transaction transaction, EntidadVO entidad) throws DaoException;
	
	public void deleteElement(Transaction transaction, EntidadVO entidad);
}