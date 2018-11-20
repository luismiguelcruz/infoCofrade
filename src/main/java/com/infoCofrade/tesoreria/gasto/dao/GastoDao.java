package com.infoCofrade.tesoreria.gasto.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.gasto.vo.GastoVO;


public interface GastoDao {
	public GastoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<GastoVO> findAll(Transaction transaction, String order);
	
	public List<GastoVO> findUsingTemplate(Transaction transaction, GastoVO gasto, String order);
	
	public List<GastoVO> findUsingExactTemplate(Transaction transaction, GastoVO gasto, String order);
	
	public void createElement(Transaction transaction, GastoVO gasto) throws DaoException;
	
	public void deleteElement(Transaction transaction, GastoVO gasto);
}