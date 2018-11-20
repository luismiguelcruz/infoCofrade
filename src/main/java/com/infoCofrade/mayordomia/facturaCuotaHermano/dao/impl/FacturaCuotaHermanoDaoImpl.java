package com.infoCofrade.mayordomia.facturaCuotaHermano.dao.impl;

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
import com.infoCofrade.mayordomia.facturaCuotaHermano.dao.FacturaCuotaHermanoDao;
import com.infoCofrade.mayordomia.facturaCuotaHermano.vo.FacturaCuotaHermanoVO;


@Component("facturaCuotaHermanoDaoImpl")
public class FacturaCuotaHermanoDaoImpl implements FacturaCuotaHermanoDao{
	private static FacturaCuotaHermanoDao instance = new FacturaCuotaHermanoDaoImpl();
	private Session session;
	
	private FacturaCuotaHermanoDaoImpl(){}
	
	public static FacturaCuotaHermanoDao getInstance(){
		if (instance == null) {
            synchronized(FacturaCuotaHermanoDaoImpl.class) {
                if (instance == null) { 
                	instance = new FacturaCuotaHermanoDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized FacturaCuotaHermanoVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		FacturaCuotaHermanoVO facturaCuotaHermano = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from FacturaCuotaHermanoVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				facturaCuotaHermano = (FacturaCuotaHermanoVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return facturaCuotaHermano;
	}
	
	public synchronized List<FacturaCuotaHermanoVO> findAll(Transaction transaction, String order){
		List<FacturaCuotaHermanoVO> list = new ArrayList<FacturaCuotaHermanoVO>();
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
					"SELECT * FROM "+schema+"FACTURA_CUOTA_HERMANO");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(FacturaCuotaHermanoVO.class);
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
	
	public List<FacturaCuotaHermanoVO> findUsingTemplate(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano, String order){
		List<FacturaCuotaHermanoVO> list = new ArrayList<FacturaCuotaHermanoVO>();
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
			
			if(facturaCuotaHermano == null){
				throw new Exception("DAO Exception: FacturaCuotaHermano is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT facturaCuotaHermano.* FROM "+schema+"FACTURA_CUOTA_HERMANO facturaCuotaHermano");
			StringBuilder conditions = new StringBuilder();
			if (facturaCuotaHermano.getId() != null) {
				conditions.append(" facturaCuotaHermano.id = " + facturaCuotaHermano.getId() + " AND");
			}
			if(facturaCuotaHermano.getIdHermano() != null){
				conditions.append(" facturaCuotaHermano.`idHermano` = "+
						facturaCuotaHermano.getIdHermano()+" AND");
			}
			if(facturaCuotaHermano.getIdCuotaHermano() != null){
				conditions.append(" facturaCuotaHermano.`idCuotaHermano` <= "+
						facturaCuotaHermano.getIdCuotaHermano()+" AND");
			}
			if(facturaCuotaHermano.getMes() != null){
				conditions.append(" facturaCuotaHermano.`mes` = "+
						facturaCuotaHermano.getMes()+" AND");
			}
			if(facturaCuotaHermano.getAnyo() != null){
				conditions.append(" facturaCuotaHermano.`anyo` = "+
						facturaCuotaHermano.getAnyo()+" AND");
			}
			if(facturaCuotaHermano.getPagada() != null){
				conditions.append(" facturaCuotaHermano.`pagada` = "+
						facturaCuotaHermano.getPagada()+" AND");
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
			query.addEntity("facturaCuotaHermano", FacturaCuotaHermanoVO.class);
			
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
	
	public List<FacturaCuotaHermanoVO> findUsingExactTemplate(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano, String order){
		List<FacturaCuotaHermanoVO> list = new ArrayList<FacturaCuotaHermanoVO>();
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
			
			if(facturaCuotaHermano == null){
				throw new Exception("DAO Exception: FacturaCuotaHermano is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT facturaCuotaHermano.* FROM "+schema+"FACTURA_CUOTA_HERMANO facturaCuotaHermano");
			StringBuilder conditions = new StringBuilder();
			if (facturaCuotaHermano.getId() != null) {
				conditions.append(" facturaCuotaHermano.id = " + facturaCuotaHermano.getId() + " AND");
			}
			if(facturaCuotaHermano.getIdHermano() != null){
				conditions.append(" facturaCuotaHermano.`idHermano` = "+
						facturaCuotaHermano.getIdHermano()+" AND");
			}
			if(facturaCuotaHermano.getIdCuotaHermano() != null){
				conditions.append(" facturaCuotaHermano.`idCuotaHermano` <= "+
						facturaCuotaHermano.getIdCuotaHermano()+" AND");
			}
			if(facturaCuotaHermano.getMes() != null){
				conditions.append(" facturaCuotaHermano.`mes` = "+
						facturaCuotaHermano.getMes()+" AND");
			}
			if(facturaCuotaHermano.getAnyo() != null){
				conditions.append(" facturaCuotaHermano.`anyo` = "+
						facturaCuotaHermano.getAnyo()+" AND");
			}
			if(facturaCuotaHermano.getPagada() != null){
				conditions.append(" facturaCuotaHermano.`pagada` = "+
						facturaCuotaHermano.getPagada()+" AND");
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
			query.addEntity("facturaCuotaHermano", FacturaCuotaHermanoVO.class);
			
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
	public synchronized void createElement(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (facturaCuotaHermano == null) {
				throw new DaoException("Element is null");
			}
			
			if(facturaCuotaHermano.getId() == null){
				session.save(facturaCuotaHermano);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(facturaCuotaHermano);
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
	
	public void createElement(Transaction transaction, List<FacturaCuotaHermanoVO> listFacturasParaCrear) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (listFacturasParaCrear == null || listFacturasParaCrear.size() <= 0) {
				throw new DaoException("No se pueden crear las facturas porque la lista de facturas a crear está vacía");
			}
			
			
			for(FacturaCuotaHermanoVO item: listFacturasParaCrear){
				if(item.getId() == null){
					session.save(item);
				} else {
					session.merge(item);
				}	
			}
			
			if (transaction == null) {
				currentTransaction.commit();
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elementos creados correctamente"));
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
	
	public void createStructureElement(Transaction transaction, List<List<FacturaCuotaHermanoVO>> listFacturasParaCrear) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (listFacturasParaCrear == null || listFacturasParaCrear.size() <= 0) {
				throw new DaoException("No se pueden actualizar las facturas porque la lista de facturas a crear está vacía");
			}
			
			
			for(List<FacturaCuotaHermanoVO> listAnyo: listFacturasParaCrear){
				for(FacturaCuotaHermanoVO item: listAnyo){	
					if(item.getId() == null){
						session.save(item);
					} else {
						session.merge(item);
					}
				}
			}
			
			if (transaction == null) {
				currentTransaction.commit();
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elementos creados correctamente"));
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
	public synchronized void deleteElement(Transaction transaction, FacturaCuotaHermanoVO facturaCuotaHermano) {
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
			
			if (facturaCuotaHermano == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"FACTURA_CUOTA_HERMANO ");
			StringBuilder conditions = new StringBuilder();
			if (facturaCuotaHermano.getId() != null) {
				conditions.append(" facturaCuotaHermano.id = " + facturaCuotaHermano.getId() + " AND");
			}
			if(facturaCuotaHermano.getIdHermano() != null){
				conditions.append(" `idHermano` = "+
						facturaCuotaHermano.getIdHermano()+" AND");
			}
			if(facturaCuotaHermano.getIdCuotaHermano() != null){
				conditions.append(" `idCuotaHermano` <= "+
						facturaCuotaHermano.getIdCuotaHermano()+" AND");
			}
			if(facturaCuotaHermano.getMes() != null){
				conditions.append(" `mes` = "+
						facturaCuotaHermano.getMes()+" AND");
			}
			if(facturaCuotaHermano.getAnyo() != null){
				conditions.append(" `anyo` = "+
						facturaCuotaHermano.getAnyo()+" AND");
			}
			if(facturaCuotaHermano.getPagada() != null){
				conditions.append(" `pagada` = "+
						facturaCuotaHermano.getPagada()+" AND");
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