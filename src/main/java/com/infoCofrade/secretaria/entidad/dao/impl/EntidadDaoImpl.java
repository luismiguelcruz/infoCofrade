package com.infoCofrade.secretaria.entidad.dao.impl;

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
import com.infoCofrade.secretaria.entidad.dao.EntidadDao;
import com.infoCofrade.secretaria.entidad.vo.EntidadVO;


@Component("entidadDaoImpl")
public class EntidadDaoImpl implements EntidadDao{
	private static EntidadDao instance = new EntidadDaoImpl();
	private Session session;
	
	private EntidadDaoImpl(){}
	
	public static EntidadDao getInstance(){
		if (instance == null) {
            synchronized(EntidadDaoImpl.class) {
                if (instance == null) { 
                	instance = new EntidadDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized EntidadVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		EntidadVO entidad = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from EntidadVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				entidad = (EntidadVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return entidad;
	}
	
	public synchronized List<EntidadVO> findAll(Transaction transaction, String order){
		List<EntidadVO> list = new ArrayList<EntidadVO>();
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
					"SELECT * FROM "+schema+"ENTIDAD");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(EntidadVO.class);
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
	
	public List<EntidadVO> findUsingTemplate(Transaction transaction, EntidadVO entidad, String order){
		List<EntidadVO> list = new ArrayList<EntidadVO>();
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
			
			if(entidad == null){
				throw new Exception("DAO Exception: Entidad is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT entidad.* FROM "+schema+"ENTIDAD entidad");
			StringBuilder conditions = new StringBuilder();
			if (entidad.getId() != null) {
				conditions.append(" entidad.id = " + entidad.getId() + " AND");
			}
			if (entidad.getIdGrupo() != null) {
				conditions.append(" entidad.`idGrupo` = " + entidad.getIdGrupo() + " AND");
			}
			if(entidad.getRazonSocial() != null && !entidad.getRazonSocial().isEmpty()){
				conditions.append(" UPPER(entidad.`razonSocial`) like '%"+
						entidad.getRazonSocial().toUpperCase()+"%' AND");
			}
			if(entidad.getNombreCorto() != null && !entidad.getNombreCorto().isEmpty()){
				conditions.append(" UPPER(entidad.`nombreCorto`) like '%"+
						entidad.getNombreCorto().toUpperCase()+"%' AND");
			}
			if(entidad.getCifNif() != null && !entidad.getCifNif().isEmpty()){
				conditions.append(" UPPER(entidad.`cifNif`) like '%"+
						entidad.getCifNif().toUpperCase()+"%' AND");
			}
			if(entidad.getDomicilio() != null && !entidad.getDomicilio().isEmpty()){
				conditions.append(" UPPER(entidad.`domicilio`) like '%"+
						entidad.getDomicilio().toUpperCase()+"%' AND");
			}
			if (entidad.getIdLocalidad() != null) {
				conditions.append(" entidad.`idLocalidad` = " + entidad.getIdLocalidad() + " AND");
			}
			if(entidad.getTelefono() != null && !entidad.getTelefono().isEmpty()){
				conditions.append(" UPPER(entidad.`telefono`) like '%"+
						entidad.getTelefono().toUpperCase()+"%' AND");
			}
			if(entidad.getEmail() != null && !entidad.getEmail().isEmpty()){
				conditions.append(" UPPER(entidad.`email`) like '%"
						+entidad.getEmail().toUpperCase()+"%' AND");
			}
			if(entidad.getPersonaContacto1() != null && !entidad.getPersonaContacto1().isEmpty()){
				conditions.append(" UPPER(entidad.`personaContacto1`) like '%"+
						entidad.getPersonaContacto1().toUpperCase()+"%' AND");
			}
			if(entidad.getMovilContacto1() != null && !entidad.getMovilContacto1().isEmpty()){
				conditions.append(" UPPER(entidad.`movilContacto1`) = '"+
						entidad.getMovilContacto1().toUpperCase()+"' AND");
			}
			if(entidad.getPersonaContacto2() != null && !entidad.getPersonaContacto2().isEmpty()){
				conditions.append(" UPPER(entidad.`personaContacto2`) like '%"+
						entidad.getPersonaContacto2().toUpperCase()+"%' AND");
			}
			if(entidad.getMovilContacto2() != null && !entidad.getMovilContacto2().isEmpty()){
				conditions.append(" UPPER(entidad.`movilContacto2`) = '"+
						entidad.getMovilContacto2().toUpperCase()+"' AND");
			}
			if(entidad.getObservaciones() != null && !entidad.getObservaciones().isEmpty()){
				conditions.append(" UPPER(entidad.`observaciones`) like '%"+
						entidad.getObservaciones().toUpperCase()+"%' AND");
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
			query.addEntity("entidad", EntidadVO.class);
			
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
	
	public List<EntidadVO> findUsingExactTemplate(Transaction transaction, EntidadVO entidad, String order){
		List<EntidadVO> list = new ArrayList<EntidadVO>();
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
			
			if(entidad == null){
				throw new Exception("DAO Exception: Entidad is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT entidad.* FROM "+schema+"ENTIDAD entidad");
			StringBuilder conditions = new StringBuilder();
			if (entidad.getId() != null) {
				conditions.append(" entidad.id = " + entidad.getId() + " AND");
			}
			if (entidad.getIdGrupo() != null) {
				conditions.append(" entidad.`idGrupo` = " + entidad.getIdGrupo() + " AND");
			}
			if(entidad.getRazonSocial() != null && !entidad.getRazonSocial().isEmpty()){
				conditions.append(" UPPER(entidad.`razonSocial`) = '"+
						entidad.getRazonSocial().toUpperCase()+"' AND");
			}
			if(entidad.getNombreCorto() != null && !entidad.getNombreCorto().isEmpty()){
				conditions.append(" UPPER(entidad.`nombreCorto`) = '"+
						entidad.getNombreCorto().toUpperCase()+"' AND");
			}
			if(entidad.getCifNif() != null && !entidad.getCifNif().isEmpty()){
				conditions.append(" UPPER(entidad.`cifNif`) = '"+entidad.getCifNif().toUpperCase()+"' AND");
			}
			if(entidad.getDomicilio() != null && !entidad.getDomicilio().isEmpty()){
				conditions.append(" UPPER(entidad.`domicilio`) = '"+
						entidad.getDomicilio().toUpperCase()+"' AND");
			}
			if (entidad.getIdLocalidad() != null) {
				conditions.append(" entidad.`idLocalidad` = " + entidad.getIdLocalidad() + " AND");
			}
			if(entidad.getTelefono() != null && !entidad.getTelefono().isEmpty()){
				conditions.append(" UPPER(entidad.`telefono`) = '"+entidad.getTelefono().toUpperCase()+"' AND");
			}
			if(entidad.getEmail() != null && !entidad.getEmail().isEmpty()){
				conditions.append(" UPPER(entidad.`email`) = '"+entidad.getEmail().toUpperCase()+"' AND");
			}
			if(entidad.getPersonaContacto1() != null && !entidad.getPersonaContacto1().isEmpty()){
				conditions.append(" UPPER(entidad.`personaContacto1`) = '"+
						entidad.getPersonaContacto1().toUpperCase()+"' AND");
			}
			if(entidad.getMovilContacto1() != null && !entidad.getMovilContacto1().isEmpty()){
				conditions.append(" UPPER(entidad.`movilContacto1`) = '"+
						entidad.getMovilContacto1().toUpperCase()+"' AND");
			}
			if(entidad.getPersonaContacto2() != null && !entidad.getPersonaContacto2().isEmpty()){
				conditions.append(" UPPER(entidad.`personaContacto2`) = '"+
						entidad.getPersonaContacto2().toUpperCase()+"' AND");
			}
			if(entidad.getMovilContacto2() != null && !entidad.getMovilContacto2().isEmpty()){
				conditions.append(" UPPER(entidad.`movilContacto2`) = '"+
						entidad.getMovilContacto2().toUpperCase()+"' AND");
			}
			if(entidad.getObservaciones() != null && !entidad.getObservaciones().isEmpty()){
				conditions.append(" UPPER(entidad.`observaciones`) = '"+
						entidad.getObservaciones().toUpperCase()+"' AND");
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
			query.addEntity("entidad", EntidadVO.class);
			
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
	public synchronized void createElement(Transaction transaction, EntidadVO entidad) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (entidad == null) {
				throw new DaoException("Element is null");
			}
			
			if(entidad.getId() == null){
				session.save(entidad);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(entidad);
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
	public synchronized void deleteElement(Transaction transaction, EntidadVO entidad) {
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
			
			if (entidad == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"ENTIDAD ");
			StringBuilder conditions = new StringBuilder();
			if (entidad.getId() != null) {
				conditions.append(" id = " + entidad.getId() + " AND");
			}
			if (entidad.getIdGrupo() != null) {
				conditions.append(" `idGrupo` = " + entidad.getIdGrupo() + " AND");
			}
			if(entidad.getRazonSocial() != null && !entidad.getRazonSocial().isEmpty()){
				conditions.append(" UPPER(`razonSocial`) = '"+
						entidad.getRazonSocial().toUpperCase()+"' AND");
			}
			if(entidad.getNombreCorto() != null && !entidad.getNombreCorto().isEmpty()){
				conditions.append(" UPPER(`nombreCorto`) = '"+
						entidad.getNombreCorto().toUpperCase()+"' AND");
			}
			if(entidad.getCifNif() != null && !entidad.getCifNif().isEmpty()){
				conditions.append(" UPPER(`cifNif`) = '"+entidad.getCifNif().toUpperCase()+"' AND");
			}
			if(entidad.getDomicilio() != null && !entidad.getDomicilio().isEmpty()){
				conditions.append(" UPPER(`domicilio`) = '"+
						entidad.getDomicilio().toUpperCase()+"' AND");
			}
			if (entidad.getIdLocalidad() != null) {
				conditions.append(" `idLocalidad` = " + entidad.getIdLocalidad() + " AND");
			}
			if(entidad.getTelefono() != null && !entidad.getTelefono().isEmpty()){
				conditions.append(" UPPER(`telefono`) = '"+entidad.getTelefono().toUpperCase()+"' AND");
			}
			if(entidad.getEmail() != null && !entidad.getEmail().isEmpty()){
				conditions.append(" UPPER(`email`) = '"+entidad.getEmail().toUpperCase()+"' AND");
			}
			if(entidad.getPersonaContacto1() != null && !entidad.getPersonaContacto1().isEmpty()){
				conditions.append(" UPPER(entidad.`personaContacto1`) = '"+
						entidad.getPersonaContacto1().toUpperCase()+"' AND");
			}
			if(entidad.getMovilContacto1() != null && !entidad.getMovilContacto1().isEmpty()){
				conditions.append(" UPPER(entidad.`movilContacto1`) = '"+
						entidad.getMovilContacto1().toUpperCase()+"' AND");
			}
			if(entidad.getPersonaContacto2() != null && !entidad.getPersonaContacto2().isEmpty()){
				conditions.append(" UPPER(entidad.`personaContacto2`) = '"+
						entidad.getPersonaContacto2().toUpperCase()+"' AND");
			}
			if(entidad.getMovilContacto2() != null && !entidad.getMovilContacto2().isEmpty()){
				conditions.append(" UPPER(entidad.`movilContacto2`) = '"+
						entidad.getMovilContacto2().toUpperCase()+"' AND");
			}
			if(entidad.getObservaciones() != null && !entidad.getObservaciones().isEmpty()){
				conditions.append(" UPPER(entidad.`observaciones`) = '"+
						entidad.getObservaciones().toUpperCase()+"' AND");
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