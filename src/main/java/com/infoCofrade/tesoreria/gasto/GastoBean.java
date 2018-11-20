package com.infoCofrade.tesoreria.gasto;

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
import com.infoCofrade.tesoreria.gasto.bo.GastoBO;
import com.infoCofrade.tesoreria.gasto.vo.GastoVO;


@Named
@Scope("session")
public class GastoBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private GastoVO gasto;
	private GastoVO gastoFilter;
	
	@Inject
	private GastoBO gastoBO;
	@Inject
	private CuentaBO cuentaBO;
	
	private List<GastoVO> listGasto;
	
	private List<CuentaVO> listSearchCuenta;
	private List<CuentaVO> listFilteredCuentas;
	
	private static final String listPageName = "listGasto";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	private static final String editPageFromExternalServlet = "editGasto";
	
	private String returnPageName = null;
	private Long idCuentaSelected = null;
	
	
	public GastoBean(){
		this.gasto = new GastoVO();
		this.gastoFilter = new GastoVO();
	}
	

	public void init(){
		super.init();
		
		this.gasto = new GastoVO();
		this.gastoFilter = new GastoVO();
		this.idCuentaSelected = null;
		
		this.createListGasto();
		this.createListSearchCuenta();
	}
	
	public void clear(){
		super.init();
		
		this.gasto = new GastoVO();
		this.gastoFilter = new GastoVO();
		this.idCuentaSelected = null;
		
		this.listFilteredCuentas = null;
	}
	
	//------------------------------- ACTIONS ----------------------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * @return String value of the destiny page
	 */
	public String doNavigate(){
		this.init();
		
		// Ponemos la pagina de retorno a nulo para que no vuelva desde la edicion a otra pagina que
		// no sea el listado de gastos
		this.returnPageName = null;
		
		return listPageName;
	}
	
	/**
	 * Find gasto elements using the filter get.
	 */
	public void doAddFilter(){
		listGasto = this.gastoBO.findUsingTemplate(null, gastoFilter, null);
	}
	
	/**
	 * Clear the gasto filter of the list page
	 */
	public void doClearFilter(){
		this.init();
	}
	
	/**
	 * Add a new element into the database
	 * @return String value of the destiny page
	 */
	public String doCreateElement(){
		String navigateString = editPageName;
		
		if(validate()){
			navigateString = listPageName;	
			
			try{
				this.gastoBO.createElement(null, gasto);
				
				this.init();
			} catch (DaoException e){
				navigateString = editPageName;
			}
		}
		
		if(returnPageName != null && !navigateString.equalsIgnoreCase(editPageName)){
			CuentaBean cuentaBean = (CuentaBean)this.getBean("cuentaBean");
			return cuentaBean.doNavigate();
		} else {
			return navigateString;	
		}
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the gasto edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			gasto = this.gastoBO.findByPrimaryKey(null, Long.valueOf(id));
			
			this.createListSearchCuenta();
		}

		if (edition != null) {
			if(edition.equalsIgnoreCase(editPageName)){
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
	 * @return String destiny mapped page from the gasto edition
	 */
	public String doAddGastoCuenta() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String idCuenta = fc.getExternalContext().getRequestParameterMap().get("id");
		Integer anyo = Integer.valueOf(fc.getExternalContext().getRequestParameterMap().get("anyo"));
		String edition = fc.getExternalContext().getRequestParameterMap()
				.get("edition");
		this.clear();
		
		this.idCuentaSelected = Long.valueOf(idCuenta);
		this.returnPageName = fc.getExternalContext().getRequestParameterMap().get("returnPage");
		
		this.gasto = new GastoVO();
		this.gasto.setIdCuenta(this.idCuentaSelected);
		this.gasto.setAnyo(anyo);

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
	 * @return String value of the destiny page
	 */
	public void doDeleteElement(){
		GastoVO gasto = new GastoVO();
		gasto.setId(this.selectedDeleteItemId);
		
		this.gastoBO.deleteElement(null, gasto);
		
		this.createListGasto();
	}
	
	/**
	 * Carga la lista embebida de cuentas para asociarla al gasto
	 */
	public void loadEmbeddedList() {
		this.createListSearchCuenta();
	}

	public String doSelectCuenta() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String idCuenta = fc.getExternalContext().getRequestParameterMap()
				.get("id");
		this.gasto.setIdCuenta(Long.valueOf(idCuenta));

		return null;
	}

	/**
	 * Eliminar el id de cuenta asociado al gasto
	 */
	public String clearIdCuenta() {
		this.gasto.setIdCuenta(null);
		this.createListSearchCuenta();
		this.listFilteredCuentas = null;

		return null;
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListGasto(){
		listGasto = this.gastoBO.findAll(null, null);
	}
	
	private void createListSearchCuenta() {
		listSearchCuenta = this.cuentaBO.findAll(null, "`nivel`, `nombre`");
	}
	
	private Boolean validate(){
		Boolean value = true;
		
		/*if (this.gasto.getFechaEntrada() != null && this.gasto.getFechaSalida() != null){
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
							"La gasto puede ser de entrada o de salida, por tanto no puede tener las dos fechas asignadas"));
			
			value = false;
		}*/
		
		return value;
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public GastoVO getGasto() {
		return gasto;
	}

	public void setGasto(GastoVO gasto) {
		this.gasto = gasto;
	}

	public GastoVO getGastoFilter() {
		return gastoFilter;
	}

	public void setGastoFilter(GastoVO gastoFilter) {
		this.gastoFilter = gastoFilter;
	}

	public GastoBO getGastoBO() {
		return gastoBO;
	}

	public void setGastoBO(GastoBO gastoBO) {
		this.gastoBO = gastoBO;
	}

	public void setListGasto(List<GastoVO> listGasto) {
		this.listGasto = listGasto;
	}

	public List<GastoVO> getListGasto(){
		if(listGasto == null){
			this.createListGasto();
		}
		
		return listGasto;
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