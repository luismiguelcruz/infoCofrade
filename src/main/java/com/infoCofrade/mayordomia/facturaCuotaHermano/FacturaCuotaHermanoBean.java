package com.infoCofrade.mayordomia.facturaCuotaHermano;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.mayordomia.cuotaHermano.bo.CuotaHermanoBO;
import com.infoCofrade.mayordomia.cuotaHermano.vo.CuotaHermanoVO;
import com.infoCofrade.mayordomia.facturaCuotaHermano.bo.FacturaCuotaHermanoBO;
import com.infoCofrade.mayordomia.facturaCuotaHermano.vo.FacturaCuotaHermanoVO;
import com.infoCofrade.secretaria.hermano.bo.HermanoBO;
import com.infoCofrade.secretaria.hermano.vo.HermanoVO;

@Named
@Scope("session")
public class FacturaCuotaHermanoBean extends AbstractBean {
	private static final long serialVersionUID = 1L;

	private FacturaCuotaHermanoVO facturaCuotaHermano;
	private FacturaCuotaHermanoVO facturaCuotaHermanoFilter;
	private HermanoVO hermano;
	private HermanoVO hermanoFilter;

	@Inject
	private FacturaCuotaHermanoBO facturaCuotaHermanoBO;
	@Inject
	private HermanoBO hermanoBO;
	@Inject
	private CuotaHermanoBO cuotaHermanoBO;

	private List<FacturaCuotaHermanoVO> listFacturaCuotaHermano;
	private List<HermanoVO> listHermano;
	
	private Map<Long, List<FacturaCuotaHermanoVO>> mapListFacturaCuotaHermano;
	
	private Boolean checkAllGenerarCuotas;
	
	private Integer anyoGeneracionCuotas;
	
	private List<FacturaCuotaHermanoVO> listFacturasParaCrear = null;
	
	private Map<Integer, List<FacturaCuotaHermanoVO>> mapListFacturasAnualesDeHermano = null;
	private List<List<FacturaCuotaHermanoVO>> listFacturasAnualesDeHermano = null;

	private static final String listPageName = "listFacturaCuotaHermano";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";

	public FacturaCuotaHermanoBean() {
		this.facturaCuotaHermano = new FacturaCuotaHermanoVO();
		this.facturaCuotaHermanoFilter = new FacturaCuotaHermanoVO();
	}

	public void init() {
		super.init();

		this.facturaCuotaHermano = new FacturaCuotaHermanoVO();
		this.facturaCuotaHermanoFilter = new FacturaCuotaHermanoVO();
		this.hermano = new HermanoVO();
		this.hermanoFilter = new HermanoVO();
		this.anyoGeneracionCuotas = Integer.valueOf(new SimpleDateFormat("yyyy").format(new Date()));
		
		this.listFacturasAnualesDeHermano = null;
		mapListFacturasAnualesDeHermano = null;

		this.createListFacturaCuotaHermano();
		this.createListHermano();
	}

	public void clear() {
		super.init();

		this.facturaCuotaHermano = new FacturaCuotaHermanoVO();
		this.facturaCuotaHermanoFilter = new FacturaCuotaHermanoVO();
		
		this.listFacturasAnualesDeHermano = null;
		mapListFacturasAnualesDeHermano = null;
		
		this.anyoGeneracionCuotas = Integer.valueOf(new SimpleDateFormat("yyyy").format(new Date()));
	}

	// ------------------------ ACTIONS ------------------------
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
	 * Find facturaCuotaHermano elements using the filter get.
	 */
	public void doAddFilter() {
		listFacturaCuotaHermano = this.facturaCuotaHermanoBO.findUsingTemplate(null,
				facturaCuotaHermanoFilter, null);
	}

	/**
	 * Clear the facturaCuotaHermano filter of the list page
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
				this.facturaCuotaHermanoBO.createStructureElement(null, listFacturasAnualesDeHermano);

				this.init();
			} catch (DaoException e) {
				navigateString = editPageName;
			}
		}

		return navigateString;
	}

	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the facturaCuotaHermano edition
	 */
	public String doEditElement() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap()
				.get("edition");
		this.clear();

		if (id != null) {
			hermano = this.hermanoBO.findByPrimaryKey(null,
					Long.valueOf(id));
			
			this.createListFacturasAnualesDeHermano();
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
		FacturaCuotaHermanoVO facturaCuotaHermano = new FacturaCuotaHermanoVO();
		facturaCuotaHermano.setId(this.selectedDeleteItemId);

		this.facturaCuotaHermanoBO.deleteElement(null, facturaCuotaHermano);

		this.createListFacturaCuotaHermano();
	}
	
