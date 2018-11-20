package com.infoCofrade.tesoreria.ingreso.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.ingreso.vo.IngresoVO;


public interface IngresoDao {
	public IngresoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<IngresoVO> findAll(Transaction transaction, String order);
	
	public List<IngresoVO> findUsingTemplate(Transaction transaction, IngresoVO ingreso, String order);
	
	public List<IngresoVO> findUsingExactTemplate(Transaction transaction, IngresoVO ingreso, String order);
	
	public void createElement(Transaction transaction, IngresoVO ingreso) throws DaoException;
	
	public void deleteElement(Transaction transaction, IngresoVO ingreso);
}