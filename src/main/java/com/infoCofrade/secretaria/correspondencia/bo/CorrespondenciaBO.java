package com.infoCofrade.secretaria.correspondencia.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.correspondencia.vo.CorrespondenciaVO;


public interface CorrespondenciaBO{
	public CorrespondenciaVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<CorrespondenciaVO> findAll(Transaction transaction, String order);
	
	public List<CorrespondenciaVO> findUsingTemplate(Transaction transaction, CorrespondenciaVO Correspondencia, String order);
	
	public List<CorrespondenciaVO> findUsingExactTemplate(Transaction transaction, CorrespondenciaVO Correspondencia, String order);
	
	public void createElement(Transaction transaction, CorrespondenciaVO Correspondencia) throws DaoException;
	
	public void deleteElement(Transaction transaction, CorrespondenciaVO Correspondencia);
}