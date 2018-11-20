package com.infoCofrade.tesoreria.ingreso.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.ingreso.vo.IngresoVO;


public interface IngresoBO{
	public IngresoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<IngresoVO> findAll(Transaction transaction, String order);
	
	public List<IngresoVO> findUsingTemplate(Transaction transaction, IngresoVO Ingreso, String order);
	
	public List<IngresoVO> findUsingExactTemplate(Transaction transaction, IngresoVO Ingreso, String order);
	
	public void createElement(Transaction transaction, IngresoVO Ingreso) throws DaoException;
	
	public void deleteElement(Transaction transaction, IngresoVO Ingreso);
}