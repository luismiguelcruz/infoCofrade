package com.infoCofrade.administration.tipoVia.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.tipoVia.vo.TipoViaVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface TipoViaBO{
	public TipoViaVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<TipoViaVO> findAll(Transaction transaction, String order);
	
	public List<TipoViaVO> findUsingTemplate(Transaction transaction, TipoViaVO TipoVia, String order);
	
	public List<TipoViaVO> findUsingExactTemplate(Transaction transaction, TipoViaVO TipoVia, String order);
	
	public void createElement(Transaction transaction, TipoViaVO TipoVia) throws DaoException;
	
	public void deleteElement(Transaction transaction, TipoViaVO TipoVia);
}