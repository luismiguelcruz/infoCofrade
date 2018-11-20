package com.infoCofrade.administration.secretaria.motivoBaja.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.motivoBaja.vo.MotivoBajaVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface MotivoBajaDao {
	public MotivoBajaVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<MotivoBajaVO> findAll(Transaction transaction, String order);
	
	public List<MotivoBajaVO> findUsingTemplate(Transaction transaction, MotivoBajaVO motivoBaja, String order);
	
	public List<MotivoBajaVO> findUsingExactTemplate(Transaction transaction, MotivoBajaVO motivoBaja, String order);
	
	public void createElement(Transaction transaction, MotivoBajaVO motivoBaja) throws DaoException;
	
	public void deleteElement(Transaction transaction, MotivoBajaVO motivoBaja);
}