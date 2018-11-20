package com.infoCofrade.administration.secretaria.calleView;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.localidad.bo.LocalidadBO;
import com.infoCofrade.administration.localidad.vo.LocalidadVO;
import com.infoCofrade.administration.provincia.bo.ProvinciaBO;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;
import com.infoCofrade.administration.secretaria.calleView.bo.CalleViewBO;
import com.infoCofrade.administration.secretaria.calleView.vo.CalleViewVO;
import com.infoCofrade.common.bean.AbstractBean;

@Named
@Scope("session")
public class CalleViewBean extends AbstractBean {
	private static final long serialVersionUID = 1L;

	private CalleViewVO calleView;
	private CalleViewVO calleViewFilter;

	@Inject
	private CalleViewBO calleViewBO;
	@Inject
	private LocalidadBO localidadBO;
	@Inject
	private ProvinciaBO provinciaBO;

	private List<CalleViewVO> listCalleView;

	private List<CalleViewVO> listSearchCalle;
	private List<CalleViewVO> listFilteredCalles;
	private List<CalleViewVO> listSearchCalleCobro;
	private List<CalleViewVO> listFilteredCallesCobro;

	private LocalidadVO localidadSelected;

	private static final String listPageName = "listCalleView";
	private static final String editPageName = "edit";

	public CalleViewBean() {
		this.calleView = new CalleViewVO();
		this.calleViewFilter = new CalleViewVO();
	}

	public void init() {
		super.init();

		this.calleView = new CalleViewVO();
		this.calleViewFilter = new CalleViewVO();

		this.localidadSelected = new LocalidadVO();

		this.createListCalleView();
		this.createListSearchCalle();
		this.createListSearchCalleCobro();
	}

	public void clear() {
		super.init();

		this.calleView = new CalleViewVO();
		this.calleViewFilter = new CalleViewVO();

		this.localidadSelected = new LocalidadVO();
	}

	// ------------------------------- ACTIONS
	// ----------------------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * 
	 * @return String value of the destiny page
	 */
	public String doNavigate() {
		this.init();

		return listPageName;
	}

	/**
	 * Find calleView elements using the filter get.
	 */
	public void doAddFilter() {
		listCalleView = this.calleViewBO.findUsingTemplate(null,
				calleViewFilter, null);
	}

	/**
	 * Find calleView elements using filter by the ValueChangeEvent
	 * 
	 * @param event
	 *            contains the behaviour of the step
	 */
	public void doAddFilter(ValueChangeEvent event) {
		if (event.getNewValue() != null) {
			if (event.getComponent().getAttributes().get("idLocalidad") != null
					&& Boolean.parseBoolean((String) event.getComponent()
							.getAttributes().get("idLocalidad"))) {
				this.calleView.setIdLocalidad((Long) event.getNewValue());
			}

			listCalleView = this.calleViewBO.findUsingTemplate(null, calleView,
					null);
		}
	}

	/**
	 * Clear the calleView filter of the list page
	 */
	public void doClearFilter() {
		this.init();
	}

	/**
	 * Navigate to the edition page
	 * 
	 * @return String destiny mapped page from the calleView edition
	 */
	public String doEditElement() {
		String navigateString = editPageName;
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap()
				.get("edition");
		this.clear();

		if (id != null) {
			calleView = this.calleViewBO.findByPrimaryKey(null,
					Long.valueOf(id));

			if (edition.equalsIgnoreCase("edition")) {
				if (calleView.getIdLocalidad() != null) {
					LocalidadVO localidad = localidadBO.findByPrimaryKey(null,
							calleView.getIdLocalidad());
					ProvinciaVO provincia = provinciaBO.findByPrimaryKey(null,
							localidad.getIdProvincia());

					calleView.setCodigoPostal(localidad.getCodigoPostal());
					calleView.setProvincia(provincia.getProvincia());

					this.setLocalidadSelected(localidad);
				}
			}

			this.createListSearchCalle();
			this.createListSearchCalleCobro();
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
			calleView.setIdLocalidad(localidadSelected.getId());
		}
		calleView.setCodigoPostal(localidadSelected.getCodigoPostal());
		calleView.setProvincia(provincia.getProvincia());
	}

	/**
	 * Carga la lista embebida de provincias para añadirlas a una localidad
	 */
	public void loadEmbeddedList() {
		this.calleViewFilter = new CalleViewVO();

		this.createListCalleView();
	}

	// ------------------- ADITIONAL FUNCTIONS --------------------
	private void createListCalleView() {
		listCalleView = this.calleViewBO.findAll(null, null);
	}

	private void createListSearchCalle() {
		listSearchCalle = this.calleViewBO.findAll(null, "`nombreVia`");
	}
	
	private void createListSearchCalleCobro() {
		listSearchCalleCobro = this.calleViewBO.findAll(null, "`nombreVia`");
	}

	// ------------------- GETTERS / SETTERS -------------------
	public CalleViewVO getCalleView() {
		return calleView;
	}

	public void setCalleView(CalleViewVO calleView) {
		this.calleView = calleView;
	}

	public CalleViewVO getCalleViewFilter() {
		return calleViewFilter;
	}

	public void setCalleViewFilter(CalleViewVO calleViewFilter) {
		this.calleViewFilter = calleViewFilter;
	}

	public CalleViewBO getCalleViewBO() {
		return calleViewBO;
	}

	public void setCalleViewBO(CalleViewBO calleViewBO) {
		this.calleViewBO = calleViewBO;
	}

	public void setListCalleView(List<CalleViewVO> listCalleView) {
		this.listCalleView = listCalleView;
	}

	public List<CalleViewVO> getListCalleView() {
		if (listCalleView == null) {
			this.createListCalleView();
		}

		return listCalleView;
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
	
	public void setListFilteredCallesCobro(List<CalleViewVO> listFilteredCallesCobro) {
		this.listFilteredCallesCobro = listFilteredCallesCobro;
	}

	public List<CalleViewVO> getListFilteredCallesCobro() {
		return listFilteredCallesCobro;
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
}