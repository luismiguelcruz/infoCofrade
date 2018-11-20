package com.infoCofrade.secretaria.memoriaAnual;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.infoCofrade.common.bean.AbstractBean;
import com.infoCofrade.common.exceptions.DaoException;
import com.infoCofrade.secretaria.acto.bo.ActoBO;
import com.infoCofrade.secretaria.acto.vo.ActoVO;
import com.infoCofrade.secretaria.memoriaAnual.vo.EventosMesVO;


@Named
@Scope("session")
public class MemoriaAnualBean extends AbstractBean
{
	private static final long serialVersionUID = 1L;

	private ActoVO acto;
	private ActoVO actoFilter;
	private Integer anyo;
	
	@Inject
	private ActoBO actoBO;
	
	private List<ActoVO> listActo;
	private List<EventosMesVO> listMemoriaAnual;
	
	private static final String listPageName = "listMemoriaAnual";
	private static final String editPageName = "edit";
	private static final String viewPageName = "view";
	
	
	public MemoriaAnualBean(){
		this.acto = new ActoVO();
		this.actoFilter = new ActoVO();
	}
	

	public void init(){
		super.init();
		
		this.acto = new ActoVO();
		this.actoFilter = new ActoVO();
		this.anyo = Integer.valueOf(new SimpleDateFormat("yyyy").format(new Date()));
		
		this.createListMemoriaAnual();
	}
	
	public void clear(){
		super.init();
		
		this.acto = new ActoVO();
		this.actoFilter = new ActoVO();
		this.anyo = Integer.valueOf(new SimpleDateFormat("yyyy").format(new Date()));
	}
	
	//------------------------------- ACTIONS ----------------------------------
	/**
	 * Initialize the values where the list page is showed from menu
	 * @return String value of the destiny page
	 */
	public String doNavigate(){
		this.init();
		
		return listPageName;
	}
	
	/**
	 * Find memoriaAnual elements using the filter get.
	 */
	public void doAddFilter(){
		this.createListMemoriaAnual();
	}
	
	/**
	 * Clear the memoriaAnual filter of the list page
	 */
	public void doClearFilter(){
		this.init();
	}
	
	/**
	 * Add a new element into the database
	 * @return String value of the destiny page
	 */
	public String doCreateElement(){
		String navigateString = listPageName;
			
		try{
			this.actoBO.createElement(null, acto);
			
			this.init();
		} catch (DaoException e){
			navigateString = editPageName;
		}
		
		return navigateString;
	}
	
	/**
	 * Navigate to the edition page
	 * @return String destiny mapped page from the memoriaAnual edition
	 */
	public String doEditElement(){
		FacesContext fc = FacesContext.getCurrentInstance();
		String id = fc.getExternalContext().getRequestParameterMap().get("id");
		String edition = fc.getExternalContext().getRequestParameterMap().get("edition");
		this.clear();

		if (id != null){
			acto = this.actoBO.findByPrimaryKey(null, Long.valueOf(id));
		}

		if(edition != null && edition.equalsIgnoreCase("edition")){
			return editPageName;	
		} else {
			return viewPageName;
		}
	}
	
	/**
	 * Delete an element from database
	 * @return String value of the destiny page
	 */
	public void doDeleteElement(){
		ActoVO memoriaAnual = new ActoVO();
		memoriaAnual.setId(this.selectedDeleteItemId);
		
		this.actoBO.deleteElement(null, memoriaAnual);
		
		this.createListMemoriaAnual();
	}
	
	//-------------------------- ADITIONAL FUNCTIONS -----------------------------
	private void createListMemoriaAnual(){
		listActo = this.actoBO.findActoByYear(null, anyo, "fecha ASC");
		List<ActoVO> listActosMes = new LinkedList<ActoVO>();
		SimpleDateFormat sdfNumeroMes = new SimpleDateFormat("MM");
		SimpleDateFormat sdfNombreMes = new SimpleDateFormat("MMMM", new Locale("es","ES"));
		listMemoriaAnual = new LinkedList<EventosMesVO>();
		EventosMesVO eventosMes = new EventosMesVO();
		
		// Crea una lista de eventos para cada mes
		for(ActoVO acto: listActo){
			if(listActosMes.size() == 0){
				listActosMes.add(acto);
			} else {
				// Si el ultimo evento metido no es del mismo mes que el evento actual, metemos la lista
				// de eventos del mes anterior dentro de la lista de eventos anuales e inicializamos
				// la lista de eventos del mes para meter el evento del nuevo mes
				if(!sdfNumeroMes.format(acto.getFecha()).equalsIgnoreCase(
						sdfNumeroMes.format(listActosMes.get(listActosMes.size()-1).getFecha()))){
					eventosMes.setListActosMes(listActosMes);
					eventosMes.setNombreMes(sdfNombreMes.format(listActosMes.get(0).getFecha()));
					listMemoriaAnual.add(eventosMes);
					listActosMes = new LinkedList<ActoVO>();
					eventosMes = new EventosMesVO();
				}
				
				listActosMes.add(acto);
			}
		}
		
		if(!listActo.isEmpty() && !listActosMes.isEmpty()){
			// Si no hemos metido la ultima lista en la memoria anual, la metemos
			if(listActo.get(listActo.size()-1).getFecha().compareTo(listActosMes.get(listActosMes.size()-1).getFecha()) == 0){
				eventosMes.setListActosMes(listActosMes);
				eventosMes.setNombreMes(sdfNombreMes.format(listActosMes.get(0).getFecha()));
				listMemoriaAnual.add(eventosMes);
				listActosMes = null;
			}	
		}
	}
	
	//-------------------------- GETTERS / SETTERS -----------------------------
	public ActoVO getActo() {
		return acto;
	}

	public void setActo(ActoVO acto) {
		this.acto = acto;
	}

	public ActoVO getActoFilter() {
		return actoFilter;
	}

	public void setActoFilter(ActoVO actoFilter) {
		this.actoFilter = actoFilter;
	}

	public Integer getAnyo() {
		return anyo;
	}

	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}

	public ActoBO getActoBO() {
		return actoBO;
	}

	public void setActoBO(ActoBO actoBO) {
		this.actoBO = actoBO;
	}

	public void setListActo(List<ActoVO> listActo) {
		this.listActo = listActo;
	}

	public List<ActoVO> getListActo(){
		if(listActo == null){
			this.createListMemoriaAnual();
		}
		
		return listActo;
	}

	public List<EventosMesVO> getListMemoriaAnual() {
		return listMemoriaAnual;
	}

	public void setListMemoriaAnual(List<EventosMesVO> listMemoriaAnual) {
		this.listMemoriaAnual = listMemoriaAnual;
	}
}