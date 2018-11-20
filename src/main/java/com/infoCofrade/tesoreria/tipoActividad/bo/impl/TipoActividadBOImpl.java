package com.infoCofrade.tesoreria.tipoActividad.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.tipoActividad.bo.TipoActividadBO;
import com.infoCofrade.tesoreria.tipoActividad.dao.TipoActividadDao;
import com.infoCofrade.tesoreria.tipoActividad.dao.impl.TipoActividadDaoImpl;
import com.infoCofrade.tesoreria.tipoActividad.vo.TipoActividadVO;


public class TipoActividadBOImpl implements TipoActividadBO{
	private TipoActividadDao tipoActividadDao = TipoActividadDaoImpl.getInstance();
	
	public TipoActividadVO findByPrimaryKey(Transaction transaction, Long id){
		return tipoActividadDao.findByPrimaryKey(transaction, id);
	}
	
	public List<TipoActividadVO> findAll(Transaction transaction, String order){
		return tipoActividadDao.findAll(transaction, order);
	}
	
	public List<TipoActividadVO> findUsingTemplate(Transaction transaction,
			TipoActividadVO tipoActividad, String order) {
		return tipoActividadDao.findUsingTemplate(transaction, tipoActividad, order);
	}

	@Override
	public List<TipoActividadVO> findUsingExactTemplate(Transaction transaction,
			TipoActividadVO tipoActividad, String order) {
		return tipoActividadDao.findUsingExactTemplate(transaction, tipoActividad, order);
	}
	
	public void createElement(Transaction transaction, TipoActividadVO tipoActividad) throws DaoException{
		this.tipoActividadDao.createElement(transaction, tipoActividad);
	}
	
	public void deleteElement(Transaction transaction, TipoActividadVO tipoActividad){
		this.tipoActividadDao.deleteElement(transaction, tipoActividad);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public TipoActividadDao getTipoActividadDao() {
		return tipoActividadDao;
	}

	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao;
	}
}