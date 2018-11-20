package com.infoCofrade.mayordomia.facturaCuotaHermano.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.mayordomia.facturaCuotaHermano.bo.FacturaCuotaHermanoBO;
import com.infoCofrade.mayordomia.facturaCuotaHermano.dao.FacturaCuotaHermanoDao;
import com.infoCofrade.mayordomia.facturaCuotaHermano.dao.impl.FacturaCuotaHermanoDaoImpl;
import com.infoCofrade.mayordomia.facturaCuotaHermano.vo.FacturaCuotaHermanoVO;


public class FacturaCuotaHermanoBOImpl implements FacturaCuotaHermanoBO{
	private FacturaCuotaHermanoDao facturaCuotaHermanoDao = FacturaCuotaHermanoDaoImpl.getInstance();
	
	public FacturaCuotaHermanoVO findByPrimaryKey(Transaction transaction, Long id){
		return facturaCuotaHermanoDao.findByPrimaryKey(transaction, id);
	}
	
	public List<FacturaCuotaHermanoVO> findAll(Transaction transaction, String order){
		return facturaCuotaHermanoDao.findAll(transaction, order);
	}
	
	public List<FacturaCuotaHermanoVO> findUsingTemplate(Transaction transaction,
			FacturaCuotaHermanoVO facturaCuotaHermano, String order) {
		return facturaCuotaHermanoDao.findUsingTemplate(transaction, facturaCuotaHermano, order);
	}

	@Override
	public List<FacturaCuotaHermanoVO> findUsingExactTemplate(Transaction transaction,
			FacturaCuotaHermanoVO facturaCuotaHermano, String order) {
		return facturaCuotaHermanoDao.findUsingExactTemplate(transaction, facturaCuotaHermano, order);
	}
	
	public void createElement(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano) throws DaoException{
		this.facturaCuotaHermanoDao.createElement(transaction, facturaCuotaHermano);
	}
	
	public void createElement(Transaction transaction, List<FacturaCuotaHermanoVO> listFacturasParaCrear) throws DaoException{
		this.facturaCuotaHermanoDao.createElement(transaction, listFacturasParaCrear);
	}
	
	public void createStructureElement(Transaction transaction, List<List<FacturaCuotaHermanoVO>> listFacturasParaCrear) throws DaoException{
		this.facturaCuotaHermanoDao.createStructureElement(transaction, listFacturasParaCrear);
	}
	
	public void deleteElement(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano){
		this.facturaCuotaHermanoDao.deleteElement(transaction, facturaCuotaHermano);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public FacturaCuotaHermanoDao getFacturaCuotaHermanoDao() {
		return facturaCuotaHermanoDao;
	}

	public void setFacturaCuotaHermanoDao(FacturaCuotaHermanoDao facturaCuotaHermanoDao) {
		this.facturaCuotaHermanoDao = facturaCuotaHermanoDao;
	}
}