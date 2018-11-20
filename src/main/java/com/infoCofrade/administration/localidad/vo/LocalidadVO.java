package com.infoCofrade.administration.localidad.vo;


public class LocalidadVO implements Cloneable {
	private Long id;
	private Long idProvincia;
	private String localidad;
	private String localidadSeo;
	private Integer codigoPostal;
	private Double latitud;
	private Double longitud;
	
	public LocalidadVO(){
		this.id = null;
		this.idProvincia = null;
		this.localidad = null;
		this.localidadSeo = null;
		this.codigoPostal = null;
		this.latitud = null;
		this.longitud = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getLocalidadSeo() {
		return localidadSeo;
	}

	public void setLocalidadSeo(String localidadSeo) {
		this.localidadSeo = localidadSeo;
	}
	
	public Integer getCodigoPostal() {
		return codigoPostal;
	}
	
	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}


	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        LocalidadVO clone = new LocalidadVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getLocalidad() != null && !this.getLocalidad().isEmpty()){
        	clone.setLocalidad(new String(this.getLocalidad()));
        }
        if(this.getLocalidadSeo() != null && !this.getLocalidadSeo().isEmpty()){
        	clone.setLocalidadSeo(new String(this.getLocalidadSeo()));
        }
        if(this.getCodigoPostal() != null){
        	clone.setCodigoPostal(new Integer(this.getCodigoPostal()));
        }
        if(this.getLatitud() != null){
        	clone.setLatitud(new Double(this.getLatitud()));
        }
        if(this.getLongitud() != null){
        	clone.setLongitud(new Double(this.getLongitud()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			result = true;
			
			LocalidadVO localidad = (LocalidadVO) obj;
			if (localidad.getId() == null || 
					this.getId() == null || 
					localidad.getId().compareTo(this.getId()) != 0){
				result = false;
			}
			else if (localidad.getCodigoPostal() == null || this.getCodigoPostal() == null ||
					localidad.getCodigoPostal().compareTo(this.getCodigoPostal()) != 0){
				result = false;
			}
			else if (localidad.getLocalidad() == null || this.getLocalidad() == null ||
					!localidad.getLocalidad().equalsIgnoreCase(this.getLocalidad())){
				result = false;
			}
		}
		
		return result;
	}
}
