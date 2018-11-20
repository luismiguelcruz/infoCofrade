package com.infoCofrade.mayordomia.cuotaHermano.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.mayordomia.cuotaHermano.bo.CuotaHermanoBO;
import com.infoCofrade.mayordomia.cuotaHermano.dao.CuotaHermanoDao;
import com.infoCofrade.mayordomia.cuotaHermano.dao.impl.CuotaHermanoDaoImpl;
import com.infoCofrade.mayordomia.cuotaHermano.vo.CuotaHermanoVO;


public class CuotaHermanoBOImpl implements CuotaHermanoBO{
	private CuotaHermanoDao cuotaHermanoDao = CuotaHermanoDaoImpl.getInstance();
	
	public CuotaHermanoVO findByPrimaryKey(Transaction transaction, Long id){
		return cuotaHermanoDao.findByPrimaryKey(transaction, id);
	}
	
	public List<CuotaHermanoVO> findAll(Transaction transaction, String order){
		return cuotaHermanoDao.findAll(transaction, order);
	}
	
	public CuotaHermanoVO findCuotaHermano(Transaction transaction, Integer edad){
		return cuotaHermanoDao.findCuotaHermano(transaction, edad);
	}
	
	public List<CuotaHermanoVO> findEmbeddedItem(Transaction transaction, 
			CuotaHermanoVO cuotaHermano, String order){
		return cuotaHermanoDao.findEmbeddedItem(transaction, cuotaHermano, order);
	}
	
	public List<CuotaHermanoVO> findUsingTemplate(Transaction transaction,
			CuotaHermanoVO cuotaHermano, String order) {
		return cuotaHermanoDao.findUsingTemplate(transaction, cuotaHermano, order);
	}

	@Override
	public List<CuotaHermanoVO> findUsingExactTemplate(Transaction transaction,
			CuotaHermanoVO cuotaHermano, String order) {
		return cuotaHermanoDao.findUsingExactTemplate(transaction, cuotaHermano, order);
	}
	
	public void createElement(Transaction transaction, CuotaHermanoVO cuotaHermano) throws DaoException{
		this.cuotaHermanoDao.createElement(transaction, cuotaHermano);
	}
	
	public void deleteElement(Transaction transaction, CuotaHermanoVO cuotaHermano){
		this.cuotaHermanoDao.deleteElement(transaction, cuotaHermano);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public CuotaHermanoDao getCuotaHermanoDao() {
		return cuotaHermanoDao;
	}

	public void setCuotaHermanoDao(CuotaHermanoDao cuotaHermanoDao) {
		this.cuotaHermanoDao = cuotaHermanoDao;
	}
}