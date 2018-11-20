package com.infoCofrade.administration.ataqueSQL.vo;

import java.util.Date;


public class AtaqueSQLVO implements Cloneable {
	private Long id;
	private String ip;
	private String sentencia;
	private Date hora;
	
	public AtaqueSQLVO(){
		this.id = null;
		this.ip = null;
		this.sentencia = null;
		this.hora = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSentencia() {
		return sentencia;
	}

	public void setSentencia(String sentencia) {
		this.sentencia = sentencia;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}
	

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        AtaqueSQLVO clone = new AtaqueSQLVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getIp() != null && !this.getIp().isEmpty()){
        	clone.setIp(new String(this.getIp()));
        }
        if(this.getSentencia() != null && !this.getSentencia().isEmpty()){
        	clone.setSentencia(new String(this.getSentencia()));
        }
        if(this.getHora() != null){
        	clone.setHora((Date) this.getHora().clone());
        }
        
        return clone;
    }

	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			AtaqueSQLVO ataqueSQL = (AtaqueSQLVO) obj;
			if((ataqueSQL.getId() == null && this.id == null) ||
					ataqueSQL.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}