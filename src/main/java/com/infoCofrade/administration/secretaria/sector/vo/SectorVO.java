package com.infoCofrade.administration.secretaria.sector.vo;

import com.infoCofrade.administration.secretaria.sector.vo.SectorVO;


public class SectorVO implements Cloneable {
	private Long id;
	private String nombre;
	private String personaReparto;
	
	public SectorVO(){
		this.id = null;
		this.nombre = null;
		this.personaReparto = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getPersonaReparto() {
		return personaReparto;
	}

	public void setPersonaReparto(String personaReparto) {
		this.personaReparto = personaReparto;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        SectorVO clone = new SectorVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getNombre() != null && !this.getNombre().isEmpty()){
        	clone.setNombre(new String(this.getNombre()));
        }
        if(this.getPersonaReparto() != null && !this.getPersonaReparto().isEmpty()){
        	clone.setPersonaReparto(new String(this.getPersonaReparto()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			SectorVO sector = (SectorVO) obj;
			if((sector.getId() == null && this.id == null) ||
					sector.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
