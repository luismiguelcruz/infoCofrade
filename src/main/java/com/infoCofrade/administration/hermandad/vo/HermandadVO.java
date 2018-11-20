package com.infoCofrade.administration.hermandad.vo;

import java.util.Date;


public class HermandadVO implements Cloneable {
	private Long id;
	private String codigo;
	private String nombreCorto;
	private String nombreLargo;
	private String dataSourceName;
	private Date fechaAlta;
	private Date fechaUltimoPago;
	private Date fechaBaja;
	private Long idLocalidad;
	private String observaciones;
	private byte[] hermandadImage;
	private String urlWeb;
	
	// Campos rellenables no guardables en base de datos
	private String provincia;
	private Integer codigoPostal;
	
	public HermandadVO(){
		this.id = null;
		this.codigo = null;
		this.nombreCorto = null;
		this.nombreLargo = null;
		this.dataSourceName = null;
		this.fechaAlta = null;
		this.fechaUltimoPago = null;
		this.fechaBaja = null;
		this.idLocalidad = null;
		this.observaciones = null;
		this.hermandadImage = null;
		this.urlWeb = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getNombreLargo() {
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaUltimoPago() {
		return fechaUltimoPago;
	}

	public void setFechaUltimoPago(Date fechaUltimoPago) {
		this.fechaUltimoPago = fechaUltimoPago;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Long getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Long idLocalidad) {
		this.idLocalidad = idLocalidad;
	}
	
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public byte[] getHermandadImage() {
		return hermandadImage;
	}

	public void setHermandadImage(byte[] hermandadImage) {
		this.hermandadImage = hermandadImage;
	}

	public String getUrlWeb() {
		return urlWeb;
	}

	public void setUrlWeb(String urlWeb) {
		this.urlWeb = urlWeb;
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
        HermandadVO clone = new HermandadVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getCodigo() != null && !this.getCodigo().isEmpty()){
        	clone.setCodigo(new String(this.getCodigo()));
        }
        if(this.getNombreCorto() != null && !this.getNombreCorto().isEmpty()){
        	clone.setNombreCorto(new String(this.getNombreCorto()));
        }
        if(this.getNombreLargo() != null && !this.getNombreLargo().isEmpty()){
        	clone.setNombreLargo(new String(this.getNombreLargo()));
        }
        if(this.getDataSourceName() != null && !this.getDataSourceName().isEmpty()){
        	clone.setDataSourceName(new String(this.getDataSourceName()));
        }
        if(this.getFechaAlta() != null){
        	clone.setFechaAlta((Date)this.getFechaAlta().clone());
        }
        if(this.getFechaUltimoPago() != null){
        	clone.setFechaUltimoPago((Date)this.getFechaUltimoPago().clone());
        }
        if(this.getFechaBaja() != null){
        	clone.setFechaBaja((Date)this.getFechaBaja().clone());
        }
        if(this.getIdLocalidad() != null){
        	clone.setIdLocalidad(new Long(this.getIdLocalidad()));
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
			HermandadVO hermandad = (HermandadVO) obj;
			if((hermandad.getId() == null && this.id == null) ||
					hermandad.getId().compareTo(this.id) == 0 ||
					hermandad.getCodigo().compareTo(this.getCodigo()) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
