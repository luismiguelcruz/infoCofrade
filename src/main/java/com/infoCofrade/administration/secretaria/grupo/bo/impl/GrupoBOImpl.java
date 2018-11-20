package com.infoCofrade.administration.secretaria.grupo.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.grupo.bo.GrupoBO;
import com.infoCofrade.administration.secretaria.grupo.dao.GrupoDao;
import com.infoCofrade.administration.secretaria.grupo.dao.impl.GrupoDaoImpl;
import com.infoCofrade.administration.secretaria.grupo.vo.GrupoVO;
import com.infoCofrade.common.exceptions.DaoException;


public class GrupoBOImpl implements GrupoBO{
	private GrupoDao grupoDao = GrupoDaoImpl.getInstance();
	
	public GrupoVO findByPrimaryKey(Transaction transaction, Long id){
		return grupoDao.findByPrimaryKey(transaction, id);
	}
	
	public List<GrupoVO> findAll(Transaction transaction, String order){
		return grupoDao.findAll(transaction, order);
	}
	
	public List<GrupoVO> findUsingTemplate(Transaction transaction,
			GrupoVO grupo, String order) {
		return grupoDao.findUsingTemplate(transaction, grupo, order);
	}

	@Override
	public List<GrupoVO> findUsingExactTemplate(Transaction transaction,
			GrupoVO grupo, String order) {
		return grupoDao.findUsingExactTemplate(transaction, grupo, order);
	}
	
	public void createElement(Transaction transaction, GrupoVO grupo) throws DaoException{
		this.grupoDao.createElement(transaction, grupo);
	}
	
	public void deleteElement(Transaction transaction, GrupoVO grupo){
		this.grupoDao.deleteElement(transaction, grupo);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public GrupoDao getGrupoDao() {
		return grupoDao;
	}

	public void setGrupoDao(GrupoDao grupoDao) {
		this.grupoDao = grupoDao;
	}
}