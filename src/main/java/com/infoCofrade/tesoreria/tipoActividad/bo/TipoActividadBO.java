package com.infoCofrade.tesoreria.tipoActividad.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.tipoActividad.vo.TipoActividadVO;


public interface TipoActividadBO{
	public TipoActividadVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<TipoActividadVO> findAll(Transaction transaction, String order);
	
	public List<TipoActividadVO> findUsingTemplate(Transaction transaction, TipoActividadVO TipoActividad, String order);
	
	public List<TipoActividadVO> findUsingExactTemplate(Transaction transaction, TipoActividadVO TipoActividad, String order);
	
	public void createElement(Transaction transaction, TipoActividadVO TipoActividad) throws DaoException;
	
	public void deleteElement(Transaction transaction, TipoActividadVO TipoActividad);
}