	/**
	 * Cambia el icono de generar cuota para todos los hermanos de la lista de hermanos activos
	 */
	public void selectAllGenerarCuotas() {
		for(HermanoVO hermano: listHermano){
			hermano.setGenerarCuotaAnual(checkAllGenerarCuotas);
		}
	}
	
	/**
	 * Genera todas las cuotas correspondientes para los hermanos seleccionados y el año que se ha solicitado
	 * teniendo especial atencio a que si un hermano se ha dado de alta a mitad de un año, solo genere cuotas
	 * a pagar desde la mitad de año hacia adelante.
	 */
	public void doGenerarCuotasAnuales(){
		if(this.anyoGeneracionCuotas != null && this.anyoGeneracionCuotas > 0){
			if(this.selectedDeleteItemId.compareTo(new Long(-1)) != 0){
				listHermano.get(this.selectedDeleteItemId.intValue()).setGenerarCuotaAnual(true);
			}
			
			listFacturasParaCrear = new LinkedList<FacturaCuotaHermanoVO>();
			for(HermanoVO currentHermano: listHermano){
				if(currentHermano.getGenerarCuotaAnual()){
					this.generarCuotasAnuales(currentHermano);
					
					if(listFacturasParaCrear.size() > 0){
						try {
							this.facturaCuotaHermanoBO.createElement(null, listFacturasParaCrear);
						} catch (DaoException e) {
						}
					}
				}
			}
			
			if(this.selectedDeleteItemId.compareTo(new Long(-1)) != 0){
				listHermano.get(this.selectedDeleteItemId.intValue()).setGenerarCuotaAnual(false);
			}
			
			// Actualizamos la lista de hermanos para que muestre si un hermano ha pagado las cuotas o no
			this.createListFacturaCuotaHermano();
			this.createListHermano();
		}
	}
	
	/**
	 * Elimina todas las cuotas correspondientes para los hermanos seleccionados y el año que se ha solicitado
	 */
	public void doEliminarCuotasAnuales(){
		if(this.anyoGeneracionCuotas != null && this.anyoGeneracionCuotas > 0){
			if(this.selectedDeleteItemId.compareTo(new Long(-1)) != 0){
				listHermano.get(this.selectedDeleteItemId.intValue()).setGenerarCuotaAnual(true);
			}
			
			listFacturasParaCrear = new LinkedList<FacturaCuotaHermanoVO>();
			for(HermanoVO currentHermano: listHermano){
				if(currentHermano.getGenerarCuotaAnual()){
					this.eliminarCuotasAnuales(currentHermano);
				}
			}
			
			if(this.selectedDeleteItemId.compareTo(new Long(-1)) != 0){
				listHermano.get(this.selectedDeleteItemId.intValue()).setGenerarCuotaAnual(false);
			}
			
			// Actualizamos la lista de hermanos para que muestre si un hermano ha pagado las cuotas o no
			this.createListFacturaCuotaHermano();
			this.createListHermano();
		}
	}
	
	/**
	 * Actualiza el id del pago de una factura para marcarla como seleccionada
	 * @param event
	 */
	public void actualizaCheckPagoHermanos(ValueChangeEvent event) {
		Integer anyo = Integer.valueOf(event.getComponent().getAttributes()
				.get("anyoParaCambiarPagos").toString());
		List<FacturaCuotaHermanoVO> listAux = null;
		FacturaCuotaHermanoVO item = null;
		boolean changed = false;
		
		for(int i=0; i<listFacturasAnualesDeHermano.size(); i++){
			listAux = listFacturasAnualesDeHermano.get(i);
			for(int j=0; j<listAux.size(); j++){
				item = listAux.get(j);
				if(item.getAnyo().compareTo(anyo) == 0){
					item.setPagada(Boolean.valueOf(event.getNewValue().toString()));
					listAux.set(j, item);
					changed = true;
				}
			}
			
			if(changed){
				listFacturasAnualesDeHermano.set(i, listAux);
				changed = false;
			}
		}
	}

	// ---------------------- ADITIONAL FUNCTIONS ----------------------
	private void createListFacturaCuotaHermano() {
		listFacturaCuotaHermano = this.facturaCuotaHermanoBO.findAll(null, null);
		
		mapListFacturaCuotaHermano = new HashMap<Long, List<FacturaCuotaHermanoVO>>();
		List<FacturaCuotaHermanoVO> listAux;
		for(FacturaCuotaHermanoVO facturaHermano: listFacturaCuotaHermano){
			if(!mapListFacturaCuotaHermano.containsKey(facturaHermano.getIdHermano())){
				listAux = new LinkedList<FacturaCuotaHermanoVO>();
			} else {
				listAux = mapListFacturaCuotaHermano.get(facturaHermano.getIdHermano());
			}
			
			listAux.add(facturaHermano);
			mapListFacturaCuotaHermano.put(facturaHermano.getIdHermano(), listAux);
		}
	}
	
