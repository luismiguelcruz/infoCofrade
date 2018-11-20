package com.infoCofrade.administration.secretaria.metodoPago.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.metodoPago.vo.MetodoPagoVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface MetodoPagoBO{
	public MetodoPagoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<MetodoPagoVO> findAll(Transaction transaction, String order);
	
	public List<MetodoPagoVO> findUsingTemplate(Transaction transaction, MetodoPagoVO MetodoPago, String order);
	
	public List<MetodoPagoVO> findUsingExactTemplate(Transaction transaction, MetodoPagoVO MetodoPago, String order);
	
	public void createAllElement(Transaction transaction, List<MetodoPagoVO> listMetodoPago) throws DaoException;
	
	public void createElement(Transaction transaction, MetodoPagoVO MetodoPago) throws DaoException;
	
	public void deleteElement(Transaction transaction, MetodoPagoVO MetodoPago);
}