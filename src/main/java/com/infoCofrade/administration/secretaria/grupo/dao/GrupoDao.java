package com.infoCofrade.administration.secretaria.grupo.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.grupo.vo.GrupoVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface GrupoDao {
	public GrupoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<GrupoVO> findAll(Transaction transaction, String order);
	
	public List<GrupoVO> findUsingTemplate(Transaction transaction, GrupoVO grupo, String order);
	
	public List<GrupoVO> findUsingExactTemplate(Transaction transaction, GrupoVO grupo, String order);
	
	public void createElement(Transaction transaction, GrupoVO grupo) throws DaoException;
	
	public void deleteElement(Transaction transaction, GrupoVO grupo);
}