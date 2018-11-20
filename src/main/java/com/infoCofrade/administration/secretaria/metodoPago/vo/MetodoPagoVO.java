package com.infoCofrade.administration.secretaria.metodoPago.vo;


public class MetodoPagoVO implements Cloneable {
	private Long id;
	private String nombre;
	private Boolean seleccionable;
	
	public MetodoPagoVO(){
		this.id = null;
		this.nombre = null;
		this.seleccionable = null;
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
	
	public Boolean getSeleccionable() {
		return seleccionable;
	}

	public void setSeleccionable(Boolean seleccionable) {
		this.seleccionable = seleccionable;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        MetodoPagoVO clone = new MetodoPagoVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getNombre() != null && !this.getNombre().isEmpty()){
        	clone.setNombre(new String(this.getNombre()));
        }
        if(this.getSeleccionable() != null){
        	clone.setSeleccionable(new Boolean(this.getSeleccionable()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			MetodoPagoVO metodoPago = (MetodoPagoVO) obj;
			if((metodoPago.getId() == null && this.id == null) ||
					metodoPago.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}