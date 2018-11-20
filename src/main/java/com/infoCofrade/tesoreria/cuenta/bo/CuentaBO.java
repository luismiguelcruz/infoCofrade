package com.infoCofrade.tesoreria.cuenta.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.cuenta.vo.CuentaVO;


public interface CuentaBO{
	public CuentaVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<CuentaVO> findAll(Transaction transaction, String order);
	
	public List<CuentaVO> findUsingTemplate(Transaction transaction, CuentaVO cuenta, String order);
	
	public List<CuentaVO> findUsingTemplateForLevel(Transaction transaction, CuentaVO cuenta, String order);
	
	public List<CuentaVO> findUsingExactTemplate(Transaction transaction, CuentaVO cuenta, String order);
	
	public void createElement(Transaction transaction, CuentaVO cuenta) throws DaoException;
	
	public void deleteElement(Transaction transaction, CuentaVO cuenta);
}