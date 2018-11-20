package com.infoCofrade.web.noticia.bo.impl;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.web.noticia.bo.NoticiaBO;
import com.infoCofrade.web.noticia.dao.NoticiaDao;
import com.infoCofrade.web.noticia.dao.impl.NoticiaDaoImpl;
import com.infoCofrade.web.noticia.vo.NoticiaVO;


public class NoticiaBOImpl implements NoticiaBO{
	private NoticiaDao noticiaDao = NoticiaDaoImpl.getInstance();
	
	public NoticiaVO findByPrimaryKey(Transaction transaction, Long id){
		return noticiaDao.findByPrimaryKey(transaction, id);
	}
	
	public List<NoticiaVO> findAll(Transaction transaction, String order){
		return noticiaDao.findAll(transaction, order);
	}
	
	public List<NoticiaVO> findUsingTemplate(Transaction transaction,
			NoticiaVO noticia, String order) {
		return noticiaDao.findUsingTemplate(transaction, noticia, order);
	}

	@Override
	public List<NoticiaVO> findUsingExactTemplate(Transaction transaction,
			NoticiaVO noticia, String order) {
		return noticiaDao.findUsingExactTemplate(transaction, noticia, order);
	}
	
	public void createElement(Transaction transaction, NoticiaVO noticia) throws DaoException{
		this.noticiaDao.createElement(transaction, noticia);
	}
	
	public void deleteElement(Transaction transaction, NoticiaVO noticia){
		this.noticiaDao.deleteElement(transaction, noticia);
	}
	
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	
	public NoticiaDao getNoticiaDao() {
		return noticiaDao;
	}

	public void setNoticiaDao(NoticiaDao noticiaDao) {
		this.noticiaDao = noticiaDao;
	}
}