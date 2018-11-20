package com.infoCofrade.secretaria.hermano.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Component;

import com.infoCofrade.administration.secretaria.status.vo.StatusVO;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.common.hibernate.ConfHibernate;
import com.infoCofrade.secretaria.hermano.dao.HermanoDao;
import com.infoCofrade.secretaria.hermano.vo.HermanoVO;
import com.infoCofrade.secretaria.statusPorHermano.dao.impl.StatusPorHermanoDaoImpl;
import com.infoCofrade.secretaria.statusPorHermano.vo.StatusPorHermanoVO;

@Component("hermanoDaoImpl")
public class HermanoDaoImpl implements HermanoDao {
	private static HermanoDao instance = new HermanoDaoImpl();
	private Session session;

	private HermanoDaoImpl() {
	}

	public static HermanoDao getInstance() {
		if (instance == null) {
			synchronized (HermanoDaoImpl.class) {
				if (instance == null) {
					instance = new HermanoDaoImpl();
				}
			}
		}

		return instance;
	}

	public synchronized HermanoVO findByPrimaryKey(Transaction transaction,
			Long id) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		HermanoVO hermano = null;
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			StringBuilder queryString = new StringBuilder("from HermanoVO");
			if (id != null) {
				queryString.append(" where id = " + id);

				hermano = (HermanoVO) session.createQuery(
						queryString.toString()).uniqueResult();
			}

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error de base de datos: ", ex.getMessage()));
			}
		}

		return hermano;
	}

	public Long findLastBrother(Transaction transaction) {
		Long result = null;
		String schema = ((SessionFactoryImplementor) ConfHibernate
				.getSessionFactory()).getSettings().getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (schema == null) {
				schema = "";
			} else {
				schema += ".";
			}
			
			StringBuilder queryString = new StringBuilder("SELECT * FROM "
					+ schema + "HERMANO ");
			
			SQLQuery query = session.createSQLQuery(queryString.toString());
			List<HermanoVO> list = query.list();

			if(list != null && list.size() > 0){
				queryString = new StringBuilder(
						"SELECT MAX(`numHermano`) FROM " + schema + "HERMANO");
	
				query = session.createSQLQuery(queryString.toString());
				result = ((BigInteger) query.uniqueResult()).longValue();
				if (transaction == null) {
					currentTransaction.commit();
				}
			}
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error de base de datos: ", ex.getMessage()));
			}
		}

		return result;
	}

	public synchronized List<HermanoVO> findAll(Transaction transaction,
			String order, Boolean onlyActive) {
		List<HermanoVO> list = new ArrayList<HermanoVO>();
		String schema = ((SessionFactoryImplementor) ConfHibernate
				.getSessionFactory()).getSettings().getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (schema == null) {
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder("SELECT * FROM "
					+ schema + "HERMANO ");

			if (onlyActive != null) {
				if (onlyActive) {
					queryString.append(" WHERE `fechaBaja` IS NULL ");
				} else {
					queryString.append(" WHERE `fechaBaja` IS NOT NULL ");
				}
			}

			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			}

			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity(HermanoVO.class);
			list = query.list();
			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			if (transaction == null) {
				ex.printStackTrace();
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error de base de datos: ", ex.getMessage()));
			}
		}

		return list;
	}

	public List<HermanoVO> findUsingTemplate(Transaction transaction,
			HermanoVO hermano, String order, Boolean onlyActive) {
		List<HermanoVO> list = new ArrayList<HermanoVO>();
		String schema = ((SessionFactoryImplementor) ConfHibernate
				.getSessionFactory()).getSettings().getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (hermano == null) {
				throw new Exception("DAO Exception: Hermano is null");
			}

			if (schema == null) {
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT hermano.* FROM " + schema + "HERMANO hermano");
			StringBuilder conditions = new StringBuilder();
			if (hermano.getId() != null) {
				conditions.append(" hermano.id = " + hermano.getId() + " AND");
			}
			if (hermano.getNumHermano() != null) {
				conditions.append(" hermano.`numHermano` = '"
						+ hermano.getNumHermano() + "' AND");
			}
			if (hermano.getNumIngreso() != null) {
				conditions.append(" hermano.`numIngreso` = '"
						+ hermano.getNumIngreso() + "' AND");
			}
			if (hermano.getNombre() != null && !hermano.getNombre().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombre`) like '%"
						+ hermano.getNombre().toUpperCase() + "%' AND");
			}
			if (hermano.getApellidos() != null
					&& !hermano.getApellidos().isEmpty()) {
				conditions.append(" UPPER(hermano.`apellidos`) like '%"
						+ hermano.getApellidos().toUpperCase() + "%' AND");
			}
			if (hermano.getNif() != null && !hermano.getNif().isEmpty()) {
				conditions.append(" UPPER(hermano.`nif`) like '%"
						+ hermano.getNif().toUpperCase() + "%' AND");
			}
			if (hermano.getIdCalle() != null) {
				conditions.append(" UPPER(hermano.`idCalle`) = '"
						+ hermano.getIdCalle() + "' AND");
			}
			/*
			 * if(hermano.getIdTipoVia() != null){
			 * conditions.append(" UPPER(hermano.`idTipoVia`) = '" +
			 * hermano.getIdTipoVia()+"' AND"); } if(hermano.getNombreVia() !=
			 * null && !hermano.getNombreVia().isEmpty()){
			 * conditions.append(" UPPER(hermano.`nombreVia`) like '%" +
			 * hermano.getNombreVia().toUpperCase()+"%' AND"); }
			 */
			if (hermano.getNumKm() != null && !hermano.getNumKm().isEmpty()) {
				conditions.append(" UPPER(hermano.`numKm`) like '%"
						+ hermano.getNumKm().toUpperCase() + "%' AND");
			}
			if (hermano.getBloque() != null && !hermano.getBloque().isEmpty()) {
				conditions.append(" UPPER(hermano.`bloque`) like '%"
						+ hermano.getBloque().toUpperCase() + "%' AND");
			}
			if (hermano.getEscalera() != null
					&& !hermano.getEscalera().isEmpty()) {
				conditions.append(" UPPER(hermano.`escalera`) like '%"
						+ hermano.getEscalera().toUpperCase() + "%' AND");
			}
			if (hermano.getPiso() != null && !hermano.getPiso().isEmpty()) {
				conditions.append(" UPPER(hermano.`piso`) like '%"
						+ hermano.getPiso().toUpperCase() + "%' AND");
			}
			if (hermano.getPuerta() != null && !hermano.getPuerta().isEmpty()) {
				conditions.append(" UPPER(hermano.`puerta`) like '%"
						+ hermano.getPuerta().toUpperCase() + "%' AND");
			}
			/*
			 * if (hermano.getIdLocalidad() != null) {
			 * conditions.append(" hermano.`idLocalidad` = " +
			 * hermano.getIdLocalidad() + " AND"); }
			 */
			if (hermano.getIdSector() != null) {
				conditions.append(" hermano.`idSector` = "
						+ hermano.getIdSector() + " AND");
			}
			if (hermano.getFechaNacimiento() != null) {
				conditions.append(" hermano.`fechaNacimiento` = '"
						+ hermano.getFechaNacimiento() + "' AND");
			}
			if (hermano.getTelefonoFijo() != null
					&& !hermano.getTelefonoFijo().isEmpty()) {
				conditions.append(" UPPER(hermano.`telefonoFijo`) like '%"
						+ hermano.getTelefonoFijo().toUpperCase() + "%' AND");
			}
			if (hermano.getTelefonoMovil() != null
					&& !hermano.getTelefonoMovil().isEmpty()) {
				conditions.append(" UPPER(hermano.`telefonoMovil`) like '%"
						+ hermano.getTelefonoMovil().toUpperCase() + "%' AND");
			}
			if (hermano.getEmail() != null && !hermano.getEmail().isEmpty()) {
				conditions.append(" UPPER(hermano.`email`) like '%"
						+ hermano.getEmail().toUpperCase() + "%' AND");
			}
			if (hermano.getNombrePadre() != null
					&& !hermano.getNombrePadre().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombrePadre`) like '%"
						+ hermano.getNombrePadre().toUpperCase() + "%' AND");
			}
			if (hermano.getNombreMadre() != null
					&& !hermano.getNombreMadre().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombreMadre`) like '%"
						+ hermano.getNombreMadre().toUpperCase() + "%' AND");
			}
			if (hermano.getNombreTutor() != null
					&& !hermano.getNombreTutor().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombreTutor`) like '%"
						+ hermano.getNombreTutor().toUpperCase() + "%' AND");
			}
			if (hermano.getLugarBautismo() != null
					&& !hermano.getLugarBautismo().isEmpty()) {
				conditions.append(" UPPER(hermano.`lugarBautismo`) like '%"
						+ hermano.getLugarBautismo().toUpperCase() + "%' AND");
			}
			if (hermano.getFechaBautismo() != null) {
				conditions.append(" hermano.`fechaBautismo` = '"
						+ hermano.getFechaBautismo() + "' AND");
			}
			if (hermano.getProfesion() != null
					&& !hermano.getProfesion().isEmpty()) {
				conditions.append(" UPPER(hermano.`profesion`) like '%"
						+ hermano.getProfesion().toUpperCase() + "%' AND");
			}
			if (hermano.getLibro() != null && !hermano.getLibro().isEmpty()) {
				conditions.append(" UPPER(hermano.`libro`) like '%"
						+ hermano.getLibro().toUpperCase() + "%' AND");
			}
			if (hermano.getFechaSolicitud() != null) {
				conditions.append(" hermano.`fechaSolicitud` = '"
						+ hermano.getFechaSolicitud() + "' AND");
			}
			if (hermano.getFechaJura() != null) {
				conditions.append(" hermano.`fechaJura` = '"
						+ hermano.getFechaJura() + "' AND");
			}
			if (hermano.getFechaBaja() != null) {
				conditions.append(" hermano.`fechaBaja` = '"
						+ hermano.getFechaBaja() + "' AND");
			}
			if (hermano.getMotivoBaja() != null
					&& !hermano.getMotivoBaja().isEmpty()) {
				conditions.append(" UPPER(hermano.`motivoBaja`) like '%"
						+ hermano.getMotivoBaja().toUpperCase() + "%' AND");
			}
			if (hermano.getIdCalleCobro() != null) {
				conditions.append(" UPPER(hermano.`idCalleCobro`) = '"
						+ hermano.getIdCalleCobro() + "' AND");
			}
			/*if (hermano.getIdTipoViaCobro() != null) {
				conditions.append(" UPPER(hermano.`idTipoViaCobro`) = '"
						+ hermano.getIdTipoViaCobro() + "' AND");
			}
			if (hermano.getNombreViaCobro() != null
					&& !hermano.getNombreViaCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombreViaCobro`) like '%"
						+ hermano.getNombreViaCobro().toUpperCase() + "%' AND");
			}*/
			if (hermano.getNumKmCobro() != null
					&& !hermano.getNumKmCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`numKmCobro`) like '%"
						+ hermano.getNumKmCobro().toUpperCase() + "%' AND");
			}
			if (hermano.getBloqueCobro() != null
					&& !hermano.getBloqueCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`bloqueCobro`) like '%"
						+ hermano.getBloqueCobro().toUpperCase() + "%' AND");
			}
			if (hermano.getEscaleraCobro() != null
					&& !hermano.getEscaleraCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`escaleraCobro`) like '%"
						+ hermano.getEscaleraCobro().toUpperCase() + "%' AND");
			}
			if (hermano.getPisoCobro() != null
					&& !hermano.getPisoCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`pisoCobro`) like '%"
						+ hermano.getPisoCobro().toUpperCase() + "%' AND");
			}
			if (hermano.getPuertaCobro() != null
					&& !hermano.getPuertaCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`puertaCobro`) like '%"
						+ hermano.getPuertaCobro().toUpperCase() + "%' AND");
			}
			/*if (hermano.getIdLocalidadCobro() != null) {
				conditions.append(" hermano.`idLocalidadCobro` = "
						+ hermano.getIdLocalidadCobro() + " AND");
			}*/
			if (hermano.getObservaciones() != null
					&& !hermano.getObservaciones().isEmpty()) {
				conditions.append(" UPPER(hermano.`observaciones`) like '%"
						+ hermano.getObservaciones().toUpperCase() + "%' AND");
			}
			if (hermano.getIdMotivoBaja() != null) {
				conditions.append(" hermano.`idMotivoBaja` = "
						+ hermano.getIdMotivoBaja() + " AND");
			}

			if (onlyActive != null) {
				if (onlyActive) {
					conditions.append(" hermano.`fechaBaja` IS NULL AND");
				} else {
					conditions.append(" hermano.`fechaBaja` IS NOT NULL AND");
				}
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
			query.addEntity("hermano", HermanoVO.class);

			list = query.list();

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No se han encontrado elementos", ex
										.getMessage()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Find Elements using template error", ex
										.getMessage()));
			}
		}

		return list;
	}

	public List<HermanoVO> findUsingExactTemplate(Transaction transaction,
			HermanoVO hermano, String order, Boolean onlyActive) {
		List<HermanoVO> list = new ArrayList<HermanoVO>();
		String schema = ((SessionFactoryImplementor) ConfHibernate
				.getSessionFactory()).getSettings().getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (hermano == null) {
				throw new Exception("DAO Exception: Hermano is null");
			}

			if (schema == null) {
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT hermano.* FROM " + schema + "HERMANO hermano");
			StringBuilder conditions = new StringBuilder();
			if (hermano.getId() != null) {
				conditions.append(" hermano.id = " + hermano.getId() + " AND");
			}
			if (hermano.getNumHermano() != null) {
				conditions.append(" hermano.`numHermano` = '"
						+ hermano.getNumHermano() + "' AND");
			}
			if (hermano.getNumIngreso() != null) {
				conditions.append(" hermano.`numIngreso` = '"
						+ hermano.getNumIngreso() + "' AND");
			}
			if (hermano.getNombre() != null && !hermano.getNombre().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombre`) = '"
						+ hermano.getNombre().toUpperCase() + "' AND");
			}
			if (hermano.getApellidos() != null
					&& !hermano.getApellidos().isEmpty()) {
				conditions.append(" UPPER(hermano.`apellidos`) = '"
						+ hermano.getApellidos().toUpperCase() + "' AND");
			}
			if (hermano.getNif() != null && !hermano.getNif().isEmpty()) {
				conditions.append(" UPPER(hermano.`nif`) = '"
						+ hermano.getNif().toUpperCase() + "' AND");
			}
			if (hermano.getIdCalle() != null) {
				conditions.append(" UPPER(hermano.`idCalle`) = '"
						+ hermano.getIdCalle() + "' AND");
			}
			/*
			 * if(hermano.getIdTipoVia() != null){
			 * conditions.append(" UPPER(hermano.`idTipoVia`) = '" +
			 * hermano.getIdTipoVia()+"' AND"); } if(hermano.getNombreVia() !=
			 * null && !hermano.getNombreVia().isEmpty()){
			 * conditions.append(" UPPER(hermano.`nombreVia`) = '" +
			 * hermano.getNombreVia().toUpperCase()+"' AND"); }
			 */
			if (hermano.getNumKm() != null && !hermano.getNumKm().isEmpty()) {
				conditions.append(" UPPER(hermano.`numKm`) = '"
						+ hermano.getNumKm().toUpperCase() + "' AND");
			}
			if (hermano.getBloque() != null && !hermano.getBloque().isEmpty()) {
				conditions.append(" UPPER(hermano.`bloque`) = '"
						+ hermano.getBloque().toUpperCase() + "' AND");
			}
			if (hermano.getEscalera() != null
					&& !hermano.getEscalera().isEmpty()) {
				conditions.append(" UPPER(hermano.`escalera`) = '"
						+ hermano.getEscalera().toUpperCase() + "' AND");
			}
			if (hermano.getPiso() != null && !hermano.getPiso().isEmpty()) {
				conditions.append(" UPPER(hermano.`piso`) = '"
						+ hermano.getPiso().toUpperCase() + "' AND");
			}
			if (hermano.getPuerta() != null && !hermano.getPuerta().isEmpty()) {
				conditions.append(" UPPER(hermano.`puerta`) = '"
						+ hermano.getPuerta().toUpperCase() + "' AND");
			}
			if (hermano.getIdSector() != null) {
				conditions.append(" hermano.`idSector` = "
						+ hermano.getIdSector() + " AND");
			}
			/*
			 * if (hermano.getIdLocalidad() != null) {
			 * conditions.append(" hermano.`idLocalidad` = " +
			 * hermano.getIdLocalidad() + " AND"); }
			 */
			if (hermano.getFechaNacimiento() != null) {
				conditions.append(" hermano.`fechaNacimiento` = '"
						+ hermano.getFechaNacimiento() + "' AND");
			}
			if (hermano.getTelefonoFijo() != null
					&& !hermano.getTelefonoFijo().isEmpty()) {
				conditions.append(" UPPER(hermano.`telefonoFijo`) = '"
						+ hermano.getTelefonoFijo().toUpperCase() + "' AND");
			}
			if (hermano.getTelefonoMovil() != null
					&& !hermano.getTelefonoMovil().isEmpty()) {
				conditions.append(" UPPER(hermano.`telefonoMovil`) = '"
						+ hermano.getTelefonoMovil().toUpperCase() + "' AND");
			}
			if (hermano.getEmail() != null && !hermano.getEmail().isEmpty()) {
				conditions.append(" UPPER(hermano.`email`) = '"
						+ hermano.getEmail().toUpperCase() + "' AND");
			}
			if (hermano.getNombrePadre() != null
					&& !hermano.getNombrePadre().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombrePadre`) = '"
						+ hermano.getNombrePadre().toUpperCase() + "' AND");
			}
			if (hermano.getNombreMadre() != null
					&& !hermano.getNombreMadre().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombreMadre`) = '"
						+ hermano.getNombreMadre().toUpperCase() + "' AND");
			}
			if (hermano.getNombreTutor() != null
					&& !hermano.getNombreTutor().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombreTutor`) = '"
						+ hermano.getNombreTutor().toUpperCase() + "' AND");
			}
			if (hermano.getLugarBautismo() != null
					&& !hermano.getLugarBautismo().isEmpty()) {
				conditions.append(" UPPER(hermano.`lugarBautismo`) = '"
						+ hermano.getLugarBautismo().toUpperCase() + "' AND");
			}
			if (hermano.getFechaBautismo() != null) {
				conditions.append(" hermano.`fechaBautismo` = '"
						+ hermano.getFechaBautismo() + "' AND");
			}
			if (hermano.getProfesion() != null
					&& !hermano.getProfesion().isEmpty()) {
				conditions.append(" UPPER(hermano.`profesion`) = '"
						+ hermano.getProfesion().toUpperCase() + "' AND");
			}
			if (hermano.getLibro() != null && !hermano.getLibro().isEmpty()) {
				conditions.append(" UPPER(hermano.`libro`) = '"
						+ hermano.getLibro().toUpperCase() + "' AND");
			}
			if (hermano.getFechaSolicitud() != null) {
				conditions.append(" hermano.`fechaSolicitud` = '"
						+ hermano.getFechaSolicitud() + "' AND");
			}
			if (hermano.getFechaJura() != null) {
				conditions.append(" hermano.`fechaJura` = '"
						+ hermano.getFechaJura() + "' AND");
			}
			if (hermano.getFechaBaja() != null) {
				conditions.append(" hermano.`fechaBaja` = '"
						+ hermano.getFechaBaja() + "' AND");
			}
			if (hermano.getMotivoBaja() != null
					&& !hermano.getMotivoBaja().isEmpty()) {
				conditions.append(" UPPER(hermano.`motivoBaja`) = '"
						+ hermano.getMotivoBaja().toUpperCase() + "' AND");
			}
			if (hermano.getIdCalleCobro() != null) {
				conditions.append(" UPPER(hermano.`idCalleCobro`) = '"
						+ hermano.getIdCalleCobro() + "' AND");
			}
			/*if (hermano.getIdTipoViaCobro() != null) {
				conditions.append(" UPPER(hermano.`idTipoViaCobro`) = '"
						+ hermano.getIdTipoViaCobro() + "' AND");
			}
			if (hermano.getNombreViaCobro() != null
					&& !hermano.getNombreViaCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombreViaCobro`) = '"
						+ hermano.getNombreViaCobro().toUpperCase() + "' AND");
			}*/
			if (hermano.getNumKmCobro() != null
					&& !hermano.getNumKmCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`numKmCobro`) = '"
						+ hermano.getNumKmCobro().toUpperCase() + "' AND");
			}
			if (hermano.getBloqueCobro() != null
					&& !hermano.getBloqueCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`bloqueCobro`) = '"
						+ hermano.getBloqueCobro().toUpperCase() + "' AND");
			}
			if (hermano.getEscaleraCobro() != null
					&& !hermano.getEscaleraCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`escaleraCobro`) = '"
						+ hermano.getEscaleraCobro().toUpperCase() + "' AND");
			}
			if (hermano.getPisoCobro() != null
					&& !hermano.getPisoCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`pisoCobro`) = '"
						+ hermano.getPisoCobro().toUpperCase() + "' AND");
			}
			if (hermano.getPuertaCobro() != null
					&& !hermano.getPuertaCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`puertaCobro`) = '"
						+ hermano.getPuertaCobro().toUpperCase() + "' AND");
			}
			/*if (hermano.getIdLocalidadCobro() != null) {
				conditions.append(" hermano.`idLocalidadCobro` = "
						+ hermano.getIdLocalidadCobro() + " AND");
			}*/
			if (hermano.getObservaciones() != null
					&& !hermano.getObservaciones().isEmpty()) {
				conditions.append(" UPPER(hermano.`observaciones`) = '"
						+ hermano.getObservaciones().toUpperCase() + "' AND");
			}
			if (hermano.getIdMotivoBaja() != null) {
				conditions.append(" hermano.`idMotivoBaja` = "
						+ hermano.getIdMotivoBaja() + " AND");
			}

			if (onlyActive != null) {
				if (onlyActive) {
					conditions.append(" hermano.`fechaBaja` IS NULL AND");
				} else {
					conditions.append(" hermano.`fechaBaja` IS NOT NULL AND");
				}
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
			query.addEntity("hermano", HermanoVO.class);

			list = query.list();

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"No se encontraron elementos", ex
												.getMessage()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Find Elements using template error", ex
										.getMessage()));
			}
		}

		return list;
	}

	/**
	 * Actualiza el numero de hermano para todos aquellos hermanos cuyo numero
	 * de hermano sea posterior al del hermano que recibe como parámetro
	 */
	public void updateBrotherNumber(Transaction transaction, HermanoVO hermano,
			String order) {
		String schema = ((SessionFactoryImplementor) ConfHibernate
				.getSessionFactory()).getSettings().getDefaultSchemaName();
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (hermano == null) {
				throw new Exception("DAO Exception: Hermano is null");
			}

			if (schema == null) {
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT hermano.* FROM " + schema + "HERMANO hermano");
			StringBuilder conditions = new StringBuilder(
					"hermano.`numHermano` > " + hermano.getNumHermano()
							+ " AND");

			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}

			queryString.append(conditions);

			if (order != null && !order.isEmpty()) {
				queryString.append(" ORDER BY " + order);
			} else {
				queryString.append(" ORDER BY `numHermano` ASC");
			}

			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("hermano", HermanoVO.class);

			List<HermanoVO> list = query.list();

			if (!list.isEmpty()) {
				for (HermanoVO item : list) {
					item.setNumHermano(item.getNumHermano() - 1);
					session.merge(item);
					session.flush();
				}
			}

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (NoSuchElementException ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"No se encontraron elementos", ex
												.getMessage()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Find Elements using template error", ex
										.getMessage()));
			}
		}
	}

	/**
	 * Create an element into the Database
	 */
	public synchronized void createElement(Transaction transaction,
			HermanoVO hermano) throws DaoException {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (hermano == null) {
				throw new DaoException("Element is null");
			}

			if (hermano.getId() == null) {
				session.save(hermano);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								"Elemento creado correctamente"));
			} else {
				session.merge(hermano);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								"Elemento actualizado correctamente"));
			}

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Cannot create element", ex.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}
	}

	/**
	 * Crea un hermano en la base de datos y le asocia todos los status
	 * seleccionados para él
	 */
	public void createElement(Transaction transaction, HermanoVO hermano,
			List<StatusVO> listStatusPorHermano) throws DaoException {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (hermano == null) {
				throw new DaoException("Element is null");
			}

			StatusPorHermanoDaoImpl statusPorHermanoDao = (StatusPorHermanoDaoImpl) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "statusPorHermanoDaoImpl");

			if (hermano.getId() == null) {
				session.save(hermano);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								"Elemento creado correctamente"));
			} else {
				session.merge(hermano);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								"Elemento actualizado correctamente"));
			}

			StringBuilder ids = new StringBuilder();
			for (StatusVO status : listStatusPorHermano) {
				ids.append(status.getId() + ",");
			}

			if (ids.length() > 0) {
				ids.deleteCharAt(ids.length() - 1);
			}

			String where = "WHERE `idHermano` = " + hermano.getId();
			if (ids.length() > 0) {
				where = where + " AND `idStatus` NOT IN (" + ids + ")";
			}
			statusPorHermanoDao.deleteElementByWhere(currentTransaction, where);

			// Buscamos todos los hermanos insertados que aun quedan asociados
			// al hermano y lo metemos en
			// un map
			StatusPorHermanoVO statusPorHermanoSearch = new StatusPorHermanoVO();
			statusPorHermanoSearch.setIdHermano(hermano.getId());
			List<StatusPorHermanoVO> listAux = statusPorHermanoDao
					.findUsingTemplate(currentTransaction,
							statusPorHermanoSearch, null);

			Map<Long, StatusPorHermanoVO> mapSelected = new HashMap<Long, StatusPorHermanoVO>();
			for (StatusPorHermanoVO item : listAux) {
				mapSelected.put(item.getIdStatus(), item);
			}

			// 3.- Para cada status, creamos un nuevo status para el hermano si
			// dicho estatus no existia
			StatusPorHermanoVO statusPorHermano = null;
			for (StatusVO status : listStatusPorHermano) {
				if (!mapSelected.containsKey(status.getId())) {
					statusPorHermano = new StatusPorHermanoVO();
					statusPorHermano.setIdStatus(status.getId());
					statusPorHermano.setIdHermano(hermano.getId());

					statusPorHermanoDao.createElement(currentTransaction,
							statusPorHermano);
				}
			}

			if (transaction == null) {
				currentTransaction.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Cannot create element", ex.getMessage()));
				throw new DaoException(ex.getMessage());
			}
		}
	}

	/**
	 * Delete an element from the Database
	 */
	public synchronized void deleteElement(Transaction transaction,
			HermanoVO hermano) {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor) ConfHibernate
				.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (hermano == null) {
				throw new Exception("Element is null");
			}

			if (schema == null) {
				schema = "";
			} else {
				schema += ".";
			}

			StatusPorHermanoDaoImpl statusPorHermanoDao = (StatusPorHermanoDaoImpl) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "statusPorHermanoDaoImpl");

			// 1.- Eliminamos los status asociados al hermano en cuestion
			StatusPorHermanoVO statusPorHermano = new StatusPorHermanoVO();
			statusPorHermano.setIdHermano(hermano.getId());
			statusPorHermanoDao.deleteElement(currentTransaction,
					statusPorHermano);

			// 2.- Eliminamos el hermano seleccionado
			StringBuilder queryString = new StringBuilder("DELETE FROM "
					+ schema + "HERMANO ");
			StringBuilder conditions = new StringBuilder();
			if (hermano.getId() != null) {
				conditions.append(" id = " + hermano.getId() + " AND");
			}
			if (hermano.getNumHermano() != null) {
				conditions.append(" `numHermano` = " + hermano.getNumHermano()
						+ " AND");
			}
			if (hermano.getNumIngreso() != null) {
				conditions.append(" `numIngreso` = " + hermano.getNumIngreso()
						+ " AND");
			}
			if (hermano.getNombre() != null && !hermano.getNombre().isEmpty()) {
				conditions.append(" UPPER(`nombre`) = '"
						+ hermano.getNombre().toUpperCase() + "' AND");
			}
			if (hermano.getApellidos() != null
					&& !hermano.getApellidos().isEmpty()) {
				conditions.append(" UPPER(`apellidos`) = '"
						+ hermano.getApellidos().toUpperCase() + "' AND");
			}
			if (hermano.getNif() != null && !hermano.getNif().isEmpty()) {
				conditions.append(" UPPER(`nif`) = '"
						+ hermano.getNif().toUpperCase() + "' AND");
			}
			if (hermano.getIdCalle() != null) {
				conditions.append(" UPPER(hermano.`idCalle`) = '"
						+ hermano.getIdCalle() + "' AND");
			}
			/*
			 * if(hermano.getIdTipoVia() != null){
			 * conditions.append(" UPPER(hermano.`idTipoVia`) = '" +
			 * hermano.getIdTipoVia()+"' AND"); } if(hermano.getNombreVia() !=
			 * null && !hermano.getNombreVia().isEmpty()){
			 * conditions.append(" UPPER(hermano.`nombreVia`) = '" +
			 * hermano.getNombreVia().toUpperCase()+"' AND"); }
			 */
			if (hermano.getNumKm() != null && !hermano.getNumKm().isEmpty()) {
				conditions.append(" UPPER(hermano.`numKm`) = '"
						+ hermano.getNumKm().toUpperCase() + "' AND");
			}
			if (hermano.getBloque() != null && !hermano.getBloque().isEmpty()) {
				conditions.append(" UPPER(hermano.`bloque`) = '"
						+ hermano.getBloque().toUpperCase() + "' AND");
			}
			if (hermano.getEscalera() != null
					&& !hermano.getEscalera().isEmpty()) {
				conditions.append(" UPPER(hermano.`escalera`) = '"
						+ hermano.getEscalera().toUpperCase() + "' AND");
			}
			if (hermano.getPiso() != null && !hermano.getPiso().isEmpty()) {
				conditions.append(" UPPER(hermano.`piso`) = '"
						+ hermano.getPiso().toUpperCase() + "' AND");
			}
			if (hermano.getPuerta() != null && !hermano.getPuerta().isEmpty()) {
				conditions.append(" UPPER(hermano.`puerta`) = '"
						+ hermano.getPuerta().toUpperCase() + "' AND");
			}
			if (hermano.getIdSector() != null) {
				conditions.append(" `idSector` = " + hermano.getIdSector()
						+ " AND");
			}
			/*
			 * if (hermano.getIdLocalidad() != null) {
			 * conditions.append(" `idLocalidad` = " + hermano.getIdLocalidad()
			 * + " AND"); }
			 */
			if (hermano.getFechaNacimiento() != null) {
				conditions.append(" `fechaNacimiento` = '"
						+ hermano.getFechaNacimiento() + "' AND");
			}
			if (hermano.getTelefonoFijo() != null
					&& !hermano.getTelefonoFijo().isEmpty()) {
				conditions.append(" UPPER(`telefonoFijo`) = '"
						+ hermano.getTelefonoFijo().toUpperCase() + "' AND");
			}
			if (hermano.getTelefonoMovil() != null
					&& !hermano.getTelefonoMovil().isEmpty()) {
				conditions.append(" UPPER(`telefonoMovil`) = '"
						+ hermano.getTelefonoMovil().toUpperCase() + "' AND");
			}
			if (hermano.getEmail() != null && !hermano.getEmail().isEmpty()) {
				conditions.append(" UPPER(email`) = '"
						+ hermano.getEmail().toUpperCase() + "' AND");
			}
			if (hermano.getNombrePadre() != null
					&& !hermano.getNombrePadre().isEmpty()) {
				conditions.append(" UPPER(`nombrePadre`) = '"
						+ hermano.getNombrePadre().toUpperCase() + "' AND");
			}
			if (hermano.getNombreMadre() != null
					&& !hermano.getNombreMadre().isEmpty()) {
				conditions.append(" UPPER(`nombreMadre`) = '"
						+ hermano.getNombreMadre().toUpperCase() + "' AND");
			}
			if (hermano.getNombreTutor() != null
					&& !hermano.getNombreTutor().isEmpty()) {
				conditions.append(" UPPER(`nombreTutor`) = '"
						+ hermano.getNombreTutor().toUpperCase() + "' AND");
			}
			if (hermano.getLugarBautismo() != null
					&& !hermano.getLugarBautismo().isEmpty()) {
				conditions.append(" UPPER(lugarBautismo`) = '"
						+ hermano.getLugarBautismo().toUpperCase() + "' AND");
			}
			if (hermano.getFechaBautismo() != null) {
				conditions.append(" `fechaBautismo` = '"
						+ hermano.getFechaBautismo() + "' AND");
			}
			if (hermano.getProfesion() != null
					&& !hermano.getProfesion().isEmpty()) {
				conditions.append(" UPPER(`profesion`) = '"
						+ hermano.getProfesion().toUpperCase() + "' AND");
			}
			if (hermano.getLibro() != null && !hermano.getLibro().isEmpty()) {
				conditions.append(" UPPER(`libro`) = '"
						+ hermano.getLibro().toUpperCase() + "' AND");
			}
			if (hermano.getFechaSolicitud() != null) {
				conditions.append(" `fechaSolicitud` = '"
						+ hermano.getFechaSolicitud() + "' AND");
			}
			if (hermano.getFechaJura() != null) {
				conditions.append(" `fechaJura` = '" + hermano.getFechaJura()
						+ "' AND");
			}
			if (hermano.getFechaBaja() != null) {
				conditions.append(" `fechaBaja` = '" + hermano.getFechaBaja()
						+ "' AND");
			}
			if (hermano.getMotivoBaja() != null
					&& !hermano.getMotivoBaja().isEmpty()) {
				conditions.append(" UPPER(`motivoBaja`) = '"
						+ hermano.getMotivoBaja().toUpperCase() + "' AND");
			}
			if (hermano.getIdCalleCobro() != null) {
				conditions.append(" UPPER(hermano.`idCalleCobro`) = '"
						+ hermano.getIdCalleCobro() + "' AND");
			}
			/*if (hermano.getIdTipoViaCobro() != null) {
				conditions.append(" UPPER(hermano.`idTipoViaCobro`) = '"
						+ hermano.getIdTipoViaCobro() + "' AND");
			}
			if (hermano.getNombreViaCobro() != null
					&& !hermano.getNombreViaCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`nombreViaCobro`) = '"
						+ hermano.getNombreViaCobro().toUpperCase() + "' AND");
			}*/
			if (hermano.getNumKmCobro() != null
					&& !hermano.getNumKmCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`numKmCobro`) = '"
						+ hermano.getNumKmCobro().toUpperCase() + "' AND");
			}
			if (hermano.getBloqueCobro() != null
					&& !hermano.getBloqueCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`bloqueCobro`) = '"
						+ hermano.getBloqueCobro().toUpperCase() + "' AND");
			}
			if (hermano.getEscaleraCobro() != null
					&& !hermano.getEscaleraCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`escaleraCobro`) = '"
						+ hermano.getEscaleraCobro().toUpperCase() + "' AND");
			}
			if (hermano.getPisoCobro() != null
					&& !hermano.getPisoCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`pisoCobro`) = '"
						+ hermano.getPisoCobro().toUpperCase() + "' AND");
			}
			if (hermano.getPuertaCobro() != null
					&& !hermano.getPuertaCobro().isEmpty()) {
				conditions.append(" UPPER(hermano.`puertaCobro`) = '"
						+ hermano.getPuertaCobro().toUpperCase() + "' AND");
			}
			/*if (hermano.getIdLocalidadCobro() != null) {
				conditions.append(" hermano.`idLocalidadCobro` = "
						+ hermano.getIdLocalidadCobro() + " AND");
			}*/
			if (hermano.getObservaciones() != null
					&& !hermano.getObservaciones().isEmpty()) {
				conditions.append(" UPPER(hermano.`observaciones`) = '"
						+ hermano.getObservaciones().toUpperCase() + "' AND");
			}
			if (hermano.getIdMotivoBaja() != null) {
				conditions.append(" hermano.`idMotivoBaja` = "
						+ hermano.getIdMotivoBaja() + " AND");
			}

			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}

			queryString.append(conditions);

			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("hermano", HermanoVO.class);
			query.executeUpdate();

			// Actualizamos el numero de hermano para todos los hermanos
			// posteriores al que se ha borrado
			// si el hermano no estaba ya dado de baja
			if (hermano.getNumHermano() != null
					&& hermano.getNumHermano().compareTo(new Long(0)) > 0) {
				this.updateBrotherNumber(currentTransaction, hermano, null);
			}

			if (transaction == null) {
				currentTransaction.commit();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								"Elemento borrado correctamente"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error borrando el elemento", ""));
			}
		}
	}

	public void cancelElement(Transaction transaction, HermanoVO hermano)
			throws DaoException {
		session = ConfHibernate.getSessionFactory().getCurrentSession();
		String schema = ((SessionFactoryImplementor) ConfHibernate
				.getSessionFactory()).getSettings().getDefaultSchemaName();
		Transaction currentTransaction = transaction;

		try {
			if (session.getTransaction() == null
					|| !session.getTransaction().isActive()) {
				currentTransaction = session.beginTransaction();
			} else if (session.getTransaction().isActive()) {
				currentTransaction = session.getTransaction();
			}

			if (hermano == null) {
				throw new Exception("Element is null");
			}

			if (schema == null) {
				schema = "";
			} else {
				schema += ".";
			}

			StringBuilder queryString = new StringBuilder(
					"SELECT hermano.* FROM " + schema + "HERMANO hermano ");
			StringBuilder conditions = new StringBuilder();
			if (hermano.getId() != null) {
				conditions.append(" id = " + hermano.getId() + " AND");
			}

			if (conditions.length() > 0) {
				conditions = new StringBuilder(" WHERE "
						+ conditions.substring(0, conditions.length() - 3));
			}

			queryString.append(conditions);

			SQLQuery query = session.createSQLQuery(queryString.toString());
			query.addEntity("hermano", HermanoVO.class);
			HermanoVO item = (HermanoVO) query.uniqueResult();

			item.setFechaBaja(hermano.getFechaBaja());
			item.setMotivoBaja(hermano.getMotivoBaja());
			item.setNumHermano(new Long(0));

			session.merge(item);

			// Actualizamos el numero de hermano para todos los hermanos
			// posteriores al que se ha borrado
			if (hermano.getNumHermano() != null
					&& hermano.getNumHermano().compareTo(new Long(0)) > 0) {
				this.updateBrotherNumber(currentTransaction, hermano, null);
			}

			if (transaction == null) {
				currentTransaction.commit();

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								"Elemento actualizado correctamente"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			if (transaction == null) {
				currentTransaction.rollback();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error dando de baja al hermano", ""));
				throw new DaoException(ex.getMessage());
			}
		}
	}
}