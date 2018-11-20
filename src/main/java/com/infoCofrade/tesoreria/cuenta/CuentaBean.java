package com.infoCofrade.tesoreria.cuenta;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.tesoreria.cuenta.bo.CuentaBO;
import com.infoCofrade.tesoreria.cuenta.vo.CuentaVO;
import com.infoCofrade.tesoreria.cuenta.vo.LineaCuentaVO;
import com.infoCofrade.tesoreria.gasto.GastoBean;
import com.infoCofrade.tesoreria.gasto.bo.GastoBO;
import com.infoCofrade.tesoreria.gasto.vo.GastoVO;
import com.infoCofrade.tesoreria.ingreso.IngresoBean;
import com.infoCofrade.tesoreria.ingreso.bo.IngresoBO;
import com.infoCofrade.tesoreria.ingreso.vo.IngresoVO;
import com.infoCofrade.tesoreria.tipoActividad.bo.TipoActividadBO;
import com.infoCofrade.tesoreria.tipoActividad.vo.TipoActividadVO;


@Named
@Scope("session")
public class CuentaBean extends AbstractBean {
	private static final long serialVersionUID = 1L;

	private CuentaVO cuenta;
	private CuentaVO cuentaFilter;

	@Inject
	private CuentaBO cuentaBO;
	@Inject
	private TipoActividadBO tipoActividadBO;
	@Inject
	private GastoBO gastoBO;
	@Inject
	private IngresoBO ingresoBO;

	private List<CuentaVO> listCuenta;
	
	private TreeNode listCuentaStructure;

	private List<CuentaVO> listSearchCuenta;
	private List<CuentaVO> listFilteredCuentas;
	
	private Integer anyo;
	
	private LineaCuentaVO selectedLineaCuenta;
	private GastoVO selectedGasto;
	private IngresoVO selectedIngreso;
    
    private Double balanceAnual = null; 

	private static final String listPageName = "listCuenta";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";

	public CuentaBean() {
		this.cuenta = new CuentaVO();
		this.cuentaFilter = new CuentaVO();
	}

	public void init() {
		super.init();

		this.cuenta = new CuentaVO();
		this.cuentaFilter = new CuentaVO();
		this.anyo = null;
		
		this.listCuentaStructure = null;
		this.balanceAnual = null;
		
		this.selectedGasto = null;
		this.selectedIngreso = null;

		this.createListCuenta();
		this.createListSearchCuenta();
	}

	public void clear() {
		super.init();

		this.cuenta = new CuentaVO();
		this.cuentaFilter = new CuentaVO();
		this.anyo = null;
		this.balanceAnual = null;
		
		this.selectedGasto = null;
		this.selectedIngreso = null;
		
		this.listFilteredCuentas = null;
		this.listCuentaStructure = null;
	}

	// ------------------------ ACTIONS ------------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * 
	 * @return String value of the destiny page
	 */
	public String doNavigate() {
		this.init();
		Calendar c = new GregorianCalendar();
		c.setTime(new Date());
		
		this.anyo = c.get(Calendar.YEAR);
		this.generaTreeNode();
        
		return listPageName;
	}

	/**
	 * Find cuenta elements using the filter get.
	 */
	public void doAddFilter() {
		listCuenta = this.cuentaBO.findUsingTemplate(null, cuentaFilter, null);
	}

	/**
	 * Clear the cuenta filter of the list page
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

			if (this.cuenta.getEsConceptoFijo() == null) {
				this.cuenta.setEsConceptoFijo(false);
			} else if (this.cuenta.getEsConceptoFijo()) {
				this.cuenta.setAnyo(null);
			}
			
			calculaNivelCuenta();

			try {
				this.cuentaBO.createElement(null, cuenta);

				this.init();
				
				// Volvemos a generar el árbol de cuentas del año actual
				Calendar c = new GregorianCalendar();
				c.setTime(new Date());
				
				this.anyo = c.get(Calendar.YEAR);
				this.generaTreeNode();
			} catch (DaoException e) {
				navigateString = editPageName;
			}
		}

		return navigateString;
	}

	/**
	 * Navigate to the edition page
	 * 
	 * @return String destiny mapped page from the cuenta edition
	 */
	public String doEditElement() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap()
				.get("edition");
		this.clear();

