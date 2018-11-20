package com.infoCofrade.administration.tesoreria.plazoPago.vo;


public class PlazoPagoVO implements Cloneable {
	private Long id;
	private String nombre;
	private Integer numDias;
	private Boolean seleccionable;
	
	public PlazoPagoVO(){
		this.id = null;
		this.nombre = null;
		this.numDias = null;
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
	
	public Integer getNumDias() {
		return numDias;
	}

	public void setNumDias(Integer numDias) {
		this.numDias = numDias;
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
        PlazoPagoVO clone = new PlazoPagoVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getNombre() != null && !this.getNombre().isEmpty()){
        	clone.setNombre(new String(this.getNombre()));
        }
        if(this.getNumDias() != null){
        	clone.setNumDias(new Integer(this.getNumDias()));
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
			PlazoPagoVO plazoPago = (PlazoPagoVO) obj;
			if((plazoPago.getId() == null && this.id == null) ||
					plazoPago.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}