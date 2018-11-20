package com.infoCofrade.administration.tipoVia.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.tipoVia.vo.TipoViaVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface TipoViaDao {
	public TipoViaVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<TipoViaVO> findAll(Transaction transaction, String order);
	
	public List<TipoViaVO> findUsingTemplate(Transaction transaction, TipoViaVO tipoVia, String order);
	
	public List<TipoViaVO> findUsingExactTemplate(Transaction transaction, TipoViaVO tipoVia, String order);
	
	public void createElement(Transaction transaction, TipoViaVO tipoVia) throws DaoException;
	
	public void deleteElement(Transaction transaction, TipoViaVO tipoVia);
}