	private void createListHermano() {
		Boolean showActive = true;
		
		listHermano = this.hermanoBO.findAll(null, "`numHermano`",
				showActive);
		
		// Para cada hermano tenemos que ver si todas las cuotas que tiene asociadas estan pagadas
		List<FacturaCuotaHermanoVO> listFacturasHermano;
		for(HermanoVO hermano: listHermano){
			hermano.setCuotaPagada(new Boolean(true));
			hermano.setGenerarCuotaAnual(new Boolean(false));
			listFacturasHermano = mapListFacturaCuotaHermano.get(hermano.getId());
			
			// Recorremos todas las facturas del hermano para ver si estan pagadas
			if(listFacturasHermano != null){
				for(FacturaCuotaHermanoVO facturaCuotaHermano: listFacturasHermano){
					// Si hay alguna factura no pagada, ponemos que el hermano debe alguna cuota
					if(facturaCuotaHermano.getPagada() != null && !facturaCuotaHermano.getPagada()){
						hermano.setCuotaPagada(false);
					}
				}	
			} else {
				hermano.setCuotaPagada(null);
			}
		}
	}
	
	private void createListFacturasAnualesDeHermano() {
		if(hermano != null && hermano.getId() != null){
			FacturaCuotaHermanoVO itemSearch = new FacturaCuotaHermanoVO();
			itemSearch.setIdHermano(hermano.getId());
			List<FacturaCuotaHermanoVO> listAux = 
					this.facturaCuotaHermanoBO.findUsingTemplate(null, itemSearch, "`anyo` DESC, `mes` ASC");
			List<FacturaCuotaHermanoVO> listIterator = new LinkedList<FacturaCuotaHermanoVO>();
			listFacturasAnualesDeHermano = new LinkedList<List<FacturaCuotaHermanoVO>>();
			
			if(listAux != null && listAux.size() > 0){
				mapListFacturasAnualesDeHermano = new HashMap<Integer, List<FacturaCuotaHermanoVO>>();
				
				for(FacturaCuotaHermanoVO item: listAux){
					if(!mapListFacturasAnualesDeHermano.containsKey(item.getAnyo())){
						if(listIterator.size() > 0){
							listFacturasAnualesDeHermano.add(listIterator);
						}
						
						mapListFacturasAnualesDeHermano.put(item.getAnyo(), listIterator);
						listIterator = new LinkedList<FacturaCuotaHermanoVO>();
					}
					
					listIterator.add(item);
				}
				
				if(mapListFacturasAnualesDeHermano.containsKey(listIterator.get(0).getAnyo()) &&
						listIterator.size() > 0){
					mapListFacturasAnualesDeHermano.put(listIterator.get(0).getAnyo(), listIterator);
					listFacturasAnualesDeHermano.add(listIterator);
				}
			}
		}
	}

	private Boolean validate() {
		Boolean value = true;

		return value;
	}
	
