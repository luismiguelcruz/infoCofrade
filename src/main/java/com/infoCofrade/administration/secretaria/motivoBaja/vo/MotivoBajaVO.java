package com.infoCofrade.administration.secretaria.motivoBaja.vo;


public class MotivoBajaVO implements Cloneable {
	private Long id;
	private String nombre;
	
	public MotivoBajaVO(){
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
        MotivoBajaVO clone = new MotivoBajaVO();
        
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
			MotivoBajaVO motivoBaja = (MotivoBajaVO) obj;
			if((motivoBaja.getId() == null && this.id == null) ||
					motivoBaja.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
