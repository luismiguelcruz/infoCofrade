package com.infoCofrade.secretaria.memoriaAnual.vo;

import java.util.List;

import com.infoCofrade.secretaria.acto.vo.ActoVO;


public class EventosMesVO implements Cloneable {
	private Long id;
	private String nombreMes;
	private List<ActoVO> listActosMes;
	
	public EventosMesVO(){
		this.id = null;
		this.nombreMes = null;
		this.listActosMes = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreMes() {
		return nombreMes;
	}

	public void setNombreMes(String nombreMes) {
		this.nombreMes = nombreMes;
	}
	
	public List<ActoVO> getListActosMes() {
		return listActosMes;
	}

	public void setListActosMes(List<ActoVO> listActosMes) {
		this.listActosMes = listActosMes;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        EventosMesVO clone = new EventosMesVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getNombreMes() != null && !this.getNombreMes().isEmpty()){
        	clone.setNombreMes(new String(this.getNombreMes()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			EventosMesVO eventosMes = (EventosMesVO) obj;
			if((eventosMes.getId() == null && this.id == null) ||
					eventosMes.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
