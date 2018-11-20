package com.infoCofrade.web.noticia.vo;

import java.util.Date;

import com.infoCofrade.common.bean.Constants;

public class NoticiaVO implements Cloneable {
	private Long id;
	private String titulo;
	private String contenido;
	private Date fecha;
	private byte[] imagenNoticia;
	private String styleClass;
	
	public NoticiaVO(){
		this.id = null;
		this.titulo = null;
		this.contenido = null;
		this.fecha = null;
		this.imagenNoticia = null;
		this.styleClass = null;
	}
	
	//----------------------- GETTERS / SETTERS -------------------------
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

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public byte[] getImagenNoticia() {
		return imagenNoticia;
	}

	public void setImagenNoticia(byte[] imagenNoticia) {
		this.imagenNoticia = imagenNoticia;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	public String getResumen(){
		String result = contenido.replaceAll("<[a-zA-Z]*[0-9]*>", "");
		
		if(result != null && result.length() > Integer.parseInt(Constants.LONGITUD_RESUMEN_NOTICIAS)){
			result = result.substring(0, Integer.parseInt(Constants.LONGITUD_RESUMEN_NOTICIAS)) + " ...";
		}
		
		return result;
	}
	
	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        NoticiaVO clone = new NoticiaVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getTitulo() != null && !this.getTitulo().isEmpty()){
        	clone.setTitulo(new String(this.getTitulo()));
        }
        if(this.getContenido() != null && !this.getContenido().isEmpty()){
        	clone.setContenido(new String(this.getContenido()));
        }
        if(this.getFecha() != null){
        	clone.setFecha((Date) this.getFecha().clone());
        }
        if(this.getImagenNoticia() != null){
        	clone.setImagenNoticia(this.getImagenNoticia().clone());
        }
        if(this.getStyleClass() != null && !this.getStyleClass().isEmpty()){
        	clone.setStyleClass(new String(this.getStyleClass()));
        }
        
        return clone;
    }
	
	@Override
	public boolean equals(Object obj){
		Boolean result = false;
		
		if(obj != null){
			NoticiaVO noticia = (NoticiaVO) obj;
			if((noticia.getId() == null && this.id == null) ||
					noticia.getId().compareTo(this.id) == 0){
				result = true;
			}
		}
		
		return result;
	}
}