package com.infoCofrade.administration.secretaria.grupo.vo;



public class GrupoVO implements Cloneable {
	private Long id;
	private String nombre;
	
	public GrupoVO(){
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
        GrupoVO clone = new GrupoVO();
        
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
			GrupoVO grupo = (GrupoVO) obj;
			if((grupo.getId() == null && this.id == null) ||
					grupo.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
