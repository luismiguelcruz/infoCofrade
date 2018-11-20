package com.infoCofrade.mayordomia.facturaCuotaHermano.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.mayordomia.facturaCuotaHermano.vo.FacturaCuotaHermanoVO;


public interface FacturaCuotaHermanoDao {
	public FacturaCuotaHermanoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<FacturaCuotaHermanoVO> findAll(Transaction transaction, String order);
	
	public List<FacturaCuotaHermanoVO> findUsingTemplate(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano, String order);
	
	public List<FacturaCuotaHermanoVO> findUsingExactTemplate(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano, String order);
	
	public void createElement(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano) throws DaoException;
	
	public void createElement(Transaction transaction, List<FacturaCuotaHermanoVO> listFacturasParaCrear) throws DaoException;
	
	public void createStructureElement(Transaction transaction, List<List<FacturaCuotaHermanoVO>> listFacturasParaCrear) throws DaoException;
	
	public void deleteElement(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano);
}