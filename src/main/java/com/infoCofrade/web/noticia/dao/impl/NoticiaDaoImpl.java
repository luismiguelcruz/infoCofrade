package com.infoCofrade.web.noticia.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Component;

import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.hibernate.ConfHibernate;
import com.infoCofrade.web.noticia.dao.NoticiaDao;
import com.infoCofrade.web.noticia.vo.NoticiaVO;


@Component("noticiaDaoImpl")
public class NoticiaDaoImpl implements NoticiaDao{
	private static NoticiaDao instance = new NoticiaDaoImpl();
	private Session session;
	
	private NoticiaDaoImpl(){}
	
	public static NoticiaDao getInstance(){
		if (instance == null) {
            synchronized(NoticiaDaoImpl.class) {
                if (instance == null) { 
                	instance = new NoticiaDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized NoticiaVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		NoticiaVO noticia = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from NoticiaVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				noticia = (NoticiaVO)session.createQuery(queryString.toString()).uniqueResult();
			}
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex){
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}
		
		return noticia;
	}
	
	public synchronized List<NoticiaVO> findAll(Transaction transaction, String order){
		List<NoticiaVO> list = new ArrayList<NoticiaVO>();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"SELECT * FROM "+schema+"NOTICIA");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(NoticiaVO.class);
			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}

		return list;
	}
	
	public List<NoticiaVO> findUsingTemplate(Transaction transaction, NoticiaVO noticia, String order){
		List<NoticiaVO> list = new ArrayList<NoticiaVO>();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(noticia == null){
				throw new Exception("DAO Exception: Noticia is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT noticia.* FROM "+schema+"NOTICIA noticia");
			StringBuilder conditions = new StringBuilder();
			if (noticia.getId() != null) {
				conditions.append(" noticia.id = " + noticia.getId() + " AND");
			}
			if (noticia.getTitulo() != null && !noticia.getTitulo().isEmpty()) {
				conditions.append(" noticia.`titulo` like '%" + noticia.getTitulo() + "%' AND");
			}
			if (noticia.getContenido() != null && !noticia.getContenido().isEmpty()) {
				conditions.append(" noticia.`contenido` like '%" + noticia.getContenido() + "%' AND");
			}
			if (noticia.getFecha() != null) {
				conditions.append(" noticia.`fecha` = " + noticia.getFecha() + " AND");
			}
			if (noticia.getImagenNoticia() != null) {
				conditions.append(" noticia.`imagenNoticia` = " + noticia.getImagenNoticia() + " AND");
			}
			if (noticia.getStyleClass() != null && !noticia.getStyleClass().isEmpty()) {
				conditions.append(" noticia.`contenido` = '" + noticia.getStyleClass() + "' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("noticia", NoticiaVO.class);
			
			list = query.list();
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se han encontrado elementos", ex.getMessage()));
			}
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	public List<NoticiaVO> findUsingExactTemplate(Transaction transaction, NoticiaVO noticia, String order){
		List<NoticiaVO> list = new ArrayList<NoticiaVO>();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if(noticia == null){
				throw new Exception("DAO Exception: Noticia is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT noticia.* FROM "+schema+"NOTICIA noticia");
			StringBuilder conditions = new StringBuilder();
			if (noticia.getId() != null) {
				conditions.append(" noticia.id = " + noticia.getId() + " AND");
			}
			if (noticia.getTitulo() != null && !noticia.getTitulo().isEmpty()) {
				conditions.append(" noticia.`titulo` = '" + noticia.getTitulo() + "' AND");
			}
			if (noticia.getContenido() != null && !noticia.getContenido().isEmpty()) {
				conditions.append(" noticia.`contenido` = '" + noticia.getContenido() + "' AND");
			}
			if (noticia.getFecha() != null) {
				conditions.append(" noticia.`fecha` = " + noticia.getFecha() + " AND");
			}
			if (noticia.getImagenNoticia() != null) {
				conditions.append(" noticia.`imagenNoticia` = " + noticia.getImagenNoticia() + " AND");
			}
			if (noticia.getStyleClass() != null && !noticia.getStyleClass().isEmpty()) {
				conditions.append(" noticia.`contenido` = '" + noticia.getStyleClass() + "' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("noticia", NoticiaVO.class);
			
			list = query.list();
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se encontraron elementos", ex.getMessage()));
			}
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	
	/**
	 * Create an element into the Database
	 */
	public synchronized void createElement(Transaction transaction, NoticiaVO noticia) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (noticia == null) {
				throw new DaoException("Element is null");
			}
			
			if(noticia.getId() == null){
				session.save(noticia);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(noticia);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento actualizado correctamente"));
			}
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cannot create element", ex.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}
	}

	/**
	 * Delete an element from the Database
	 */
	public synchronized void deleteElement(Transaction transaction, NoticiaVO noticia) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = 
				((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings()
					.getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (noticia == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"NOTICIA ");
			StringBuilder conditions = new StringBuilder();
			if (noticia.getId() != null) {
				conditions.append(" id = " + noticia.getId() + " AND");
			}
			if (noticia.getTitulo() != null && !noticia.getTitulo().isEmpty()) {
				conditions.append(" `titulo` = '" + noticia.getTitulo() + "' AND");
			}
			if (noticia.getContenido() != null && !noticia.getContenido().isEmpty()) {
				conditions.append(" `contenido` = '" + noticia.getContenido() + "' AND");
			}
			if (noticia.getFecha() != null) {
				conditions.append(" `fecha` = " + noticia.getFecha() + " AND");
			}
			if (noticia.getImagenNoticia() != null) {
				conditions.append(" `imagenNoticia` = " + noticia.getImagenNoticia() + " AND");
			}
			if (noticia.getStyleClass() != null && !noticia.getStyleClass().isEmpty()) {
				conditions.append(" `contenido` = '" + noticia.getStyleClass() + "' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.executeUpdate();
			
			if (transaction == null) {
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento borrado correctamente"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error borrando el elemento", ""));
			}
		}
	}
}