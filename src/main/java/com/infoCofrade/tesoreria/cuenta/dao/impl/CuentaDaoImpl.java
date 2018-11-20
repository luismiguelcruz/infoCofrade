package com.infoCofrade.tesoreria.cuenta.dao.impl;

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
import com.infoCofrade.tesoreria.cuenta.dao.CuentaDao;
import com.infoCofrade.tesoreria.cuenta.vo.CuentaVO;
import com.infoCofrade.tesoreria.gasto.dao.impl.GastoDaoImpl;
import com.infoCofrade.tesoreria.gasto.vo.GastoVO;
import com.infoCofrade.tesoreria.ingreso.dao.impl.IngresoDaoImpl;
import com.infoCofrade.tesoreria.ingreso.vo.IngresoVO;


@Component("cuentaDaoImpl")
public class CuentaDaoImpl implements CuentaDao{
	private static CuentaDao instance = new CuentaDaoImpl();
	private Session session;
	
	private CuentaDaoImpl(){}
	
	public static CuentaDao getInstance(){
		if (instance == null) {
            synchronized(CuentaDaoImpl.class) {
                if (instance == null) { 
                	instance = new CuentaDaoImpl();
                }
            }
        }
		
		return instance;
	}
	
	public synchronized CuentaVO findByPrimaryKey(Transaction transaction, Long id){
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		CuentaVO cuenta = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			StringBuilder queryString = new StringBuilder("from CuentaVO");
			if(id != null) {
				queryString.append(" where id = " + id);
				
				cuenta = (CuentaVO)session.createQuery(queryString.toString()).uniqueResult();
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
		
		return cuenta;
	}
	
	public synchronized List<CuentaVO> findAll(Transaction transaction, String order){
		List<CuentaVO> list = new ArrayList<CuentaVO>();
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
					"SELECT * FROM "+schema+"CUENTA");
			
			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(CuentaVO.class);
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
	
	public List<CuentaVO> findUsingTemplate(Transaction transaction, CuentaVO cuenta, String order){
		List<CuentaVO> list = new ArrayList<CuentaVO>();
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
			
			if(cuenta == null){
				throw new Exception("DAO Exception: Cuenta is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT cuenta.* FROM "+schema+"CUENTA cuenta");
			StringBuilder conditions = new StringBuilder();
			if (cuenta.getId() != null) {
				conditions.append(" cuenta.id = " + cuenta.getId() + " AND");
			}
			if (cuenta.getIdCuentaPadre() != null) {
				conditions.append(" cuenta.`idCuentaPadre` = " + cuenta.getIdCuentaPadre() + " AND");
			}
			if (cuenta.getNumeroCuenta() != null) {
				conditions.append(" cuenta.`numeroCuenta` = " + cuenta.getNumeroCuenta() + " AND");
			}
			if (cuenta.getNombre() != null && !cuenta.getNombre().isEmpty()) {
				conditions.append(" cuenta.`nombre` like '%" + cuenta.getNombre() + "%' AND");
			}
			if (cuenta.getIdTipoActividad() != null) {
				conditions.append(" cuenta.`idTipoActividad` = " + cuenta.getIdTipoActividad() + " AND");
			}
			if (cuenta.getAnyo() != null) {
				conditions.append(" cuenta.`anyo` = " + cuenta.getAnyo() + " AND");
			}
			if (cuenta.getEsConceptoFijo() != null) {
				conditions.append(" cuenta.`esConceptoFijo` = " + cuenta.getEsConceptoFijo() + " AND");
			}
			if (cuenta.getNivel() != null) {
				conditions.append(" cuenta.`nivel` = " + cuenta.getNivel() + " AND");
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
			query.addEntity("cuenta", CuentaVO.class);
			
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
	
	public List<CuentaVO> findUsingTemplateForLevel(Transaction transaction, CuentaVO cuenta, String order){
		List<CuentaVO> list = new ArrayList<CuentaVO>();
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
			
			if(cuenta == null){
				throw new Exception("DAO Exception: Cuenta is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT cuenta.* FROM "+schema+"CUENTA cuenta");
			StringBuilder conditions = new StringBuilder();
			if (cuenta.getId() != null) {
				conditions.append(" cuenta.id = " + cuenta.getId() + " AND");
			}
			if (cuenta.getIdCuentaPadre() != null) {
				conditions.append(" cuenta.`idCuentaPadre` = " + cuenta.getIdCuentaPadre() + " AND");
			} else {
				conditions.append(" cuenta.`idCuentaPadre` IS NULL AND");
			}
			if (cuenta.getNumeroCuenta() != null) {
				conditions.append(" cuenta.`numeroCuenta` = " + cuenta.getNumeroCuenta() + " AND");
			}
			if (cuenta.getNombre() != null && !cuenta.getNombre().isEmpty()) {
				conditions.append(" cuenta.`nombre` like '%" + cuenta.getNombre() + "%' AND");
			}
			if (cuenta.getIdTipoActividad() != null) {
				conditions.append(" cuenta.`idTipoActividad` = " + cuenta.getIdTipoActividad() + " AND");
			}
			if (cuenta.getAnyo() != null) {
				conditions.append(" cuenta.`anyo` = " + cuenta.getAnyo() + " AND");
			}
			if (cuenta.getEsConceptoFijo() != null) {
				conditions.append(" cuenta.`esConceptoFijo` = " + cuenta.getEsConceptoFijo() + " AND");
			}
			if (cuenta.getNivel() != null) {
				conditions.append(" cuenta.`nivel` = " + cuenta.getNivel() + " AND");
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
			query.addEntity("cuenta", CuentaVO.class);
			
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
	
	public List<CuentaVO> findUsingExactTemplate(Transaction transaction, CuentaVO cuenta, String order){
		List<CuentaVO> list = new ArrayList<CuentaVO>();
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
			
			if(cuenta == null){
				throw new Exception("DAO Exception: Cuenta is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT cuenta.* FROM "+schema+"CUENTA cuenta");
			StringBuilder conditions = new StringBuilder();
			if (cuenta.getId() != null) {
				conditions.append(" cuenta.id = " + cuenta.getId() + " AND");
			}
			if (cuenta.getIdCuentaPadre() != null) {
				conditions.append(" cuenta.`idCuentaPadre` = " + cuenta.getIdCuentaPadre() + " AND");
			}
			if (cuenta.getNumeroCuenta() != null) {
				conditions.append(" cuenta.`numeroCuenta` = " + cuenta.getNumeroCuenta() + " AND");
			}
			if (cuenta.getNombre() != null && !cuenta.getNombre().isEmpty()) {
				conditions.append(" cuenta.`nombre` = '" + cuenta.getNombre() + "' AND");
			}
			if (cuenta.getIdTipoActividad() != null) {
				conditions.append(" cuenta.`idTipoActividad` = " + cuenta.getIdTipoActividad() + " AND");
			}
			if (cuenta.getAnyo() != null) {
				conditions.append(" cuenta.`anyo` = " + cuenta.getAnyo() + " AND");
			}
			if (cuenta.getEsConceptoFijo() != null) {
				conditions.append(" cuenta.`esConceptoFijo` = " + cuenta.getEsConceptoFijo() + " AND");
			}
			if (cuenta.getNivel() != null) {
				conditions.append(" cuenta.`nivel` = " + cuenta.getNivel() + " AND");
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
			query.addEntity("cuenta", CuentaVO.class);
			
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
	public synchronized void createElement(Transaction transaction, CuentaVO cuenta) throws DaoException{
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null || !session.getTransaction().isActive()) {
		        currentTransaction = session.beginTransaction();
		    } else if(session.getTransaction().isActive()){
		    	currentTransaction = session.getTransaction();
		    }
			
			if (cuenta == null) {
				throw new DaoException("Element is null");
			}
			
			if(cuenta.getId() == null){
				session.save(cuenta);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Elemento creado correctamente"));
			} else {
				session.merge(cuenta);
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
	public synchronized void deleteElement(Transaction transaction, CuentaVO cuenta) {
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
			
			if (cuenta == null) {
				throw new Exception("Element is null");
			}
			
			if(schema == null){
				schema = "";
			} else {
				schema += ".";
			}
			
			// 1. Eliminamos todos los gastos que estuvieran asociados a la cuenta
			GastoDaoImpl gastoDao = (GastoDaoImpl) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "gastoDaoImpl");
			
			GastoVO gasto = new GastoVO();
			gasto.setIdCuenta(cuenta.getId());
			gastoDao.deleteElement(currentTransaction, gasto);
			
			// 2. Eliminados todos los ingresos que estuvieran asociados a la cuenta
			IngresoDaoImpl ingresoDao = (IngresoDaoImpl) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "ingresoDaoImpl");
			
			IngresoVO ingreso = new IngresoVO();
			ingreso.setIdCuenta(cuenta.getId());
			ingresoDao.deleteElement(currentTransaction, ingreso);
			
			// 3. Eliminados las subcuentas que estuvieran asociadas a la cuenta
			CuentaVO subcuenta = new CuentaVO();
			subcuenta.setIdCuentaPadre(cuenta.getId());
			List<CuentaVO> listSubCuentas = this.findUsingTemplate(currentTransaction, subcuenta, null);
			if(listSubCuentas.size() > 0){
				for(CuentaVO cuentaSelected: listSubCuentas){
					this.deleteElement(currentTransaction, cuentaSelected);	
				}
			}
			
			// 4. Eliminados la cuenta seleccionada
			StringBuilder queryString = new StringBuilder(
					"DELETE FROM "+schema+"CUENTA ");
			StringBuilder conditions = new StringBuilder();
			if (cuenta.getId() != null) {
				conditions.append(" id = " + cuenta.getId() + " AND");
			}
			if (cuenta.getIdCuentaPadre() != null) {
				conditions.append(" `idCuentaPadre` = " + cuenta.getIdCuentaPadre() + " AND");
			}
			if (cuenta.getNumeroCuenta() != null) {
				conditions.append(" `numeroCuenta` = " + cuenta.getNumeroCuenta() + " AND");
			}
			if (cuenta.getNombre() != null && !cuenta.getNombre().isEmpty()) {
				conditions.append(" `nombre` = '" + cuenta.getNombre() + "' AND");
			}
			if (cuenta.getIdTipoActividad() != null) {
				conditions.append(" `idTipoActividad` = " + cuenta.getIdTipoActividad() + " AND");
			}
			if (cuenta.getAnyo() != null) {
				conditions.append(" `anyo` = " + cuenta.getAnyo() + " AND");
			}
			if (cuenta.getEsConceptoFijo() != null) {
				conditions.append(" `esConceptoFijo` = " + cuenta.getEsConceptoFijo() + " AND");
			}
			if (cuenta.getNivel() != null) {
				conditions.append(" `nivel` = " + cuenta.getNivel() + " AND");
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