	// Genera las cuotas anuales para un hermano seleccionado
	private void generarCuotasAnuales(HermanoVO currentHermano){
		if(this.anyoGeneracionCuotas != null && this.anyoGeneracionCuotas > 0){
			FacturaCuotaHermanoVO  factura;
			Map<Integer, FacturaCuotaHermanoVO> mapFacturasCreadas = new HashMap<Integer, FacturaCuotaHermanoVO>();
			CuotaHermanoVO cuotaHermano = this.cuotaHermanoBO.findCuotaHermano(null, currentHermano.getEdad());
			Calendar fechaSolicitudHermano = new GregorianCalendar();
			Calendar fechaFactura = new GregorianCalendar();
			
			// Igualamos la hora del mes y el dia para que se paguen los meses completos desde que
			// el hermano se dio de alta
			fechaSolicitudHermano.setTime(currentHermano.getFechaSolicitud());
			fechaSolicitudHermano.set(Calendar.DAY_OF_MONTH, 1);
			fechaSolicitudHermano.set(Calendar.HOUR_OF_DAY, 0);
			fechaSolicitudHermano.set(Calendar.MINUTE, 0);
			fechaSolicitudHermano.set(Calendar.SECOND, 0);
			fechaSolicitudHermano.set(Calendar.MILLISECOND, 0);
			fechaFactura.setTime(new Date());
			fechaFactura.set(Calendar.YEAR, this.anyoGeneracionCuotas);
			fechaFactura.set(Calendar.DAY_OF_MONTH, 1);
			fechaFactura.set(Calendar.HOUR_OF_DAY, 0);
			fechaFactura.set(Calendar.MINUTE, 0);
			fechaFactura.set(Calendar.SECOND, 0);
			fechaFactura.set(Calendar.MILLISECOND, 0);
			
			// Solo creamos la factura si no hay ya una factura para el mes y año creada
			FacturaCuotaHermanoVO itemSearch = new FacturaCuotaHermanoVO();
			itemSearch.setAnyo(this.anyoGeneracionCuotas);
			itemSearch.setIdHermano(currentHermano.getId());
			List<FacturaCuotaHermanoVO> listFacturasCreadas = 
					this.facturaCuotaHermanoBO.findUsingTemplate(null, itemSearch, "`mes`");
			for(FacturaCuotaHermanoVO item: listFacturasCreadas){
				mapFacturasCreadas.put(item.getMes(), item);
			}
			
			for(int mesIterate= 0; mesIterate<12; mesIterate++){
				fechaFactura.set(Calendar.MONTH, mesIterate);
				
				// Si la factura no está creada, y el hermano ya estaba dado de alta ese mes de ese año
				// tenemos que crear una factura para ese mes y año del hermano 
				if(!mapFacturasCreadas.containsKey(mesIterate) && 
						fechaFactura.compareTo(fechaSolicitudHermano) >= 0){
					factura = new FacturaCuotaHermanoVO();
					factura.setIdHermano(currentHermano.getId());
					factura.setIdCuotaHermano(cuotaHermano.getId());
					factura.setMes(mesIterate);
					factura.setAnyo(this.anyoGeneracionCuotas);
					factura.setPagada(false);
					listFacturasParaCrear.add(factura);	
				}
			}
		}
	}
	
	// Elimina las cuotas anuales para un hermano seleccionado y un año
	private void eliminarCuotasAnuales(HermanoVO currentHermano){
		if(this.anyoGeneracionCuotas != null && this.anyoGeneracionCuotas > 0){
			FacturaCuotaHermanoVO deleteItem = new FacturaCuotaHermanoVO();
			deleteItem.setAnyo(this.anyoGeneracionCuotas);
			deleteItem.setIdHermano(currentHermano.getId());
			this.facturaCuotaHermanoBO.deleteElement(null, deleteItem);
		}
	}

	// ---------------------- GETTERS / SETTERS ----------------------
	public FacturaCuotaHermanoVO getFacturaCuotaHermano() {
		return facturaCuotaHermano;
	}

	public void setFacturaCuotaHermano(FacturaCuotaHermanoVO facturaCuotaHermano) {
		this.facturaCuotaHermano = facturaCuotaHermano;
	}

	public FacturaCuotaHermanoVO getFacturaCuotaHermanoFilter() {
		return facturaCuotaHermanoFilter;
	}

	public void setFacturaCuotaHermanoFilter(FacturaCuotaHermanoVO facturaCuotaHermanoFilter) {
		this.facturaCuotaHermanoFilter = facturaCuotaHermanoFilter;
	}

	public FacturaCuotaHermanoBO getFacturaCuotaHermanoBO() {
		return facturaCuotaHermanoBO;
	}

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

	public void setFacturaCuotaHermanoBO(FacturaCuotaHermanoBO facturaCuotaHermanoBO) {
		this.facturaCuotaHermanoBO = facturaCuotaHermanoBO;
	}

	public void setListFacturaCuotaHermano(List<FacturaCuotaHermanoVO> listFacturaCuotaHermano) {
		this.listFacturaCuotaHermano = listFacturaCuotaHermano;
	}

	public List<FacturaCuotaHermanoVO> getListFacturaCuotaHermano() {
		if (listFacturaCuotaHermano == null) {
			this.createListFacturaCuotaHermano();
		}

		return listFacturaCuotaHermano;
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

	public Boolean getCheckAllGenerarCuotas() {
		return checkAllGenerarCuotas;
	}

	public void setCheckAllGenerarCuotas(Boolean checkAllGenerarCuotas) {
		this.checkAllGenerarCuotas = checkAllGenerarCuotas;
	}

	public Integer getAnyoGeneracionCuotas() {
		return anyoGeneracionCuotas;
	}

	public void setAnyoGeneracionCuotas(Integer anyoGeneracionCuotas) {
		this.anyoGeneracionCuotas = anyoGeneracionCuotas;
	}

	public List<List<FacturaCuotaHermanoVO>> getListFacturasAnualesDeHermano() {
		return listFacturasAnualesDeHermano;
	}

	public void setListFacturasAnualesDeHermano(
			List<List<FacturaCuotaHermanoVO>> listFacturasAnualesDeHermano) {
		this.listFacturasAnualesDeHermano = listFacturasAnualesDeHermano;
	}
}