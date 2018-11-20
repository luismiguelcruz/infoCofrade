package com.infoCofrade.administration.secretaria.motivoBaja.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.motivoBaja.vo.MotivoBajaVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface MotivoBajaBO{
	public MotivoBajaVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<MotivoBajaVO> findAll(Transaction transaction, String order);
	
	public List<MotivoBajaVO> findUsingTemplate(Transaction transaction, MotivoBajaVO MotivoBaja, String order);
	
	public List<MotivoBajaVO> findUsingExactTemplate(Transaction transaction, MotivoBajaVO MotivoBaja, String order);
	
	public void createElement(Transaction transaction, MotivoBajaVO MotivoBaja) throws DaoException;
	
	public void deleteElement(Transaction transaction, MotivoBajaVO MotivoBaja);
}