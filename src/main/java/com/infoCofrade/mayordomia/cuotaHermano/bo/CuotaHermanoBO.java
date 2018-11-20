package com.infoCofrade.mayordomia.cuotaHermano.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.mayordomia.cuotaHermano.vo.CuotaHermanoVO;


public interface CuotaHermanoBO{
	public CuotaHermanoVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<CuotaHermanoVO> findAll(Transaction transaction, String order);
	
	public CuotaHermanoVO findCuotaHermano(Transaction transaction, Integer edad);
	
	public List<CuotaHermanoVO> findEmbeddedItem(Transaction transaction, CuotaHermanoVO cuotaHermano, String order);
	
	public List<CuotaHermanoVO> findUsingTemplate(Transaction transaction, CuotaHermanoVO CuotaHermano, String order);
	
	public List<CuotaHermanoVO> findUsingExactTemplate(Transaction transaction, CuotaHermanoVO CuotaHermano, String order);
	
	public void createElement(Transaction transaction, CuotaHermanoVO CuotaHermano) throws DaoException;
	
	public void deleteElement(Transaction transaction, CuotaHermanoVO CuotaHermano);
}