		if (id != null) {
			cuenta = this.cuentaBO.findByPrimaryKey(null, Long.valueOf(id));
			
			this.createListSearchCuenta();
		}

		if (edition != null && Boolean.valueOf(edition)) {
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
		CuentaVO cuenta = new CuentaVO();
		cuenta.setId(this.selectedDeleteItemId);

		this.cuentaBO.deleteElement(null, cuenta);
		this.generaTreeNode();
	}
	
	/**
	 * Borra un gasto de la base de datos 
	 * @return String value of the destiny page
	 */
	public void doDeleteGasto() {
		GastoVO gasto = new GastoVO();
		gasto.setId(this.selectedDeleteItemId);

		this.gastoBO.deleteElement(null, gasto);
		this.generaTreeNode();
	}
	
	/**
	 * Borra un ingreso de la base de datos 
	 * @return String value of the destiny page
	 */
	public void doDeleteIngreso() {
		IngresoVO ingreso = new IngresoVO();
		ingreso.setId(this.selectedDeleteItemId);

		this.ingresoBO.deleteElement(null, ingreso);
		this.generaTreeNode();
	}
	
	/**
	 * Crea una nueva subcuenta asociada a la cuenta padre
	 */
	public String doAddSubcuenta(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String idTipoActividad = fc.getExternalContext().getRequestParameterMap().get("idTipoActividad");
		String edition = fc.getExternalContext().getRequestParameterMap()
				.get("edition");
		Integer anyoSelected = this.getAnyo();
		this.clear();

		if (id != null && !id.equalsIgnoreCase("null")) {
			CuentaVO cuentaPadre = this.cuentaBO.findByPrimaryKey(null, Long.valueOf(id));
			
			cuenta = new CuentaVO();
			cuenta.setIdCuentaPadre(cuentaPadre.getId());
			cuenta.setAnyo(anyoSelected);
			cuenta.setIdTipoActividad(cuentaPadre.getIdTipoActividad());
			
			this.createListSearchCuenta();
		} else if (idTipoActividad != null && !idTipoActividad.equalsIgnoreCase("null")) {
			cuenta = new CuentaVO();
			cuenta.setAnyo(anyoSelected);
			cuenta.setIdTipoActividad(Long.valueOf(idTipoActividad));
			
			this.createListSearchCuenta();
		}

		if (edition != null && Boolean.valueOf(edition)) {
			return editPageName;
		} else {
			return viewPageName;
		}
	}

	/**
	 * Carga la lista embebida de cuentas para ponerla como padre de la cuenta
	 */
	public void loadEmbeddedList() {
		this.createListSearchCuenta();
	}

	public String doSelectCuentaPadre() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String idCuentaPadre = fc.getExternalContext().getRequestParameterMap()
				.get("id");
		cuenta.setIdCuentaPadre(Long.valueOf(idCuentaPadre));

