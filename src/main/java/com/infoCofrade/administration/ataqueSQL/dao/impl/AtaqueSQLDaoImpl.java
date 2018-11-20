package com.infoCofrade.administration.ataqueSQL.dao.impl;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Component;

import com.infoCofrade.administration.ataqueSQL.dao.AtaqueSQLDao;
import com.infoCofrade.administration.ataqueSQL.vo.AtaqueSQLVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.hibernate.ConfHibernate;


@Component("ataqueSQLDaoImpl")
public class AtaqueSQLDaoImpl implements AtaqueSQLDao{
	private static AtaqueSQLDao instance = new AtaqueSQLDaoImpl();
	private Session session;
	
	private AtaqueSQLDaoImpl(){}
	
	public static AtaqueSQLDao getInstance(){
		if (instance == null) {
            synchronized(AtaqueSQLDaoImpl.class) {
                if (instance == null) { 
                	instance = new AtaqueSQLDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	
	/**
	 * Create an element into the Database
	 */
	public synchronized void createElement(StringBuilder sentencia) throws DaoException{
		String hibernateConfFileName = AbstractBean.hibernateConfFileName;
		if (AbstractBean.hibernateConfFileName != null) {
			AbstractBean.hibernateConfFileName = null;
			ConfHibernate.changeSessionFactory();
		}
		
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = session.beginTransaction();

		try {
			if (sentencia == null) {
				throw new DaoException("Element is null");
			}
			
			AtaqueSQLVO ataqueSQL = new AtaqueSQLVO();
			
			// Construimos la ip desde donde se ha lanzado el ataque
			HttpServletRequest request = 
					(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if(request != null){
				ataqueSQL.setIp(request.getRemoteAddr());
			}
			
			// Añadimos la hora a la que se ha producido el ataque
			ataqueSQL.setHora(new Date());
			
			// Sustituimos la cadena de SQL Injection para que la base de datos no la ejecute
			ataqueSQL.setSentencia(sentencia.toString());
			ataqueSQL.getSentencia().replaceAll("'", "\'");
			ataqueSQL.getSentencia().replaceAll("\"", "\\\"");
			
			session.save(ataqueSQL);
			
			currentTransaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			currentTransaction.rollback();
			throw new DaoException(ex.getMessage());
		}
		
		// Volvemos a establecer la base de datos que se estuviera usando
		if (AbstractBean.hibernateConfFileName != null) {
			AbstractBean.hibernateConfFileName = hibernateConfFileName;
			ConfHibernate.changeSessionFactory();
		}
	}

	/**
	 * Delete an element from the Database
	 */
	public synchronized void deleteElement(Transaction transaction, AtaqueSQLVO ataqueSQL) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (ataqueSQL == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"ATAQUESQL ");
			StringBuilder conditions = new StringBuilder();
			if (ataqueSQL.getId() != null) {
				conditions.append(" id = " + ataqueSQL.getId() + " AND");
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