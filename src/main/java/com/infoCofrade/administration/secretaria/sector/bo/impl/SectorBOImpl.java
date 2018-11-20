package com.infoCofrade.administration.secretaria.sector.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.sector.bo.SectorBO;
import com.infoCofrade.administration.secretaria.sector.dao.SectorDao;
import com.infoCofrade.administration.secretaria.sector.dao.impl.SectorDaoImpl;
import com.infoCofrade.administration.secretaria.sector.vo.SectorVO;
import com.infoCofrade.common.exceptions.DaoException;


public class SectorBOImpl implements SectorBO{
	private SectorDao sectorDao = SectorDaoImpl.getInstance();
	
	public SectorVO findByPrimaryKey(Transaction transaction, Long id){
		return sectorDao.findByPrimaryKey(transaction, id);
	}
	
	public List<SectorVO> findAll(Transaction transaction, String order){
		return sectorDao.findAll(transaction, order);
	}
	
	public List<SectorVO> findUsingTemplate(Transaction transaction,
			SectorVO sector, String order) {
		return sectorDao.findUsingTemplate(transaction, sector, order);
	}

	@Override
	public List<SectorVO> findUsingExactTemplate(Transaction transaction,
			SectorVO sector, String order) {
		return sectorDao.findUsingExactTemplate(transaction, sector, order);
	}
	
	public void createElement(Transaction transaction, SectorVO sector) throws DaoException{
		this.sectorDao.createElement(transaction, sector);
	}
	
	public void deleteElement(Transaction transaction, SectorVO sector){
		this.sectorDao.deleteElement(transaction, sector);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public SectorDao getSectorDao() {
		return sectorDao;
	}

	public void setSectorDao(SectorDao sectorDao) {
		this.sectorDao = sectorDao;
	}
}