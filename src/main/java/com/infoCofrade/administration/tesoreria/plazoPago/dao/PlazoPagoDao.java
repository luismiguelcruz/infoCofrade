package com.infoCofrade.administration.tesoreria.plazoPago.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.tesoreria.plazoPago.vo.PlazoPagoVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface PlazoPagoDao {
	public PlazoPagoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<PlazoPagoVO> findAll(Transaction transaction, String order);
	
	public List<PlazoPagoVO> findUsingTemplate(Transaction transaction, PlazoPagoVO plazoPago, String order);
	
	public List<PlazoPagoVO> findUsingExactTemplate(Transaction transaction, PlazoPagoVO plazoPago, String order);
	
	public void createAllElement(Transaction transaction, List<PlazoPagoVO> listPlazoPago) throws DaoException;
	
	public void createElement(Transaction transaction, PlazoPagoVO plazoPago) throws DaoException;
	
	public void deleteElement(Transaction transaction, PlazoPagoVO plazoPago);
}