package com.infoCofrade.administration.tipoVia.vo;


public class TipoViaVO implements Cloneable {
	private Long id;
	private String sigla;
	private String nombre;
	
	public TipoViaVO(){
		this.id = null;
		this.sigla = null;
		this.nombre = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
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
        TipoViaVO clone = new TipoViaVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getSigla() != null && !this.getSigla().isEmpty()){
        	clone.setSigla(new String(this.getSigla()));
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
			TipoViaVO tipoVia = (TipoViaVO) obj;
			if((tipoVia.getId() == null && this.id == null) ||
					tipoVia.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
