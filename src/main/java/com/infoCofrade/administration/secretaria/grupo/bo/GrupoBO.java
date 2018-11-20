package com.infoCofrade.administration.secretaria.grupo.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.grupo.vo.GrupoVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface GrupoBO{
	public GrupoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<GrupoVO> findAll(Transaction transaction, String order);
	
	public List<GrupoVO> findUsingTemplate(Transaction transaction, GrupoVO Grupo, String order);
	
	public List<GrupoVO> findUsingExactTemplate(Transaction transaction, GrupoVO Grupo, String order);
	
	public void createElement(Transaction transaction, GrupoVO Grupo) throws DaoException;
	
	public void deleteElement(Transaction transaction, GrupoVO Grupo);
}