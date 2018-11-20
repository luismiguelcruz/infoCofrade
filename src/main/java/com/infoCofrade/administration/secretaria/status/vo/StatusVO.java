package com.infoCofrade.administration.secretaria.status.vo;


public class StatusVO implements Cloneable {
	private Long id;
	private String nombre;
	
	public StatusVO(){
		this.id = null;
		this.nombre = null;
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
	
	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        StatusVO clone = new StatusVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getNombre() != null && !this.getNombre().isEmpty()){
        	clone.setNombre(new String(this.getNombre()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			StatusVO status = (StatusVO) obj;
			if((status.getId() == null && this.id == null) ||
					status.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
