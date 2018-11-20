package com.infoCofrade.secretaria.informe;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.text.WordUtils;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import com.infoCofrade.administration.secretaria.calleView.bo.CalleViewBO;
import com.infoCofrade.administration.secretaria.calleView.vo.CalleViewVO;
import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.secretaria.hermano.bo.HermanoBO;
import com.infoCofrade.secretaria.hermano.vo.HermanoVO;
import com.infoCofrade.secretaria.informe.vo.InformeVO;


@Named
@Scope("session")
public class InformeBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;
	
	private InformeVO informe;
	
	private List<InformeVO> listReportItems;
	
	private Boolean checkAllItems;
	
	
	// Variables particulares de cada informe
	private HermanoVO hermanoFilter;
	@Inject
	private HermanoBO hermanoBO;
	@Inject
	private CalleViewBO calleViewBO;
	
	
	// Variables para la navegación
	private static final String listPageName = "listInforme";
	private static final String editPageName = "edit";
	
	
	public InformeBean(){
		this.informe = new InformeVO();
	}
	

	public void init(){
		super.init();
		
		this.informe = new InformeVO();
		this.hermanoFilter = new HermanoVO();
	}
	
	public void clear(){
		super.init();
		
		this.informe = new InformeVO();
		this.hermanoFilter = new HermanoVO();
	}
	
	//------------------------------- ACTIONS -------------------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * @return String value of the destiny page
	 */
	public String doNavigate(){
		this.init();
		
		return listPageName;
	}
	
	public String doNavigateReport(){
		String navigate = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		String reportString = fc.getExternalContext().getRequestParameterMap().get("report");
		
		if(reportString != null && !reportString.isEmpty()){
			navigate = editPageName + WordUtils.capitalize(reportString);
			
			this.createListHermano();
		}
		
		return navigate;
	}
	
	/**
	 * Selecciona los elementos a incluir en el informe
	 */
	public void selectAllItems() {
		for(InformeVO item: listReportItems){
			item.setChecked(checkAllItems);
		}
	}
	
	public StreamedContent getGeneratedReport(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String reportString = fc.getExternalContext().getRequestParameterMap().get("report");
		StreamedContent result = null;
		
		if(reportString != null && !reportString.isEmpty()){
			List<InformeVO> listReportItemsSelected = new LinkedList<InformeVO>();
			List<InformeVO> listReportEvenItemsSelected = new LinkedList<InformeVO>();
			
			// Creamos la lista de los elementos a mostrar en el informe eliminando aquellos
			// elementos que no han sido seleccionados
			int i = 0;
			for(InformeVO item: listReportItems){
				if(item.getChecked()){
					if((i+1)%2 == 0){
						listReportEvenItemsSelected.add(item);
					} else {
						listReportItemsSelected.add(item);	
					}
					i++;
				}
			}
			
			if(listReportItemsSelected != null && listReportItemsSelected.size() > 0){
				String outputFileName = null;
				
				Map<Object, Object> data = new HashMap<Object, Object>();
				String templatePath = null;
				ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
		                .getExternalContext().getContext();
		        String projectPath = ctx.getRealPath("/");
				
				if(reportString.equalsIgnoreCase("etiquetas")){
					templatePath = projectPath + "/resources/reportsTemplates/etiquetas.odt";
					outputFileName = "Etiquetas postales";
					
					data.put("listHermanos", listReportItemsSelected);
					data.put("listHermanosPares", listReportEvenItemsSelected);
					data.put("var", "value");
				}
				
				result = this.getDownloadReportFile(data, templatePath, outputFileName);
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"ERROR DE INFORME: ",
								"No se han seleccionado elementos a mostrar en el informe"));
			}
		}
		
		return result;
	}
	
	/**
	 * Busca los hermanos que cumplan los requisitos del filtro
	 */
	public void doAddFilterHermano() {
		Boolean showActive = true;
		
		List<HermanoVO> listHermanos = this.hermanoBO.findUsingTemplate(null, hermanoFilter,
				null, showActive);
		
		if(listHermanos != null && listHermanos.size() > 0){
			generaListInforme(listHermanos);	
		}
	}

	/**
	 * Limpia el filtro de hermanos y los muestra en pantalla
	 */
	public void doClearFilterHermano() {
		this.init();
		
		createListHermano();
	}
	
	// -------------------- ADITIONAL FUNCTIONS --------------------
	private void createListHermano() {
		Boolean showActive = true;
		List<HermanoVO> listHermanos = this.hermanoBO.findAll(null, "`numHermano`",
				showActive);
		
		if(listHermanos != null && listHermanos.size() > 0){
			generaListInforme(listHermanos);	
		}
	}
	
	private void generaListInforme(List listItems){
		InformeVO informe = new InformeVO();
		listReportItems = new LinkedList<InformeVO>();
		
		for(Object item: listItems){
			informe = new InformeVO();
		
			if(listItems.get(0) instanceof HermanoVO){
				HermanoVO hermano = (HermanoVO)item;
				
				informe.setString1(hermano.getNombre());
				informe.setString2(hermano.getApellidos());
				if(hermano.getIdCalle() != null){
					CalleViewVO calleView = calleViewBO.findByPrimaryKey(null, hermano.getIdCalle());
					informe.setString3(calleView.getTipoVia());
					informe.setString4(calleView.getNombreVia());
					informe.setString5(hermano.getNumKm());
					informe.setString6(hermano.getBloque());
					informe.setString7(hermano.getEscalera());
					informe.setString8(hermano.getPiso());
					informe.setString9(hermano.getPuerta());
					informe.setString10(calleView.getCodigoPostal()+"");
					informe.setString11(calleView.getLocalidad());
					informe.setString12(calleView.getProvincia());
					informe.setString13(calleView.getPais());
				}
			}
			
			listReportItems.add(informe);
		}
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public Boolean getCheckAllItems() {
		return checkAllItems;
	}

	public void setCheckAllItems(Boolean checkAllItems) {
		this.checkAllItems = checkAllItems;
	}

	public List<InformeVO> getListReportItems() {
		return listReportItems;
	}

	public void setListReportItems(List<InformeVO> listReportItems) {
		this.listReportItems = listReportItems;
	}

	public InformeVO getInforme() {
		return informe;
	}

	public void setInforme(InformeVO informe) {
		this.informe = informe;
	}

	public HermanoVO getHermanoFilter() {
		return hermanoFilter;
	}

	public void setHermanoFilter(HermanoVO hermanoFilter) {
		this.hermanoFilter = hermanoFilter;
	}
}