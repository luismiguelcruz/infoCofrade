package com.infoCofrade.administration.secretaria.metodoPago.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.metodoPago.vo.MetodoPagoVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface MetodoPagoDao {
	public MetodoPagoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<MetodoPagoVO> findAll(Transaction transaction, String order);
	
	public List<MetodoPagoVO> findUsingTemplate(Transaction transaction, MetodoPagoVO metodoPago, String order);
	
	public List<MetodoPagoVO> findUsingExactTemplate(Transaction transaction, MetodoPagoVO metodoPago, String order);
	
	public void createAllElement(Transaction transaction, List<MetodoPagoVO> listMetodoPago) throws DaoException;
	
	public void createElement(Transaction transaction, MetodoPagoVO metodoPago) throws DaoException;
	
	public void deleteElement(Transaction transaction, MetodoPagoVO metodoPago);
}