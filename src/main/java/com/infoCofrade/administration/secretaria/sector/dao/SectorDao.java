package com.infoCofrade.administration.secretaria.sector.dao;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.sector.vo.SectorVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface SectorDao {
	public SectorVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<SectorVO> findAll(Transaction transaction, String order);
	
	public List<SectorVO> findUsingTemplate(Transaction transaction, SectorVO sector, String order);
	
	public List<SectorVO> findUsingExactTemplate(Transaction transaction, SectorVO sector, String order);
	
	public void createElement(Transaction transaction, SectorVO sector) throws DaoException;
	
	public void deleteElement(Transaction transaction, SectorVO sector);
}