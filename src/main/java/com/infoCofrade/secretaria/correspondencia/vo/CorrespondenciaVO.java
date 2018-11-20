package com.infoCofrade.secretaria.correspondencia.vo;

import java.util.Date;


public class CorrespondenciaVO implements Cloneable {
	private Long id;
	private String remitente;
	private String receptor;
	private Date fechaEntrada;
	private Date fechaSalida;
	private String asunto;
	private Long idGrupo;
	
	public CorrespondenciaVO(){
		this.id = null;
		this.remitente = null;
		this.receptor = null;
		this.fechaEntrada = null;
		this.fechaSalida = null;
		this.asunto = null;
		this.idGrupo = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	public String getReceptor() {
		return receptor;
	}

	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        CorrespondenciaVO clone = new CorrespondenciaVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getRemitente() != null && !this.getRemitente().isEmpty()){
        	clone.setRemitente(new String(this.getRemitente()));
        }
        if(this.getFechaEntrada() != null){
        	clone.setFechaEntrada((Date)this.getFechaEntrada().clone());
        }
        if(this.getFechaSalida() != null){
        	clone.setFechaSalida((Date)this.getFechaSalida().clone());
        }
        if(this.getAsunto() != null && !this.getAsunto().isEmpty()){
        	clone.setAsunto(new String(this.getAsunto()));
        }
        if(this.getIdGrupo() != null){
        	clone.setIdGrupo(new Long(this.getIdGrupo()));
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = true;
		
		if(obj != null){
			CorrespondenciaVO correspondencia = (CorrespondenciaVO) obj;
			if((correspondencia.getId() != null && this.id == null) ||
				(correspondencia.getId() == null && this.id != null) ||
					correspondencia.getId().compareTo(this.id) != 0){
				result = false;
			}
		}
		
		return result;
	}
}
