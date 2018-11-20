package com.infoCofrade.secretaria.acto.vo;

import java.util.Date;


public class ActoVO implements Cloneable {
	private Long id;
	private String titulo;
	private Date fecha;
	private String lugar;
	private Long idLocalidad;
	private String descripcion;
	
	private String provincia;
	private Integer codigoPostal;
	
	public ActoVO(){
		this.id = null;
		this.titulo = null;
		this.fecha = null;
		this.lugar = null;
		this.idLocalidad = null;
		this.descripcion = null;
		
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Long getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Long idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        ActoVO clone = new ActoVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getTitulo() != null && !this.getTitulo().isEmpty()){
        	clone.setTitulo(new String(this.getTitulo()));
        }
        if(this.getFecha() != null){
        	clone.setFecha((Date)this.getFecha().clone());
        }
        if(this.getLugar() != null && !this.getLugar().isEmpty()){
        	clone.setLugar(new String(this.getLugar()));
        }
        if(this.getIdLocalidad() != null){
        	clone.setIdLocalidad(new Long(this.getIdLocalidad()));
        }
        if(this.getDescripcion() != null && !this.getDescripcion().isEmpty()){
        	clone.setDescripcion(new String(this.getDescripcion()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			ActoVO acto = (ActoVO) obj;
			if((acto.getId() == null && this.id == null) ||
					acto.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}
