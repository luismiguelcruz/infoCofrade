package com.infoCofrade.web.noticia.bo;

import java.util.List;

import org.hibernate.Transaction;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.web.noticia.vo.NoticiaVO;


public interface NoticiaBO{
	public NoticiaVO findByPrimaryKey(Transaction transaction, Long id);
	
	public List<NoticiaVO> findAll(Transaction transaction, String order);
	
	public List<NoticiaVO> findUsingTemplate(Transaction transaction, NoticiaVO Noticia, String order);
	
	public List<NoticiaVO> findUsingExactTemplate(Transaction transaction, NoticiaVO Noticia, String order);
	
	public void createElement(Transaction transaction, NoticiaVO Noticia) throws DaoException;
	
	public void deleteElement(Transaction transaction, NoticiaVO Noticia);
}