package com.infoCofrade.administration.secretaria.calleView.vo;

public class CalleViewVO {
	private Long id;
	private Long idTipoVia;
	private String tipoVia;
	private String nombreVia;
	private String numKmMinimo;
	private String numKmMaximo;
	private Long idLocalidad;
	private Long idSector;
	private String encargado;
	private String localidad;
	private Integer codigoPostal;
	private Double latitud;
	private Double longitud;
	private Long idProvincia;
	private String provincia;
	private Long idPais;
	private String pais;
	
	public CalleViewVO(){
		this.id = null;
		this.idTipoVia = null;
		this.tipoVia = null;
		this.nombreVia = null;
		this.numKmMinimo = null;
		this.numKmMaximo = null;
		this.idLocalidad = null;
		this.idSector = null;
		this.encargado = null;
		this.localidad = null;
		this.codigoPostal = null;
		this.latitud = null;
		this.longitud = null;
		this.idProvincia = null;
		this.provincia = null;
		this.idPais = null;
		this.pais = null;
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
	
	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
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
	
	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
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

	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Long getIdPais() {
		return idPais;
	}

	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        CalleViewVO clone = new CalleViewVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getIdTipoVia() != null){
        	clone.setIdTipoVia(new Long(this.getIdTipoVia()));
        }
        if(this.getTipoVia() != null && !this.getTipoVia().isEmpty()){
        	clone.setTipoVia(new String(this.getTipoVia()));
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
        if(this.getLocalidad() != null && !this.getLocalidad().isEmpty()){
        	clone.setLocalidad(new String(this.getLocalidad()));
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
        if(this.getIdProvincia() != null){
        	clone.setIdProvincia(new Long(this.getIdProvincia()));
        }
        if(this.getProvincia() != null && !this.getProvincia().isEmpty()){
        	clone.setProvincia(new String(this.getProvincia()));
        }
        if(this.getIdPais() != null){
        	clone.setIdPais(new Long(this.getIdPais()));
        }
        if(this.getPais() != null && !this.getPais().isEmpty()){
        	clone.setPais(new String(this.getPais()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			CalleViewVO calleView = (CalleViewVO) obj;
			if((calleView.getId() == null && this.id == null) ||
					calleView.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}