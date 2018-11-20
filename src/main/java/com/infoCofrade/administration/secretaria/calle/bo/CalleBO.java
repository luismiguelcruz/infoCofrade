package com.infoCofrade.administration.secretaria.calle.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.calle.vo.CalleVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface CalleBO{
	public CalleVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<CalleVO> findAll(Transaction transaction, String order);
	
	public List<CalleVO> findUsingTemplate(Transaction transaction, CalleVO Calle, String order);
	
	public List<CalleVO> findUsingExactTemplate(Transaction transaction, CalleVO Calle, String order);
	
	public void createElement(Transaction transaction, CalleVO Calle) throws DaoException;
	
	public void deleteElement(Transaction transaction, CalleVO Calle);
}