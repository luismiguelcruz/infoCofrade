package com.infoCofrade.secretaria.hermano;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.administration.provincia.bo.ProvinciaBO;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.administration.secretaria.calle.vo.CalleVO;
import com.infoCofrade.administration.secretaria.calleView.bo.CalleViewBO;
import com.infoCofrade.administration.secretaria.calleView.vo.CalleViewVO;
import com.infoCofrade.administration.secretaria.status.StatusBean;
import com.infoCofrade.administration.secretaria.status.bo.StatusBO;
import com.infoCofrade.administration.secretaria.status.vo.StatusVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.hermano.bo.HermanoBO;
import com.infoCofrade.secretaria.hermano.vo.HermanoVO;
import com.infoCofrade.secretaria.statusPorHermano.bo.StatusPorHermanoBO;
import com.infoCofrade.secretaria.statusPorHermano.vo.StatusPorHermanoVO;

@Named
@Scope("session")
public class HermanoBean extends AbstractBean {
	private static final long serialVersionUID = 1L;

	private HermanoVO hermano;
	private HermanoVO hermanoFilter;

	@Inject
	private HermanoBO hermanoBO;
	@Inject
	private CalleViewBO calleViewBO;
	@Inject
	private ProvinciaBO provinciaBO;
	@Inject
	private StatusPorHermanoBO statusPorHermanoBO;

	private List<HermanoVO> listHermano;
	private List<StatusVO> listStatusPorHermano;
	private List<StatusVO> listUnselectedStatus;
	private List<CalleViewVO> listSearchCalle;
	private List<CalleViewVO> listFilteredCalles;
	private List<CalleViewVO> listSearchCalleCobro;
	private List<CalleViewVO> listFilteredCallesCobro;

	private CalleVO calleSelected;
	private LocalidadVO localidadSelected;
	private LocalidadVO localidadCobroSelected;

	private static final String listPageName = "listHermano";
	private static final String listPageBajaName = "listHermanoBaja";
	private static final String editPageName = "edit";
	private static final String editPageBajaName = "editBaja";
	private static final String cancelPageName = "cancel";

	private Boolean showActive = null;

	/**
	 * Controla si se va a admitir de nuevo a un hermano que estaba dado de
	 * baja, tomando el numero de hermano último de la lista de hermanos
	 */
	private Boolean reActivateBrother = null;

	public HermanoBean() {
		this.hermano = new HermanoVO();
		this.hermanoFilter = new HermanoVO();
	}

	public void init() {
		super.init();

		this.hermano = new HermanoVO();
		this.hermanoFilter = new HermanoVO();
		this.calleSelected = new CalleVO();
		this.localidadSelected = new LocalidadVO();
		this.localidadCobroSelected = new LocalidadVO();

		this.reActivateBrother = null;

		this.createListHermano();
		this.listStatusPorHermano = new LinkedList<StatusVO>();
		this.listUnselectedStatus = new LinkedList<StatusVO>();
	}

	public void clear() {
		super.init();

		this.hermano = new HermanoVO();
		this.hermanoFilter = new HermanoVO();
		this.calleSelected = new CalleVO();
		this.localidadSelected = new LocalidadVO();
		this.localidadCobroSelected = new LocalidadVO();

		this.reActivateBrother = null;
		this.listStatusPorHermano = new LinkedList<StatusVO>();
		this.listUnselectedStatus = new LinkedList<StatusVO>();
	}

	// --------------------- ACTIONS ---------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * 
	 * @return String value of the destiny page
	 */
	public String doNavigate() {
		this.clear();
		this.showActive = new Boolean(true);

		this.createListHermano();

		return listPageName;
	}

	/**
	 * Initialize the values where the list page is showed from menu
	 * 
	 * @return String value of the destiny page
	 */
	public String doNavigateBaja() {
		this.clear();
		this.showActive = new Boolean(false);

		this.createListHermano();

		return listPageBajaName;
	}

	/**
	 * Find hermano elements using the filter get.
	 */
	public void doAddFilter() {
		listHermano = this.hermanoBO.findUsingTemplate(null, hermanoFilter,
				null, showActive);
	}

