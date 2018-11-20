package com.infoCofrade.administration.secretaria.calle.vo;


public class CalleVO {
	private Long id;
	private Long idTipoVia;
	private String nombreVia;
	private String numKmMinimo;
	private String numKmMaximo;
	private Long idLocalidad;
	private Long idSector;
	private String encargado;
	
	// Campos rellenables no guardables en base de datos
	private String provincia;
	private Integer codigoPostal;
	
	public CalleVO(){
		this.id = null;
		this.idTipoVia = null;
		this.nombreVia = null;
		this.numKmMinimo = null;
		this.numKmMaximo = null;
		this.idLocalidad = null;
		this.idSector = null;
		this.encargado = null;
		
		this.provincia = null;
		this.codigoPostal = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(Long idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}
	
	public String getNumKmMinimo() {
		return numKmMinimo;
	}

	public void setNumKmMinimo(String numKmMinimo) {
		this.numKmMinimo = numKmMinimo;
	}

	public String getNumKmMaximo() {
		return numKmMaximo;
	}

	public void setNumKmMaximo(String numKmMaximo) {
		this.numKmMaximo = numKmMaximo;
	}

	public Long getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Long idLocalidad) {
		this.idLocalidad = idLocalidad;
	}
	
	public Long getIdSector() {
		return idSector;
	}

	public void setIdSector(Long idSector) {
		this.idSector = idSector;
	}
	
	public String getEncargado() {
		return encargado;
	}

	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}
	
	

	//-------------- GETTERS / SETTERS ADICIONALES ---------------
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
	
	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        CalleVO clone = new CalleVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getIdTipoVia() != null){
        	clone.setIdTipoVia(new Long(this.getIdTipoVia()));
        }
        if(this.getNombreVia() != null && !this.getNombreVia().isEmpty()){
        	clone.setNombreVia(new String(this.getNombreVia()));
        }
        if(this.getNumKmMinimo() != null && !this.getNumKmMinimo().isEmpty()){
        	clone.setNumKmMinimo(new String(this.getNumKmMinimo()));
        }
        if(this.getNumKmMaximo() != null && !this.getNumKmMaximo().isEmpty()){
        	clone.setNumKmMaximo(new String(this.getNumKmMaximo()));
        }
        if(this.getIdLocalidad() != null){
        	clone.setIdLocalidad(new Long(this.getIdLocalidad()));
        }
        if(this.getIdSector() != null){
        	clone.setIdSector(new Long(this.getIdSector()));
        }
        if(this.getEncargado() != null && !this.getEncargado().isEmpty()){
        	clone.setEncargado(new String(this.getEncargado()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			CalleVO calle = (CalleVO) obj;
			if((calle.getId() == null && this.id == null) ||
					calle.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
