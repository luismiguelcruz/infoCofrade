package com.infoCofrade.administration.secretaria.sector.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.administration.secretaria.sector.vo.SectorVO;
import com.infoCofrade.common.exceptions.DaoException;


public interface SectorBO{
	public SectorVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<SectorVO> findAll(Transaction transaction, String order);
	
	public List<SectorVO> findUsingTemplate(Transaction transaction, SectorVO Sector, String order);
	
	public List<SectorVO> findUsingExactTemplate(Transaction transaction, SectorVO Sector, String order);
	
	public void createElement(Transaction transaction, SectorVO Sector) throws DaoException;
	
	public void deleteElement(Transaction transaction, SectorVO Sector);
}