	/**
	 * Clear the hermano filter of the list page
	 */
	public void doClearFilter() {
		this.init();
	}

	/**
	 * Add a new element into the database
	 * 
	 * @return String value of the destiny page
	 */
	public String doCreateElement() {
		String navigateString = editPageName;

		if (validate()) {
			navigateString = listPageName;
			try {
				if (hermano.getId() == null
						|| (hermano.getFechaBaja() != null && reActivateBrother)) {
					Long numHermano = this.hermanoBO.findLastBrother(null);
					if (numHermano == null) {
						numHermano = new Long(1);
						this.hermano.setNumIngreso(numHermano);
					} else {
						numHermano++;
						this.hermano.setNumIngreso(numHermano);
					}

					this.hermano.setNumHermano(numHermano);
				}

				/*this.hermano.setIdLocalidad(null);
				if (localidadSelected != null
						&& localidadSelected.getId() != null) {
					this.hermano.setIdLocalidad(localidadSelected.getId());
				}
				this.hermano.setIdLocalidadCobro(null);
				if (localidadCobroSelected != null
						&& localidadCobroSelected.getId() != null) {
					this.hermano.setIdLocalidadCobro(localidadCobroSelected
							.getId());
				}*/

				// Si readmitimos un hermano, eliminamos su fecha de baja
				if (hermano.getFechaBaja() != null && reActivateBrother) {
					this.hermano.setFechaBaja(null);
					this.hermano.setMotivoBaja(null);
				}

				this.hermanoBO.createElement(null, hermano,
						listStatusPorHermano);

				// Volvemos a mostrar todos los hermanos activos una vez que
				// hemos
				// reactivado el hermano
				this.showActive = true;

				this.init();
			} catch (DaoException e) {
				navigateString = editPageName;
			}
		}

		return navigateString;
	}

	/**
	 * Navigate to the edition page
	 * 
	 * @return String destiny mapped page from the hermano edition
	 */
	public String doEditElement() {
		String navigateString = editPageName;
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap()
				.get("edition");
		this.clear();

		if (id != null) {
			hermano = this.hermanoBO.findByPrimaryKey(null, Long.valueOf(id));

			// Calculamos el código postal y la provincia de la localidad que se
			// ha seleccionado
			if (edition.equalsIgnoreCase("edition")
					|| edition.equalsIgnoreCase("editionBaja")) {
				/*
				 * if (hermano.getIdLocalidad() != null) { LocalidadVO localidad
				 * = localidadBO.findByPrimaryKey(null,
				 * hermano.getIdLocalidad()); ProvinciaVO provincia =
				 * provinciaBO.findByPrimaryKey(null,
				 * localidad.getIdProvincia());
				 * 
				 * hermano.setCodigoPostal(localidad.getCodigoPostal());
				 * hermano.setProvincia(provincia.getProvincia());
				 * 
				 * this.setLocalidadSelected(localidad); }
				 */
				if (hermano.getIdCalle() != null) {
					CalleViewVO calle = this.calleViewBO.findByPrimaryKey(null,
							Long.valueOf(hermano.getIdCalle()));
					hermano.setIdTipoVia(calle.getIdTipoVia());
					hermano.setNombreVia(calle.getNombreVia());
					hermano.setIdLocalidad(calle.getIdLocalidad());
					localidadSelected.setId(calle.getIdLocalidad());
					hermano.setCodigoPostal(calle.getCodigoPostal());
					hermano.setProvincia(calle.getProvincia());
					hermano.setIdSector(calle.getIdSector());
				}

				/*if (hermano.getIdLocalidadCobro() != null) {
					LocalidadVO localidad = localidadBO.findByPrimaryKey(null,
							hermano.getIdLocalidadCobro());
					ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null,
							localidad.getIdProvincia());

					hermano.setCodigoPostalCobro(localidad.getCodigoPostal());
					hermano.setProvinciaCobro(provincia.getProvincia());

					this.setLocalidadCobroSelected(localidad);
				}*/
				
				if (hermano.getIdCalleCobro() != null) {
					CalleViewVO calle = this.calleViewBO.findByPrimaryKey(null,
							Long.valueOf(hermano.getIdCalleCobro()));
					hermano.setIdTipoViaCobro(calle.getIdTipoVia());
					hermano.setNombreViaCobro(calle.getNombreVia());
					hermano.setIdLocalidadCobro(calle.getIdLocalidad());
					localidadCobroSelected.setId(calle.getIdLocalidad());
					hermano.setCodigoPostalCobro(calle.getCodigoPostal());
					hermano.setProvinciaCobro(calle.getProvincia());
				}
			}

			// Calculamos los status que tiene asociado el hermano en cuestion
			StatusPorHermanoVO statusPorHermanoFilter = new StatusPorHermanoVO();
			statusPorHermanoFilter.setIdHermano(hermano.getId());
			List<StatusPorHermanoVO> listAux = statusPorHermanoBO
					.findUsingTemplate(null, statusPorHermanoFilter, null);

			if (listAux.size() > 0) {
				StatusVO statusAux = null;
				for (StatusPorHermanoVO item : listAux) {
					statusAux = new StatusVO();
					statusAux.setId(item.getIdStatus());

					listStatusPorHermano.add(statusAux);
				}
			}
		}
		
		this.createListSearchCalle();
		this.createListSearchCalleCobro();
		this.listFilteredCalles = null;
		this.listFilteredCallesCobro = null;

		if (edition != null) {
			if (edition.equalsIgnoreCase("cancel")) {
				navigateString = cancelPageName;
			} else if (edition.equalsIgnoreCase("editionBaja")) {
				navigateString = editPageBajaName;
			}
		}

		return navigateString;
	}