		return null;
	}

	/**
	 * Eliminar el id de cuenta padre asociado a la cuenta
	 */
	public String clearIdCuentaPadre() {
		this.cuenta.setIdCuentaPadre(null);
		this.createListSearchCuenta();
		this.listFilteredCuentas = null;

		return null;
	}
	
	/**
	 * Genera todas las cuentas existentes para un determinado año seleccionado y sus valores
	 */
	public void doGenerarCuentasAnuales(){
		this.generaTreeNode();
	}
	
	/**
	 * Cambia el concepto y/o la cuantía de un gasto concreto seleccionado en el listado de cuentas
	 */
	public void doChangeGasto(){
		try{
			this.gastoBO.createElement(null, selectedGasto);
			
			this.selectedGasto = null;
			this.generaTreeNode();
		} catch (DaoException e){}
	}
	
	/**
	 * Cambia el concepto y/o la cuantía de un ingreso concreto seleccionado en el listado de cuentas
	 */
	public void doChangeIngreso(){
		try{
			this.ingresoBO.createElement(null, selectedIngreso);
			
			this.selectedIngreso = null;
			this.generaTreeNode();
		} catch (DaoException e){}
	}
	

	// ---------------------- ADITIONAL FUNCTIONS ----------------------
	private void createListCuenta() {
		listCuenta = this.cuentaBO.findAll(null, null);
	}
	
	private void createListSearchCuenta() {
		listSearchCuenta = this.cuentaBO.findAll(null, "`nombre`");
	}

	private Boolean validate() {
		Boolean value = true;

		/*
		 * if (this.cuenta.getFechaEntrada() != null &&
		 * this.cuenta.getFechaSalida() != null){
		 * FacesContext.getCurrentInstance().addMessage( null, new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
		 * "La cuenta puede ser de entrada o de salida, por tanto no puede tener las dos fechas asignadas"
		 * ));
		 * 
		 * value = false; }
		 */

		return value;
	}
	
	/**
	 * Calcula el nivel dentro del arbol de cuentas
	 */
	private void calculaNivelCuenta(){
		if(this.cuenta.getIdCuentaPadre() == null){
			this.cuenta.setNivel(0);
		} else {
			CuentaVO cuentaPadre = this.cuentaBO.findByPrimaryKey(null, this.cuenta.getIdCuentaPadre());
			this.cuenta.setNivel(cuentaPadre.getNivel()+1);
		}
	}
	
	/**
	 * Genera el primer nivel del TreeNode de cuentas para un año determinado
	 */
	private void generaTreeNode(){
		if(this.anyo != null && this.getAnyo() > 0){
			List<TipoActividadVO> listActividades = this.tipoActividadBO.findAll(null, null);
			listCuentaStructure = new DefaultTreeNode("listCuentaStructure", null);
			Double total = null;
			
			for(TipoActividadVO tipoActividad: listActividades){
				LineaCuentaVO lineaCuenta = new LineaCuentaVO();
				lineaCuenta.setNombre(tipoActividad.getNombre());
				lineaCuenta.setIdTipoActividad(tipoActividad.getId());
				TreeNode tree = new DefaultTreeNode(lineaCuenta, listCuentaStructure);
				Double suma = generaTreeNodeRecursivo(tree, tipoActividad.getId(), 0, null);
				if(suma != null){
					if(total == null){
						total = suma;
					} else {
						total += suma;
					}
				}
				
				try{
					// Actualizamos el saldo del padre con la suma del saldo de los hijos
					((LineaCuentaVO)tree.getData()).setSaldo(suma);
				} catch(Exception e){
					
				}
			}
			
			if(total != null){
				balanceAnual = total;
			} else {
				balanceAnual = new Double(0);
			}
		}
	}
	
	/**
	 * Genera el TreeNode recursivo a partir de uno de los nodos padre, su tipo de actividad y el nivel
	 * que deben de tener los hijos generados
	 * @param treeNodeFather
	 * @param idTipoActividad
	 * @param nivel
	 */
	private Double generaTreeNodeRecursivo(TreeNode treeNodeFather, Long idTipoActividad, int nivel, Long idCuenta){
		CuentaVO itemSearch = new CuentaVO();
		itemSearch.setIdCuentaPadre(idCuenta);
		itemSearch.setIdTipoActividad(idTipoActividad);
		itemSearch.setNivel(nivel);
		LineaCuentaVO lineaCuenta = null;
		List<CuentaVO> listAux = this.cuentaBO.findUsingTemplateForLevel(null, itemSearch, null);
		Double total = null;
		
		if(listAux.size() > 0){
			for(CuentaVO iterator: listAux){
				lineaCuenta = new LineaCuentaVO();
				lineaCuenta.setNombre(iterator.getNombre());
				lineaCuenta.setIdCuenta(iterator.getId());
				if((iterator.getEsConceptoFijo() != null && iterator.getEsConceptoFijo()) ||
						iterator.getAnyo() != null && iterator.getAnyo().compareTo(this.anyo) == 0){
					TreeNode tree = new DefaultTreeNode(lineaCuenta, treeNodeFather);
					Double suma = generaTreeNodeRecursivo(tree, idTipoActividad, nivel+1, iterator.getId());
					if(suma != null){
						if(total == null){
							total = suma;
						} else {
							total += suma;
						}
					}
					try{
						// Actualizamos el saldo del padre con la suma del saldo de los hijos
						((LineaCuentaVO)tree.getData()).setSaldo(suma);
					} catch(Exception e){
						
					}
				}
			}
		} if(idCuenta != null){
			// Caso base, añadimos los gastos y los ingresos de esa cuenta
			GastoVO gastoSearch = new GastoVO();
			gastoSearch.setIdCuenta(idCuenta);
			gastoSearch.setAnyo(this.getAnyo());
			List<GastoVO> listGastos = this.gastoBO.findUsingTemplate(null, gastoSearch, null);
			for(GastoVO gasto: listGastos){
				lineaCuenta = new LineaCuentaVO();
				lineaCuenta.setNombre(gasto.getConcepto());
				lineaCuenta.setSaldo(-gasto.getCuantia());
				lineaCuenta.setGasto(true);
				lineaCuenta.setIdGasto(gasto.getId());
				TreeNode tree = new DefaultTreeNode(lineaCuenta, treeNodeFather);
				if(total == null){
					total = lineaCuenta.getSaldo();
				} else {
					total = total + lineaCuenta.getSaldo();	
				}
			}
			
			IngresoVO ingresoSearch = new IngresoVO();
			ingresoSearch.setIdCuenta(idCuenta);
			ingresoSearch.setAnyo(this.getAnyo());
			List<IngresoVO> listIngresos = this.ingresoBO.findUsingTemplate(null, ingresoSearch, null);
			for(IngresoVO ingreso: listIngresos){
				lineaCuenta = new LineaCuentaVO();
				lineaCuenta.setNombre(ingreso.getConcepto());
				lineaCuenta.setSaldo(ingreso.getCuantia());
				lineaCuenta.setIngreso(true);
				lineaCuenta.setIdIngreso(ingreso.getId());
				TreeNode tree = new DefaultTreeNode(lineaCuenta, treeNodeFather);
				if(total == null){
					total = lineaCuenta.getSaldo();
				} else {
					total = total + lineaCuenta.getSaldo();	
				}
			}
		}
		
		return total;
	}

	// ---------------------- GETTERS / SETTERS ----------------------
	public CuentaVO getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaVO cuenta) {
		this.cuenta = cuenta;
	}

	public CuentaVO getCuentaFilter() {
		return cuentaFilter;
	}

	public void setCuentaFilter(CuentaVO cuentaFilter) {
		this.cuentaFilter = cuentaFilter;
	}

	public String getListPageName() {
		return listPageName;
	}

	public CuentaBO getCuentaBO() {
		return cuentaBO;
	}

	public void setCuentaBO(CuentaBO cuentaBO) {
		this.cuentaBO = cuentaBO;
	}

	public void setListCuenta(List<CuentaVO> listCuenta) {
		this.listCuenta = listCuenta;
	}

	public List<CuentaVO> getListCuenta() {
		if (listCuenta == null) {
			this.createListCuenta();
		}

		return listCuenta;
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

	public void setListCuentaStructure(TreeNode listCuentaStructure) {
		this.listCuentaStructure = listCuentaStructure;
	}
	
	public TreeNode getListCuentaStructure() {
		return listCuentaStructure;
	}

	public Integer getAnyo() {
		return anyo;
	}

	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}

	public LineaCuentaVO getSelectedLineaCuenta() {
		return selectedLineaCuenta;
	}

	public void setSelectedLineaCuenta(LineaCuentaVO selectedLineaCuenta) {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        
		this.selectedLineaCuenta = selectedLineaCuenta;
		
		if(this.selectedLineaCuenta.getIdGasto() != null){
			this.selectedGasto = this.gastoBO.findByPrimaryKey(
					null, Long.valueOf(this.selectedLineaCuenta.getIdGasto()));
			GastoBean gastoBean = (GastoBean)this.getBean("gastoBean");
			gastoBean.setReturnPageName(this.listPageName);
		} else if(this.selectedLineaCuenta.getIdIngreso() != null){
			this.selectedIngreso = this.ingresoBO.findByPrimaryKey(
					null, Long.valueOf(this.selectedLineaCuenta.getIdIngreso()));
			IngresoBean ingresoBean = (IngresoBean)this.getBean("ingresoBean");
			ingresoBean.setReturnPageName(this.listPageName);
		}
	}

	public GastoVO getSelectedGasto() {
		return selectedGasto;
	}

	public void setSelectedGasto(GastoVO selectedGasto) {
		this.selectedGasto = selectedGasto;
	}

	public IngresoVO getSelectedIngreso() {
		return selectedIngreso;
	}

	public void setSelectedIngreso(IngresoVO selectedIngreso) {
		this.selectedIngreso = selectedIngreso;
	}

	public Double getBalanceAnual() {
		return balanceAnual;
	}

	public void setBalanceAnual(Double balanceAnual) {
		this.balanceAnual = balanceAnual;
	}
}