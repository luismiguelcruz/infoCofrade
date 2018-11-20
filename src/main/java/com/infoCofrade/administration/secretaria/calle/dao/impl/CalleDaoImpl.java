package com.infoCofrade.administration.secretaria.calle.dao.impl;

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
import com.infoCofrade.administration.secretaria.calle.dao.CalleDao;
import com.infoCofrade.administration.secretaria.calle.vo.CalleVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.exceptions.SqlInjectionException;
import com.infoCofrade.common.hibernate.ConfHibernate;
import com.infoCofrade.secretaria.hermano.dao.impl.HermanoDaoImpl;
import com.infoCofrade.secretaria.hermano.vo.HermanoVO;


@Component("calleDaoImpl")
public class CalleDaoImpl implements CalleDao{
	private static CalleDao instance = new CalleDaoImpl();
	private Session session;
	
	private CalleDaoImpl(){}
	
	public static CalleDao getInstance(){
		if (instance == null) {
            synchronized(CalleDaoImpl.class) {
                if (instance == null) { 
                	instance = new CalleDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized CalleVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		CalleVO calle = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from CalleVO");
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
				calle = (CalleVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return calle;
	}
	
	public synchronized List<CalleVO> findAll(Transaction transaction, String order){
		List<CalleVO> list = new ArrayList<CalleVO>();
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
					"SELECT * FROM "+schema+"CALLE");
			
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
			query.addEntity(CalleVO.class);
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
	
	public List<CalleVO> findUsingTemplate(Transaction transaction, CalleVO calle, String order){
		List<CalleVO> list = new ArrayList<CalleVO>();
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
			
			if(calle == null){
				throw new Exception("DAO Exception: Calle is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT calle.* FROM "+schema+"CALLE calle");
			StringBuilder conditions = new StringBuilder();
			if (calle.getId() != null) {
				conditions.append(" calle.id = " + calle.getId() + " AND");
			}
			if (calle.getIdTipoVia() != null) {
				conditions.append(" calle.`idTipoVia` = " + calle.getIdTipoVia() + " AND");
			}
			if(calle.getNombreVia() != null && !calle.getNombreVia().isEmpty()){
				conditions.append(" UPPER(calle.`nombreVia`) like '%"+calle.getNombreVia().toUpperCase()+"%' AND");
			}
			if(calle.getNumKmMinimo() != null && !calle.getNumKmMinimo().isEmpty()){
				conditions.append(" UPPER(calle.`numKmMinimo`) like '%"+calle.getNumKmMinimo().toUpperCase()+"%' AND");
			}
			if(calle.getNumKmMaximo() != null && !calle.getNumKmMaximo().isEmpty()){
				conditions.append(" UPPER(calle.`numKmMaximo`) like '%"+calle.getNumKmMaximo().toUpperCase()+"%' AND");
			}
			if (calle.getIdLocalidad() != null) {
				conditions.append(" calle.`idLocalidad` = " + calle.getIdLocalidad() + " AND");
			}
			if (calle.getIdSector() != null) {
				conditions.append(" calle.`idSector` = " + calle.getIdSector() + " AND");
			}
			if(calle.getEncargado() != null && !calle.getEncargado().isEmpty()){
				conditions.append(" UPPER(calle.`encargado`) like '%"+calle.getEncargado().toUpperCase()+"%' AND");
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
			query.addEntity("calle", CalleVO.class);
			
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
	
	public List<CalleVO> findUsingExactTemplate(Transaction transaction, CalleVO calle, String order){
		List<CalleVO> list = new ArrayList<CalleVO>();
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
			
			if(calle == null){
				throw new Exception("DAO Exception: Calle is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT calle.* FROM "+schema+"CALLE calle");
			StringBuilder conditions = new StringBuilder();
			if (calle.getId() != null) {
				conditions.append(" calle.id = " + calle.getId() + " AND");
			}
			if (calle.getIdTipoVia() != null) {
				conditions.append(" calle.`idTipoVia` = " + calle.getIdTipoVia() + " AND");
			}
			if(calle.getNombreVia() != null && !calle.getNombreVia().isEmpty()){
				conditions.append(" UPPER(calle.`nombreVia`) = '"+calle.getNombreVia().toUpperCase()+"' AND");
			}
			if(calle.getNumKmMinimo() != null && !calle.getNumKmMinimo().isEmpty()){
				conditions.append(" UPPER(calle.`numKmMinimo`) = '"+calle.getNumKmMinimo().toUpperCase()+"' AND");
			}
			if(calle.getNumKmMaximo() != null && !calle.getNumKmMaximo().isEmpty()){
				conditions.append(" UPPER(calle.`numKmMaximo`) = '"+calle.getNumKmMaximo().toUpperCase()+"' AND");
			}
			if (calle.getIdLocalidad() != null) {
				conditions.append(" calle.`idLocalidad` = " + calle.getIdLocalidad() + " AND");
			}
			if (calle.getIdSector() != null) {
				conditions.append(" calle.`idSector` = " + calle.getIdSector() + " AND");
			}
			if(calle.getEncargado() != null && !calle.getEncargado().isEmpty()){
				conditions.append(" UPPER(calle.`encargado`) = '"+calle.getEncargado().toUpperCase()+"' AND");
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
			query.addEntity("calle", CalleVO.class);
			
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
	
	
	/**
	 * Create an element into the Database
	 */
	public synchronized void createElement(Transaction transaction, CalleVO calle) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (calle == null) {
				throw new DaoException("Element is null");
			}
			
			if(calle.getId() == null){
				session.save(calle);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(calle);
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
	public synchronized void deleteElement(Transaction transaction, CalleVO calle) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor)ConfHibernate.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (calle == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			HermanoDaoImpl hermanoDao = (HermanoDaoImpl) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "HermanoDaoImpl");
			
			// 1.- Comprobamos que no hay ningún hermano asociado a la calle
			HermanoVO hermano = new HermanoVO();
			hermano.setIdCalle(calle.getId());
			List<HermanoVO> listAux = hermanoDao.findUsingTemplate(currentTransaction, hermano, null, null);
			
			if(listAux.size() > 0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", 
						"No se puede borrar la calle mientras esté asociada a algún hermano"));
			} else {
				StringBuilder queryString = new StringBuilder(
						"DELETE FROM "+schema+"CALLE ");
				StringBuilder conditions = new StringBuilder();
				if (calle.getId() != null) {
					conditions.append(" id = " + calle.getId() + " AND");
				}
				if (calle.getIdTipoVia() != null) {
					conditions.append(" calle.`idTipoVia` = " + calle.getIdTipoVia() + " AND");
				}
				if(calle.getNombreVia() != null && !calle.getNombreVia().isEmpty()){
					conditions.append(" UPPER(calle.`nombreVia`) = '"+calle.getNombreVia().toUpperCase()+"' AND");
				}
				if(calle.getNumKmMinimo() != null && !calle.getNumKmMinimo().isEmpty()){
					conditions.append(" UPPER(calle.`numKmMinimo`) = '"+calle.getNumKmMinimo().toUpperCase()+"' AND");
				}
				if(calle.getNumKmMaximo() != null && !calle.getNumKmMaximo().isEmpty()){
					conditions.append(" UPPER(calle.`numKmMaximo`) = '"+calle.getNumKmMaximo().toUpperCase()+"' AND");
				}
				if (calle.getIdLocalidad() != null) {
					conditions.append(" calle.`idLocalidad` = " + calle.getIdLocalidad() + " AND");
				}
				if (calle.getIdSector() != null) {
					conditions.append(" calle.`idSector` = " + calle.getIdSector() + " AND");
				}
				if(calle.getEncargado() != null && !calle.getEncargado().isEmpty()){
					conditions.append(" UPPER(calle.`encargado`) = '"+calle.getEncargado().toUpperCase()+"' AND");
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