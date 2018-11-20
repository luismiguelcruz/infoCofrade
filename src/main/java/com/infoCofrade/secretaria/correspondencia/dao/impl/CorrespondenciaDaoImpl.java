package com.infoCofrade.secretaria.correspondencia.dao.impl;

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
import com.infoCofrade.secretaria.correspondencia.dao.CorrespondenciaDao;
import com.infoCofrade.secretaria.correspondencia.vo.CorrespondenciaVO;


@Component("correspondenciaDaoImpl")
public class CorrespondenciaDaoImpl implements CorrespondenciaDao{
	private static CorrespondenciaDao instance = new CorrespondenciaDaoImpl();
	private Session session;
	
	private CorrespondenciaDaoImpl(){}
	
	public static CorrespondenciaDao getInstance(){
		if (instance == null) {
            synchronized(CorrespondenciaDaoImpl.class) {
                if (instance == null) { 
                	instance = new CorrespondenciaDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized CorrespondenciaVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		CorrespondenciaVO correspondencia = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from CorrespondenciaVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				correspondencia = (CorrespondenciaVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return correspondencia;
	}
	
	public synchronized List<CorrespondenciaVO> findAll(Transaction transaction, String order){
		List<CorrespondenciaVO> list = new ArrayList<CorrespondenciaVO>();
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
					"SELECT * FROM "+schema+"CORRESPONDENCIA");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(CorrespondenciaVO.class);
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
	
	public List<CorrespondenciaVO> findUsingTemplate(Transaction transaction, CorrespondenciaVO correspondencia, String order){
		List<CorrespondenciaVO> list = new ArrayList<CorrespondenciaVO>();
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
			
			if(correspondencia == null){
				throw new Exception("DAO Exception: Correspondencia is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT correspondencia.* FROM "+schema+"CORRESPONDENCIA correspondencia");
			StringBuilder conditions = new StringBuilder();
			if (correspondencia.getId() != null) {
				conditions.append(" correspondencia.id = " + correspondencia.getId() + " AND");
			}
			if(correspondencia.getRemitente() != null && !correspondencia.getRemitente().isEmpty()){
				conditions.append(" UPPER(correspondencia.`remitente`) like '%"+
						correspondencia.getRemitente().toUpperCase()+"%' AND");
			}
			if(correspondencia.getReceptor() != null && !correspondencia.getReceptor().isEmpty()){
				conditions.append(" UPPER(correspondencia.`receptor`) like '%"+
						correspondencia.getReceptor().toUpperCase()+"%' AND");
			}
			if(correspondencia.getFechaEntrada() != null){
				conditions.append(" correspondencia.`fechaEntrada` = '"+
						correspondencia.getFechaEntrada()+"' AND");
			}
			if(correspondencia.getFechaSalida() != null){
				conditions.append(" correspondencia.`fechaSalida` = '"+
						correspondencia.getFechaSalida()+"' AND");
			}
			if(correspondencia.getAsunto() != null && !correspondencia.getAsunto().isEmpty()){
				conditions.append(" UPPER(correspondencia.`asunto`) like '%"+
						correspondencia.getAsunto().toUpperCase()+"%' AND");
			}
			if(correspondencia.getIdGrupo() != null){
				conditions.append(" correspondencia.`idGrupo` = '"+correspondencia.getIdGrupo()+"' AND");
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
			query.addEntity("correspondencia", CorrespondenciaVO.class);
			
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
	
	public List<CorrespondenciaVO> findUsingExactTemplate(Transaction transaction, CorrespondenciaVO correspondencia, String order){
		List<CorrespondenciaVO> list = new ArrayList<CorrespondenciaVO>();
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
			
			if(correspondencia == null){
				throw new Exception("DAO Exception: Correspondencia is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT correspondencia.* FROM "+schema+"CORRESPONDENCIA correspondencia");
			StringBuilder conditions = new StringBuilder();
			if (correspondencia.getId() != null) {
				conditions.append(" correspondencia.id = " + correspondencia.getId() + " AND");
			}
			if(correspondencia.getRemitente() != null && !correspondencia.getRemitente().isEmpty()){
				conditions.append(" UPPER(correspondencia.`remitente`) = '"+
						correspondencia.getRemitente().toUpperCase()+"' AND");
			}
			if(correspondencia.getReceptor() != null && !correspondencia.getReceptor().isEmpty()){
				conditions.append(" UPPER(correspondencia.`receptor`) = '"+
						correspondencia.getReceptor().toUpperCase()+"' AND");
			}
			if(correspondencia.getFechaEntrada() != null){
				conditions.append(" correspondencia.`fechaEntrada` = '"+
						correspondencia.getFechaEntrada()+"' AND");
			}
			if(correspondencia.getFechaSalida() != null){
				conditions.append(" correspondencia.`fechaSalida` = '"+
						correspondencia.getFechaSalida()+"' AND");
			}
			if(correspondencia.getAsunto() != null && !correspondencia.getAsunto().isEmpty()){
				conditions.append(" UPPER(correspondencia.`asunto`) = '"+
						correspondencia.getAsunto().toUpperCase()+"' AND");
			}
			if(correspondencia.getIdGrupo() != null){
				conditions.append(" correspondencia.`idGrupo` = '"+correspondencia.getIdGrupo()+"' AND");
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
			query.addEntity("correspondencia", CorrespondenciaVO.class);
			
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
	public synchronized void createElement(Transaction transaction, CorrespondenciaVO correspondencia) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (correspondencia == null) {
				throw new DaoException("Element is null");
			}
			
			if(correspondencia.getId() == null){
				session.save(correspondencia);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(correspondencia);
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
	public synchronized void deleteElement(Transaction transaction, CorrespondenciaVO correspondencia) {
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
			
			if (correspondencia == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"CORRESPONDENCIA ");
			StringBuilder conditions = new StringBuilder();
			if (correspondencia.getId() != null) {
				conditions.append(" id = " + correspondencia.getId() + " AND");
			}
			if(correspondencia.getRemitente() != null && !correspondencia.getRemitente().isEmpty()){
				conditions.append(" UPPER(`remitente`) = '"+
						correspondencia.getRemitente().toUpperCase()+"' AND");
			}
			if(correspondencia.getReceptor() != null && !correspondencia.getReceptor().isEmpty()){
				conditions.append(" UPPER(`receptor`) = '"+
						correspondencia.getReceptor().toUpperCase()+"' AND");
			}
			if(correspondencia.getFechaEntrada() != null){
				conditions.append(" `fechaEntrada` = '"+
						correspondencia.getFechaEntrada()+"' AND");
			}
			if(correspondencia.getFechaSalida() != null){
				conditions.append(" `fechaSalida` = '"+
						correspondencia.getFechaSalida()+"' AND");
			}
			if(correspondencia.getAsunto() != null && !correspondencia.getAsunto().isEmpty()){
				conditions.append(" UPPER(`asunto`) = '"+
						correspondencia.getAsunto().toUpperCase()+"' AND");
			}
			if(correspondencia.getIdGrupo() != null){
				conditions.append(" `idGrupo` = '"+correspondencia.getIdGrupo()+"' AND");
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