package com.infoCofrade.secretaria.entidad.vo;


public class EntidadVO implements Cloneable {
	private Long id;
	private Long idGrupo;
	private String razonSocial;
	private String nombreCorto;
	private String cifNif;
	private String domicilio;
	private Long idLocalidad;
	private String telefono;
	private String email;
	private String personaContacto1;
	private String movilContacto1;
	private String personaContacto2;
	private String movilContacto2;
	private String observaciones;
	
	private String provincia;
	private Integer codigoPostal;
	
	public EntidadVO(){
		this.id = null;
		this.idGrupo = null;
		this.razonSocial = null;
		this.nombreCorto = null;
		this.cifNif = null;
		this.domicilio = null;
		this.idLocalidad = null;
		this.telefono = null;
		this.email = null;
		this.personaContacto1 = null;
		this.movilContacto1 = null;
		this.personaContacto2 = null;
		this.movilContacto2 = null;
		this.observaciones = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getCifNif() {
		return cifNif;
	}

	public void setCifNif(String cifNif) {
		this.cifNif = cifNif;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Long getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Long idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonaContacto1() {
		return personaContacto1;
	}

	public void setPersonaContacto1(String personaContacto1) {
		this.personaContacto1 = personaContacto1;
	}
	
	public String getMovilContacto1() {
		return movilContacto1;
	}

	public void setMovilContacto1(String movilContacto1) {
		this.movilContacto1 = movilContacto1;
	}
	
	public String getPersonaContacto2() {
		return personaContacto2;
	}

	public void setPersonaContacto2(String personaContacto2) {
		this.personaContacto2 = personaContacto2;
	}
	
	public String getMovilContacto2() {
		return movilContacto2;
	}

	public void setMovilContacto2(String movilContacto2) {
		this.movilContacto2 = movilContacto2;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        EntidadVO clone = new EntidadVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getIdGrupo() != null){
        	clone.setIdGrupo(new Long(this.getIdGrupo()));
        }
        if(this.getRazonSocial() != null && !this.getRazonSocial().isEmpty()){
        	clone.setRazonSocial(new String(this.getRazonSocial()));
        }
        if(this.getNombreCorto() != null && !this.getNombreCorto().isEmpty()){
        	clone.setNombreCorto(new String(this.getNombreCorto()));
        }
        if(this.getCifNif() != null && !this.getCifNif().isEmpty()){
        	clone.setCifNif(new String(this.getCifNif()));
        }
        if(this.getDomicilio() != null && !this.getDomicilio().isEmpty()){
        	clone.setDomicilio(new String(this.getDomicilio()));
        }
        if(this.getIdLocalidad() != null){
        	clone.setIdLocalidad(new Long(this.getIdLocalidad()));
        }
        if(this.getTelefono() != null && !this.getTelefono().isEmpty()){
        	clone.setTelefono(new String(this.getTelefono()));
        }
        if(this.getEmail() != null && !this.getEmail().isEmpty()){
        	clone.setEmail(new String(this.getEmail()));
        }
        if(this.getPersonaContacto1() != null && !this.getPersonaContacto1().isEmpty()){
        	clone.setPersonaContacto1(new String(this.getPersonaContacto1()));
        }
        if(this.getMovilContacto1() != null && !this.getMovilContacto1().isEmpty()){
        	clone.setMovilContacto1(new String(this.getMovilContacto1()));
        }
        if(this.getPersonaContacto2() != null && !this.getPersonaContacto2().isEmpty()){
        	clone.setPersonaContacto2(new String(this.getPersonaContacto2()));
        }
        if(this.getMovilContacto2() != null && !this.getMovilContacto2().isEmpty()){
        	clone.setMovilContacto2(new String(this.getMovilContacto2()));
        }
        if(this.getObservaciones() != null && !this.getObservaciones().isEmpty()){
        	clone.setObservaciones(new String(this.getObservaciones()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			EntidadVO entidad = (EntidadVO) obj;
			if((entidad.getId() == null && this.id == null) ||
					entidad.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