	/**
	 * Delete an element from database
	 * 
	 * @return String value of the destiny page
	 */
	public void doDeleteElement() {
		HermanoVO hermano = new HermanoVO();
		hermano.setId(this.selectedDeleteItemId);

		this.hermanoBO.deleteElement(null, hermano);

		this.createListHermano();
	}

	/**
	 * Da de baja a un usuario
	 * 
	 * @return String value of the destiny page
	 */
	public String doCancelElement() {
		String navigateString = listPageName;

		try {
			this.hermanoBO.cancelElement(null, hermano);

			// Limpiamos los valores de hermano seleccionados
			this.init();
		} catch (DaoException e) {
			navigateString = editPageName;
		}

		return navigateString;
	}

	/**
	 * Para una localidad seleccionada, carga el provincia y el codigo postal
	 * para mostrarlos en el formulario
	 */
	public void loadAddressFields() {
		ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null,
				localidadSelected.getIdProvincia());

		if (localidadSelected.getId() != null) {
			hermano.setIdLocalidad(localidadSelected.getId());
		}
		hermano.setCodigoPostal(localidadSelected.getCodigoPostal());
		hermano.setProvincia(provincia.getProvincia());
	}

	public void loadAddressCobroFields() {
		ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null,
				localidadCobroSelected.getIdProvincia());

		if (localidadSelected.getId() != null) {
			hermano.setIdLocalidad(localidadCobroSelected.getId());
		}
		hermano.setCodigoPostalCobro(localidadCobroSelected.getCodigoPostal());
		hermano.setProvinciaCobro(provincia.getProvincia());
	}

	/**
	 * Carga la lista de status que no estan asociados al hermano para
	 * mostrarlas en el desplegable
	 */
	public void loadStatusesList() {
		StatusBO statusBO = ((StatusBean) this.getBean("statusBean"))
				.getStatusBO();
		List<StatusVO> listAux = statusBO.findAll(null, null);
		this.listUnselectedStatus = new LinkedList<StatusVO>();

		if (listStatusPorHermano.isEmpty()) {
			listUnselectedStatus = listAux;
		} else {
			Map<Long, StatusVO> mapSelected = new HashMap<Long, StatusVO>();
			for (StatusVO i : listStatusPorHermano) {
				mapSelected.put(i.getId(), i);
			}

			for (StatusVO aux : listAux) {
				if (!mapSelected.containsKey(aux.getId())) {
					listUnselectedStatus.add(aux);
				}
			}
		}
	}

	/**
	 * Añade un status a la lista de status asociada al hermano seleccionado
	 */
	public String doSelectStatus() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap()
				.get("idStatus");
		StatusVO status = new StatusVO();
		status.setId(Long.valueOf(id));

		this.listStatusPorHermano.add(status);

		return null;
	}

	/**
	 * Desvincula un status del hermano en cuestión que se está editando
	 */
	public void doUnlinkStatus() {
		this.listStatusPorHermano.remove(this.selectedDeleteItemId.intValue());
	}

	/**
	 * Desvincula todos los status del hermano seleccionado
	 */
	public String clearListStatus() {
		this.listStatusPorHermano = new LinkedList<StatusVO>();

		return null;
	}

	public String doSelectCalle() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String idCalle = fc.getExternalContext().getRequestParameterMap()
				.get("id");
		CalleViewVO calle = this.calleViewBO.findByPrimaryKey(null,
				Long.valueOf(idCalle));
		hermano.setIdCalle(calle.getId());
		hermano.setIdTipoVia(calle.getIdTipoVia());
		hermano.setNombreVia(calle.getNombreVia());
		hermano.setIdLocalidad(calle.getIdLocalidad());
		localidadSelected.setId(calle.getIdLocalidad());
		hermano.setCodigoPostal(calle.getCodigoPostal());
		hermano.setProvincia(calle.getProvincia());
		hermano.setIdSector(calle.getIdSector());

		return null;
	}

	/**
	 * Eliminar el id de calle asociado al hermano
	 */
	public String clearIdCalle() {
		hermano.setIdCalle(null);
		hermano.setIdTipoVia(null);
		hermano.setNombreVia(null);
		hermano.setIdLocalidad(null);
		hermano.setCodigoPostal(null);
		hermano.setProvincia(null);

		return null;
	}
	
	public String doSelectCalleCobro() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String idCalle = fc.getExternalContext().getRequestParameterMap()
				.get("id");
		CalleViewVO calle = this.calleViewBO.findByPrimaryKey(null,
				Long.valueOf(idCalle));
		hermano.setIdCalleCobro(calle.getId());
		hermano.setIdTipoViaCobro(calle.getIdTipoVia());
		hermano.setNombreViaCobro(calle.getNombreVia());
		hermano.setIdLocalidadCobro(calle.getIdLocalidad());
		localidadCobroSelected.setId(calle.getIdLocalidad());
		hermano.setCodigoPostalCobro(calle.getCodigoPostal());
		hermano.setProvinciaCobro(calle.getProvincia());

		return null;
	}
	
	/**
	 * Eliminar el id de calle de cobro asociado al hermano
	 */
	public String clearIdCalleCobro() {
		hermano.setIdCalleCobro(null);
		hermano.setIdTipoViaCobro(null);
		hermano.setNombreViaCobro(null);
		hermano.setIdLocalidadCobro(null);
		hermano.setCodigoPostalCobro(null);
		hermano.setProvinciaCobro(null);

		return null;
	}
	

	// -------------------- ADITIONAL FUNCTIONS --------------------
	private void createListHermano() {
		listHermano = this.hermanoBO.findAll(null, "`numHermano`",
				this.showActive);
	}

	private Boolean validate() {
		Boolean value = true;

		if (this.hermano.getIdCalle() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error de validación: ",
							"Es necesario asociar una dirección al hermano"));

			value = false;
		}

		return value;
	}

	private void createListSearchCalle() {
		listSearchCalle = this.calleViewBO.findAll(null, "`nombreVia`");
	}
	
	private void createListSearchCalleCobro() {
		listSearchCalleCobro = this.calleViewBO.findAll(null, "`nombreVia`");
	}

	// --------------------- GETTERS / SETTERS ---------------------
	public HermanoVO getHermano() {
		return hermano;
	}

	public void setHermano(HermanoVO hermano) {
		this.hermano = hermano;
	}

	public HermanoVO getHermanoFilter() {
		return hermanoFilter;
	}

	public void setHermanoFilter(HermanoVO hermanoFilter) {
		this.hermanoFilter = hermanoFilter;
	}

	public HermanoBO getHermanoBO() {
		return hermanoBO;
	}

	public void setHermanoBO(HermanoBO hermanoBO) {
		this.hermanoBO = hermanoBO;
	}

	public void setListHermano(List<HermanoVO> listHermano) {
		this.listHermano = listHermano;
	}

	public List<HermanoVO> getListHermano() {
		if (listHermano == null) {
			this.createListHermano();
		}

		return listHermano;
	}
	
	public List<HermanoVO> getListHermanoInforme() {
		this.createListHermano();

		return listHermano;
	}

	public List<StatusVO> getListStatusPorHermano() {
		return listStatusPorHermano;
	}

	public void setListStatusPorHermano(List<StatusVO> listStatusPorHermano) {
		this.listStatusPorHermano = listStatusPorHermano;
	}

	public List<StatusVO> getListUnselectedStatus() {
		return listUnselectedStatus;
	}

	public void setListUnselectedStatus(List<StatusVO> listUnselectedStatus) {
		this.listUnselectedStatus = listUnselectedStatus;
	}
	
	public void setListSearchCalle(List<CalleViewVO> listSearchCalle) {
		this.listSearchCalle = listSearchCalle;
	}

	public List<CalleViewVO> getListSearchCalle() {
		if (listSearchCalle == null) {
			this.createListSearchCalle();
		}

		return listSearchCalle;
	}

	public List<CalleViewVO> getListFilteredCalles() {
		return listFilteredCalles;
	}

	public void setListFilteredCalles(List<CalleViewVO> listFilteredCalles) {
		this.listFilteredCalles = listFilteredCalles;
	}

	public void setListSearchCalleCobro(List<CalleViewVO> listSearchCalleCobro) {
		this.listSearchCalleCobro = listSearchCalleCobro;
	}

	public List<CalleViewVO> getListSearchCalleCobro() {
		if (listSearchCalleCobro == null) {
			this.createListSearchCalleCobro();
		}

		return listSearchCalleCobro;
	}

	public List<CalleViewVO> getListFilteredCallesCobro() {
		return listFilteredCallesCobro;
	}
	
	public void setListFilteredCallesCobro(List<CalleViewVO> listFilteredCallesCobro) {
		this.listFilteredCallesCobro = listFilteredCallesCobro;
	}

	public CalleVO getCalleSelected() {
		return calleSelected;
	}

	public void setCalleSelected(CalleVO calleSelected) {
		this.calleSelected = calleSelected;
	}

	public LocalidadVO getLocalidadSelected() {
		return localidadSelected;
	}

	public void setLocalidadSelected(LocalidadVO localidadSelected) {
		this.localidadSelected = localidadSelected;
	}

	public LocalidadVO getLocalidadCPSelected() {
		return localidadSelected;
	}

	public void setLocalidadCPSelected(LocalidadVO localidadSelected) {
		if (this.localidadSelected == null || localidadSelected != null) {
			this.localidadSelected = localidadSelected;
		}
	}

	public LocalidadVO getLocalidadCobroSelected() {
		return localidadCobroSelected;
	}

	public void setLocalidadCobroSelected(LocalidadVO localidadCobroSelected) {
		this.localidadCobroSelected = localidadCobroSelected;
	}

	public LocalidadVO getLocalidadCobroCPSelected() {
		return localidadCobroSelected;
	}

	public void setLocalidadCobroCPSelected(LocalidadVO localidadCobroSelected) {
		if (this.localidadCobroSelected == null
				|| localidadCobroSelected != null) {
			this.localidadCobroSelected = localidadCobroSelected;
		}
	}

	public Boolean getReActivateBrother() {
		return reActivateBrother;
	}

	public void setReActivateBrother(Boolean reActivateBrother) {
		this.reActivateBrother = reActivateBrother;
	}
}