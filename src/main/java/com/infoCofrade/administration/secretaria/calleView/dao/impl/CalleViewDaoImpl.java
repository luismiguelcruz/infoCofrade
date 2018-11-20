package com.infoCofrade.administration.secretaria.calleView.dao.impl;

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

import com.infoCofrade.administration.ataqueSQL.dao.impl.AtaqueSQLDaoImpl;
import com.infoCofrade.administration.secretaria.calleView.dao.CalleViewDao;
import com.infoCofrade.administration.secretaria.calleView.vo.CalleViewVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;


@Component("calleViewDaoImpl")
public class CalleViewDaoImpl implements CalleViewDao{
	private static CalleViewDao instance = new CalleViewDaoImpl();
	private Session session;
	
	private CalleViewDaoImpl(){}
	
	public static CalleViewDao getInstance(){
		if (instance == null) {
            synchronized(CalleViewDaoImpl.class) {
                if (instance == null) { 
                	instance = new CalleViewDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized CalleViewVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		CalleViewVO calleView = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from CalleViewVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				// Probamos si el usuario esta intentando hacer sql injection
				if(AbstractBean.preventSqlInjection(queryString)){
					currentTransaction.rollback();
					AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
							.getCurrentInstance().getELContext().getELResolver()
							.getValue(FacesContext.getCurrentInstance().getELContext(),
									null, "ataqueSQLDaoImpl");
					
					ataqueSQLDao.createElement(queryString);
					
					throw new SqlInjectionException("Wrong statements for the query!!");
				}
				calleView = (CalleViewVO)session.createQuery(queryString.toString()).uniqueResult();
			}
			
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex){
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}
		
		return calleView;
	}
	
	public synchronized List<CalleViewVO> findAll(Transaction transaction, String order){
		List<CalleViewVO> list = new ArrayList<CalleViewVO>();
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
					"SELECT * FROM "+schema+"CALLE_VIEW");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			// Probamos si el usuario esta intentando hacer sql injection
			if(AbstractBean.preventSqlInjection(queryString)){
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance().getELContext().getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "ataqueSQLDaoImpl");
				
				ataqueSQLDao.createElement(queryString);
				
				throw new SqlInjectionException("Wrong statements for the query!!");
			}
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(CalleViewVO.class);
			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (SqlInjectionException ex) {
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Database error: ", ex.getMessage()));
			}
		}

		return list;
	}
	
	public List<CalleViewVO> findUsingTemplate(Transaction transaction, CalleViewVO calleView, String order){
		List<CalleViewVO> list = new ArrayList<CalleViewVO>();
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
			
			if(calleView == null){
				throw new Exception("DAO Exception: CalleView is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT calleView.* FROM "+schema+"CALLE_VIEW calleView");
			StringBuilder conditions = new StringBuilder();
			if (calleView.getId() != null) {
				conditions.append(" calleView.id = " + calleView.getId() + " AND");
			}
			if (calleView.getIdTipoVia() != null) {
				conditions.append(" calleView.`idTipoVia` = " + calleView.getIdTipoVia() + " AND");
			}
			if(calleView.getTipoVia() != null && !calleView.getTipoVia().isEmpty()){
				conditions.append(" UPPER(calleView.`tipoVia`) like '%"+calleView.getTipoVia().toUpperCase()+"%' AND");
			}
			if(calleView.getNombreVia() != null && !calleView.getNombreVia().isEmpty()){
				conditions.append(" UPPER(calleView.`nombreVia`) like '%"+calleView.getNombreVia().toUpperCase()+"%' AND");
			}
			if(calleView.getNumKmMinimo() != null && !calleView.getNumKmMinimo().isEmpty()){
				conditions.append(" UPPER(calleView.`numKmMinimo`) like '%"+calleView.getNumKmMinimo().toUpperCase()+"%' AND");
			}
			if(calleView.getNumKmMaximo() != null && !calleView.getNumKmMaximo().isEmpty()){
				conditions.append(" UPPER(calleView.`numKmMaximo`) like '%"+calleView.getNumKmMaximo().toUpperCase()+"%' AND");
			}
			if (calleView.getIdLocalidad() != null) {
				conditions.append(" calleView.`idLocalidad` = " + calleView.getIdLocalidad() + " AND");
			}
			if (calleView.getIdSector() != null) {
				conditions.append(" calleView.`idSector` = " + calleView.getIdSector() + " AND");
			}
			if(calleView.getEncargado() != null && !calleView.getEncargado().isEmpty()){
				conditions.append(" UPPER(calleView.`encargado`) like '%"+calleView.getEncargado().toUpperCase()+"%' AND");
			}
			if(calleView.getLocalidad() != null && !calleView.getLocalidad().isEmpty()){
				conditions.append(" UPPER(calleView.`localidad`) like '%"+
						calleView.getLocalidad().toUpperCase()+"%' AND");
			}
			if(calleView.getCodigoPostal() != null){
				conditions.append(" calleView.`codigoPostal` = '"+calleView.getCodigoPostal()+"' AND");
			}
			if(calleView.getLatitud() != null){
				conditions.append(" calleView.`latitud` = '"+calleView.getLatitud()+"' AND");
			}
			if(calleView.getLongitud() != null){
				conditions.append(" calleView.`longitud` = '"+calleView.getLongitud()+"' AND");
			}
			if (calleView.getIdProvincia() != null) {
				conditions.append(" calleView.`idProvincia` = " + calleView.getIdProvincia() + " AND");
			}
			if (calleView.getProvincia() != null && !calleView.getProvincia().isEmpty()){
				conditions.append(" UPPER(calleView.`provincia`) like '%"+
						calleView.getProvincia().toUpperCase()+"%' AND");
			}
			if (calleView.getIdPais() != null){
				conditions.append(" UPPER(calleView.`idPais`) = '"+ calleView.getIdPais()+"' AND");
			}
			if (calleView.getPais() != null && !calleView.getPais().isEmpty()){
				conditions.append(" UPPER(calleView.`pais`) like '%"+
						calleView.getPais().toUpperCase()+"%' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			// Probamos si el usuario esta intentando hacer sql injection
			if(AbstractBean.preventSqlInjection(queryString)){
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance().getELContext().getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "ataqueSQLDaoImpl");
				
				ataqueSQLDao.createElement(queryString);
				
				throw new SqlInjectionException("Wrong statements for the query!!");
			}
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("calleView", CalleViewVO.class);
			
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
		} catch (SqlInjectionException ex) {
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
	
	public List<CalleViewVO> findUsingExactTemplate(Transaction transaction, CalleViewVO calleView, String order){
		List<CalleViewVO> list = new ArrayList<CalleViewVO>();
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
			
			if(calleView == null){
				throw new Exception("DAO Exception: CalleView is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT calleView.* FROM "+schema+"CALLE_VIEW calleView");
			StringBuilder conditions = new StringBuilder();
			if(calleView.getId() != null) {
				conditions.append(" calleView.id = " + calleView.getId() + " AND");
			}
			if(calleView.getIdTipoVia() != null) {
				conditions.append(" calleView.`idTipoVia` = " + calleView.getIdTipoVia() + " AND");
			}
			if(calleView.getTipoVia() != null && !calleView.getTipoVia().isEmpty()){
				conditions.append(" UPPER(calleView.`tipoVia`) = '"+calleView.getTipoVia().toUpperCase()+"' AND");
			}
			if(calleView.getNombreVia() != null && !calleView.getNombreVia().isEmpty()){
				conditions.append(" UPPER(calleView.`nombreVia`) = '"+calleView.getNombreVia().toUpperCase()+"' AND");
			}
			if(calleView.getNumKmMinimo() != null && !calleView.getNumKmMinimo().isEmpty()){
				conditions.append(" UPPER(calleView.`numKmMinimo`) = '"+calleView.getNumKmMinimo().toUpperCase()+"' AND");
			}
			if(calleView.getNumKmMaximo() != null && !calleView.getNumKmMaximo().isEmpty()){
				conditions.append(" UPPER(calleView.`numKmMaximo`) = '"+calleView.getNumKmMaximo().toUpperCase()+"' AND");
			}
			if (calleView.getIdLocalidad() != null) {
				conditions.append(" calleView.`idLocalidad` = " + calleView.getIdLocalidad() + " AND");
			}
			if (calleView.getIdSector() != null) {
				conditions.append(" calleView.`idSector` = " + calleView.getIdSector() + " AND");
			}
			if(calleView.getEncargado() != null && !calleView.getEncargado().isEmpty()){
				conditions.append(" UPPER(calleView.`encargado`) = '"+calleView.getEncargado().toUpperCase()+"' AND");
			}
			if(calleView.getLocalidad() != null && !calleView.getLocalidad().isEmpty()){
				conditions.append(" UPPER(calleView.`localidad`) = '"+
						calleView.getLocalidad().toUpperCase()+"' AND");
			}
			if(calleView.getCodigoPostal() != null){
				conditions.append(" calleView.`codigoPostal` = '"+calleView.getCodigoPostal()+"' AND");
			}
			if(calleView.getLatitud() != null){
				conditions.append(" calleView.`latitud` = '"+calleView.getLatitud()+"' AND");
			}
			if(calleView.getLongitud() != null){
				conditions.append(" calleView.`longitud` = '"+calleView.getLongitud()+"' AND");
			}
			if (calleView.getIdProvincia() != null) {
				conditions.append(" calleView.`idProvincia` = " + calleView.getIdProvincia() + " AND");
			}
			if (calleView.getProvincia() != null && !calleView.getProvincia().isEmpty()){
				conditions.append(" UPPER(calleView.`provincia`) = '"+
						calleView.getProvincia().toUpperCase()+"' AND");
			}
			if (calleView.getIdPais() != null){
				conditions.append(" UPPER(calleView.`idPais`) = '"+ calleView.getIdPais()+"' AND");
			}
			if (calleView.getPais() != null && !calleView.getPais().isEmpty()){
				conditions.append(" UPPER(calleView.`pais`) = '"+
						calleView.getPais().toUpperCase()+"' AND");
			}
			
			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}
			
			queryString.append(conditions);
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			// Probamos si el usuario esta intentando hacer sql injection
			if(AbstractBean.preventSqlInjection(queryString)){
				currentTransaction.rollback();
				AtaqueSQLDaoImpl ataqueSQLDao = (AtaqueSQLDaoImpl) FacesContext
						.getCurrentInstance().getELContext().getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "ataqueSQLDaoImpl");
				
				ataqueSQLDao.createElement(queryString);
				
				throw new SqlInjectionException("Wrong statements for the query!!");
			}
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("calleView", CalleViewVO.class);
			
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
		} catch (SqlInjectionException ex) {
		} catch (Exception ex){
			ex.printStackTrace();
			
			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Elements using template error", ex.getMessage()));
			}
		}
		
		return list;
	}
}