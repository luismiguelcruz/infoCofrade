package com.infoCofrade.tesoreria.ingreso;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.cuenta.CuentaBean;
import com.infoCofrade.tesoreria.cuenta.bo.CuentaBO;
import com.infoCofrade.tesoreria.cuenta.vo.CuentaVO;
import com.infoCofrade.tesoreria.ingreso.bo.IngresoBO;
import com.infoCofrade.tesoreria.ingreso.vo.IngresoVO;

@Named
@Scope("session")
public class IngresoBean extends AbstractBean {
	private static final long serialVersionUID = 1L;

	private IngresoVO ingreso;
	private IngresoVO ingresoFilter;

	@Inject
	private IngresoBO ingresoBO;
	@Inject
	private CuentaBO cuentaBO;

	private List<IngresoVO> listIngreso;

	private List<CuentaVO> listSearchCuenta;
	private List<CuentaVO> listFilteredCuentas;

	private static final String listPageName = "listIngreso";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	private static final String editPageFromExternalServlet = "editIngreso";

	private String returnPageName = null;
	private Long idCuentaSelected = null;

	public IngresoBean() {
		this.ingreso = new IngresoVO();
		this.ingresoFilter = new IngresoVO();
	}

	public void init() {
		super.init();

		this.ingreso = new IngresoVO();
		this.ingresoFilter = new IngresoVO();
		this.idCuentaSelected = null;

		this.createListIngreso();
		this.createListSearchCuenta();
	}

	public void clear() {
		super.init();

		this.ingreso = new IngresoVO();
		this.ingresoFilter = new IngresoVO();
		this.idCuentaSelected = null;

		this.listFilteredCuentas = null;
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

		// Ponemos la pagina de retorno a nulo para que no vuelva desde la
		// edicion a otra pagina que
		// no sea el listado de gastos
		this.returnPageName = null;

		return listPageName;
	}

	/**
	 * Find ingreso elements using the filter get.
	 */
	public void doAddFilter() {
		listIngreso = this.ingresoBO.findUsingTemplate(null, ingresoFilter,
				null);
	}

	/**
	 * Clear the ingreso filter of the list page
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
				this.ingresoBO.createElement(null, ingreso);

				this.init();
			} catch (DaoException e) {
				navigateString = editPageName;
			}
		}

		if (returnPageName != null
				&& !navigateString.equalsIgnoreCase(editPageName)) {
			CuentaBean cuentaBean = (CuentaBean) this.getBean("cuentaBean");
			return cuentaBean.doNavigate();
		} else {
			return navigateString;
		}
	}

	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the ingreso edition
	 */
	public String doEditElement() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null) {
			ingreso = this.ingresoBO.findByPrimaryKey(null, Long.valueOf(id));

			this.createListSearchCuenta();
		}

		if (edition != null) {
			if (edition.equalsIgnoreCase(editPageName)) {
				return editPageName;
			} else {
				return editPageFromExternalServlet;
			}
		} else {
			return viewPageName;
		}
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the ingreso edition
	 */
	public String doAddIngresoCuenta() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String idCuenta = fc.getExternalContext().getRequestParameterMap().get("id");
		Integer anyo = Integer.valueOf(fc.getExternalContext().getRequestParameterMap().get("anyo"));
		String edition = fc.getExternalContext().getRequestParameterMap()
				.get("edition");
		this.clear();
		
		this.idCuentaSelected = Long.valueOf(idCuenta);
		this.returnPageName = fc.getExternalContext().getRequestParameterMap().get("returnPage");
		
		this.ingreso = new IngresoVO();
		this.ingreso.setIdCuenta(this.idCuentaSelected);
		this.ingreso.setAnyo(anyo);

		if (edition != null) {
			if (edition.equalsIgnoreCase(editPageName)) {
				return editPageName;
			} else {
				return editPageFromExternalServlet;
			}
		} else {
			return viewPageName;
		}
	}

	/**
	 * Delete an element from database
	 * 
	 * @return String value of the destiny page
	 */
	public void doDeleteElement() {
		IngresoVO ingreso = new IngresoVO();
		ingreso.setId(this.selectedDeleteItemId);

		this.ingresoBO.deleteElement(null, ingreso);

		this.createListIngreso();
	}

	/**
	 * Carga la lista embebida de cuentas para asociarla al ingreso
	 */
	public void loadEmbeddedList() {
		this.createListSearchCuenta();
	}

	public String doSelectCuenta() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String idCuenta = fc.getExternalContext().getRequestParameterMap()
				.get("id");
		this.ingreso.setIdCuenta(Long.valueOf(idCuenta));

		return null;
	}

	/**
	 * Eliminar el id de cuenta asociado al ingreso
	 */
	public String clearIdCuenta() {
		this.ingreso.setIdCuenta(null);
		this.createListSearchCuenta();
		this.listFilteredCuentas = null;

		return null;
	}

	// ---------------------- ADITIONAL FUNCTIONS ----------------------
	private void createListIngreso() {
		listIngreso = this.ingresoBO.findAll(null, null);
	}

	private void createListSearchCuenta() {
		listSearchCuenta = this.cuentaBO.findAll(null, "`nivel`, `nombre`");
	}

	private Boolean validate() {
		Boolean value = true;

		/*
		 * if (this.ingreso.getFechaEntrada() != null &&
		 * this.ingreso.getFechaSalida() != null){
		 * FacesContext.getCurrentInstance().addMessage( null, new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
		 * "La ingreso puede ser de entrada o de salida, por tanto no puede tener las dos fechas asignadas"
		 * ));
		 * 
		 * value = false; }
		 */

		return value;
	}

	// ----------------------- GETTERS / SETTERS ------------------------
	public IngresoVO getIngreso() {
		return ingreso;
	}

	public void setIngreso(IngresoVO ingreso) {
		this.ingreso = ingreso;
	}

	public IngresoVO getIngresoFilter() {
		return ingresoFilter;
	}

	public void setIngresoFilter(IngresoVO ingresoFilter) {
		this.ingresoFilter = ingresoFilter;
	}

	public IngresoBO getIngresoBO() {
		return ingresoBO;
	}

	public void setIngresoBO(IngresoBO ingresoBO) {
		this.ingresoBO = ingresoBO;
	}

	public void setListIngreso(List<IngresoVO> listIngreso) {
		this.listIngreso = listIngreso;
	}

	public List<IngresoVO> getListIngreso() {
		if (listIngreso == null) {
			this.createListIngreso();
		}

		return listIngreso;
	}

	public void setListSearchCuenta(List<CuentaVO> listSearchCuenta) {
		this.listSearchCuenta = listSearchCuenta;
	}

	public List<CuentaVO> getListSearchCuenta() {
		if (listSearchCuenta == null) {
			this.createListSearchCuenta();
		}

		return listSearchCuenta;
	}

	public void setListFilteredCuentas(List<CuentaVO> listFilteredCuentas) {
		this.listFilteredCuentas = listFilteredCuentas;
	}

	public List<CuentaVO> getListFilteredCuentas() {
		return listFilteredCuentas;
	}

	public String getReturnPageName() {
		return returnPageName;
	}

	public void setReturnPageName(String returnPageName) {
		this.returnPageName = returnPageName;
	}

	public Long getIdCuentaSelected() {
		return idCuentaSelected;
	}

	public void setIdCuentaSelected(Long idCuentaSelected) {
		this.idCuentaSelected = idCuentaSelected;
	}
}