package com.infoCofrade.mayordomia.cuotaHermano;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.mayordomia.cuotaHermano.bo.CuotaHermanoBO;
import com.infoCofrade.mayordomia.cuotaHermano.vo.CuotaHermanoVO;
import com.infoCofrade.tesoreria.cuenta.bo.CuentaBO;
import com.infoCofrade.tesoreria.cuenta.vo.CuentaVO;

@Named
@Scope("session")
public class CuotaHermanoBean extends AbstractBean {
	private static final long serialVersionUID = 1L;

	private CuotaHermanoVO cuotaHermano;
	private CuotaHermanoVO cuotaHermanoFilter;

	@Inject
	private CuotaHermanoBO cuotaHermanoBO;
	@Inject
	private CuentaBO cuentaBO;

	private List<CuotaHermanoVO> listCuotaHermano;
	
	private List<CuentaVO> listSearchCuenta;
	private List<CuentaVO> listFilteredCuentas;

	private static final String listPageName = "listCuotaHermano";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";

	public CuotaHermanoBean() {
		this.cuotaHermano = new CuotaHermanoVO();
		this.cuotaHermanoFilter = new CuotaHermanoVO();
	}

	public void init() {
		super.init();

		this.cuotaHermano = new CuotaHermanoVO();
		this.cuotaHermanoFilter = new CuotaHermanoVO();

		this.createListCuotaHermano();
	}

	public void clear() {
		super.init();

		this.cuotaHermano = new CuotaHermanoVO();
		this.cuotaHermanoFilter = new CuotaHermanoVO();
		
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

		return listPageName;
	}

	/**
	 * Find cuotaHermano elements using the filter get.
	 */
	public void doAddFilter() {
		listCuotaHermano = this.cuotaHermanoBO.findUsingTemplate(null,
				cuotaHermanoFilter, null);
	}

	/**
	 * Clear the cuotaHermano filter of the list page
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
				this.cuotaHermanoBO.createElement(null, cuotaHermano);

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
	 * @return String destiny mapped page from the cuotaHermano edition
	 */
	public String doEditElement() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap()
				.get("edition");
		this.clear();

		if (id != null) {
			cuotaHermano = this.cuotaHermanoBO.findByPrimaryKey(null,
					Long.valueOf(id));
		}

		if (edition != null && edition.equalsIgnoreCase("edition")) {
			return editPageName;
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
		CuotaHermanoVO cuotaHermano = new CuotaHermanoVO();
		cuotaHermano.setId(this.selectedDeleteItemId);

		this.cuotaHermanoBO.deleteElement(null, cuotaHermano);

		this.createListCuotaHermano();
	}
	
	/**
	 * Carga la lista embebida de cuentas para asociar una de ellas a la cuota
	 */
	public void loadEmbeddedList() {
		this.createListSearchCuenta();
	}

	public String doSelectCuentaAsociada() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String idCuenta = fc.getExternalContext().getRequestParameterMap()
				.get("id");
		cuotaHermano.setIdCuenta(Long.valueOf(idCuenta));

		return null;
	}

	/**
	 * Eliminar el id de cuenta asociada a la cuota de hermano
	 */
	public String clearIdCuentaAsociada() {
		this.cuotaHermano.setIdCuenta(null);
		this.createListSearchCuenta();
		this.listFilteredCuentas = null;

		return null;
	}

	// ---------------------- ADITIONAL FUNCTIONS ----------------------
	private void createListCuotaHermano() {
		listCuotaHermano = this.cuotaHermanoBO.findAll(null, "`edadMinima`");
	}

	private Boolean validate() {
		Boolean value = true;

		if (this.cuotaHermano.getEdadMinima() != null &&
				this.cuotaHermano.getEdadMaxima() != null &&
				this.cuotaHermano.getEdadMaxima().compareTo(this.cuotaHermano.getEdadMinima()) < 0){
			FacesContext.getCurrentInstance().addMessage(null, new
					FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
							"La edad mínima no puede ser mayor que la edad máxima"
							));
		
			value = false;
		}
		if (this.cuotaHermano.getEdadMinima() != null &&
				this.cuotaHermano.getEdadMaxima() != null){
			CuotaHermanoVO aux = new CuotaHermanoVO();
			aux.setId(this.getCuotaHermano().getId());
			aux.setEdadMinima(this.getCuotaHermano().getEdadMinima());
			aux.setEdadMaxima(this.getCuotaHermano().getEdadMaxima());
			List<CuotaHermanoVO> listAux = this.cuotaHermanoBO.findEmbeddedItem(null, aux, null);
			if(listAux.size() > 0){
				FacesContext.getCurrentInstance().addMessage(null, new
						FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
								"El rango de edad ya está contenido en otra cuota de hermano creada"));
				
				value = false;
			}
		}

		return value;
	}
	
	private void createListSearchCuenta() {
		listSearchCuenta = this.cuentaBO.findAll(null, "`nombre`");
	}

	// --------------------- GETTERS / SETTERS ---------------------
	public CuotaHermanoVO getCuotaHermano() {
		return cuotaHermano;
	}

	public void setCuotaHermano(CuotaHermanoVO cuotaHermano) {
		this.cuotaHermano = cuotaHermano;
	}

	public CuotaHermanoVO getCuotaHermanoFilter() {
		return cuotaHermanoFilter;
	}

	public void setCuotaHermanoFilter(CuotaHermanoVO cuotaHermanoFilter) {
		this.cuotaHermanoFilter = cuotaHermanoFilter;
	}

	public CuotaHermanoBO getCuotaHermanoBO() {
		return cuotaHermanoBO;
	}

	public void setCuotaHermanoBO(CuotaHermanoBO cuotaHermanoBO) {
		this.cuotaHermanoBO = cuotaHermanoBO;
	}

	public void setListCuotaHermano(List<CuotaHermanoVO> listCuotaHermano) {
		this.listCuotaHermano = listCuotaHermano;
	}

	public List<CuotaHermanoVO> getListCuotaHermano() {
		if (listCuotaHermano == null) {
			this.createListCuotaHermano();
		}

		return listCuotaHermano;
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
}