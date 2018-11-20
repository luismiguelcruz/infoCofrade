package com.infoCofrade.secretaria.hermano.vo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class HermanoVO implements Cloneable {
	private Long id;
	private Long numHermano;
	private Long numIngreso;
	private String nombre;
	private String apellidos;
	private String nif;
	private Long idTipoVia;
	private Long idCalle;
	private String nombreVia;
	private String numKm;
	private String bloque;
	private String escalera;
	private String piso;
	private String puerta;
	private Long idLocalidad;
	private Long idSector;
	private Date fechaNacimiento;
	private String telefonoFijo;
	private String telefonoMovil;
	private String email;
	private String nombrePadre;
	private String nombreMadre;
	private String nombreTutor;
	private String lugarBautismo;
	private Date fechaBautismo;
	private String profesion;
	private String libro;
	private Date fechaSolicitud;
	private Date fechaJura;
	private Date fechaBaja;
	private String motivoBaja;
	private Long idCalleCobro;
	private Long idTipoViaCobro;
	private String nombreViaCobro;
	private String numKmCobro;
	private String bloqueCobro;
	private String escaleraCobro;
	private String pisoCobro;
	private String puertaCobro;
	private Long idLocalidadCobro;
	private String observaciones;
	private Long idMotivoBaja;
	
	// Campos rellenables no guardables en base de datos
	private String provincia;
	private Integer codigoPostal;
	private String provinciaCobro;
	private Integer codigoPostalCobro;
	private Boolean cuotaPagada;
	private Boolean generarCuotaAnual;
	private Boolean checked;
	
	
	public HermanoVO(){
		this.id = null;
		this.numHermano = null;
		this.numIngreso = null;
		this.nombre = null;
		this.apellidos = null;
		this.nif = null;
		this.idCalle = null;
		this.idTipoVia = null;
		this.nombreVia = null;
		this.numKm = null;
		this.bloque = null;
		this.escalera = null;
		this.piso = null;
		this.puerta = null;
		this.idLocalidad = null;
		this.fechaNacimiento = null;
		this.telefonoFijo = null;
		this.telefonoMovil = null;
		this.email = null;
		this.nombrePadre = null;
		this.nombreMadre = null;
		this.nombreTutor = null;
		this.lugarBautismo = null;
		this.fechaBautismo = null;
		this.profesion = null;
		this.libro = null;
		this.fechaSolicitud = null;
		this.fechaJura = null;
		this.fechaBaja = null;
		this.motivoBaja = null;
		this.idSector = null;
		this.idCalleCobro = null;
		this.idTipoViaCobro = null;
		this.nombreViaCobro = null;
		this.numKmCobro = null;
		this.bloqueCobro = null;
		this.escaleraCobro = null;
		this.pisoCobro = null;
		this.puertaCobro = null;
		this.idLocalidadCobro = null;
		this.observaciones = null;
		this.idMotivoBaja = null;
		
		this.provincia = null;
		this.codigoPostal = null;
		this.provinciaCobro = null;
		this.codigoPostalCobro = null;
		this.cuotaPagada = null;
		this.generarCuotaAnual = null;
		this.checked = null;
	}
	
	//-------------------- GETTERS / SETTERS -------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getNumHermano() {
		return numHermano;
	}

	public void setNumHermano(Long numHermano) {
		this.numHermano = numHermano;
	}

	public Long getNumIngreso() {
		return numIngreso;
	}

	public void setNumIngreso(Long numIngreso) {
		this.numIngreso = numIngreso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public Long getIdCalle() {
		return idCalle;
	}

	public void setIdCalle(Long idCalle) {
		this.idCalle = idCalle;
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

	public String getNumKm() {
		return numKm;
	}

	public void setNumKm(String numKm) {
		this.numKm = numKm;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombrePadre() {
		return nombrePadre;
	}

	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}

	public String getNombreMadre() {
		return nombreMadre;
	}

	public void setNombreMadre(String nombreMadre) {
		this.nombreMadre = nombreMadre;
	}

	public String getNombreTutor() {
		return nombreTutor;
	}

	public void setNombreTutor(String nombreTutor) {
		this.nombreTutor = nombreTutor;
	}

	public String getLugarBautismo() {
		return lugarBautismo;
	}

	public void setLugarBautismo(String lugarBautismo) {
		this.lugarBautismo = lugarBautismo;
	}

	public Date getFechaBautismo() {
		return fechaBautismo;
	}

	public void setFechaBautismo(Date fechaBautismo) {
		this.fechaBautismo = fechaBautismo;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getLibro() {
		return libro;
	}

	public void setLibro(String libro) {
		this.libro = libro;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaJura() {
		return fechaJura;
	}

	public void setFechaJura(Date fechaJura) {
		this.fechaJura = fechaJura;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}
	
	public Long getIdCalleCobro() {
		return idCalleCobro;
	}

	public void setIdCalleCobro(Long idCalleCobro) {
		this.idCalleCobro = idCalleCobro;
	}

	public Long getIdTipoViaCobro() {
		return idTipoViaCobro;
	}

	public void setIdTipoViaCobro(Long idTipoViaCobro) {
		this.idTipoViaCobro = idTipoViaCobro;
	}

	public String getNombreViaCobro() {
		return nombreViaCobro;
	}

	public void setNombreViaCobro(String nombreViaCobro) {
		this.nombreViaCobro = nombreViaCobro;
	}

	public String getNumKmCobro() {
		return numKmCobro;
	}

	public void setNumKmCobro(String numKmCobro) {
		this.numKmCobro = numKmCobro;
	}

	public String getBloqueCobro() {
		return bloqueCobro;
	}

	public void setBloqueCobro(String bloqueCobro) {
		this.bloqueCobro = bloqueCobro;
	}

	public String getEscaleraCobro() {
		return escaleraCobro;
	}

	public void setEscaleraCobro(String escaleraCobro) {
		this.escaleraCobro = escaleraCobro;
	}

	public String getPisoCobro() {
		return pisoCobro;
	}

	public void setPisoCobro(String pisoCobro) {
		this.pisoCobro = pisoCobro;
	}

	public String getPuertaCobro() {
		return puertaCobro;
	}

	public void setPuertaCobro(String puertaCobro) {
		this.puertaCobro = puertaCobro;
	}

	public Long getIdLocalidadCobro() {
		return idLocalidadCobro;
	}

	public void setIdLocalidadCobro(Long idLocalidadCobro) {
		this.idLocalidadCobro = idLocalidadCobro;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Long getIdMotivoBaja() {
		return idMotivoBaja;
	}

	public void setIdMotivoBaja(Long idMotivoBaja) {
		this.idMotivoBaja = idMotivoBaja;
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
	
	public String getProvinciaCobro() {
		return provinciaCobro;
	}

	public void setProvinciaCobro(String provinciaCobro) {
		this.provinciaCobro = provinciaCobro;
	}

	public Integer getCodigoPostalCobro() {
		return codigoPostalCobro;
	}

	public void setCodigoPostalCobro(Integer codigoPostalCobro) {
		this.codigoPostalCobro = codigoPostalCobro;
	}

	public Boolean getCuotaPagada() {
		return cuotaPagada;
	}

	public void setCuotaPagada(Boolean cuotaPagada) {
		this.cuotaPagada = cuotaPagada;
	}

	public Boolean getGenerarCuotaAnual() {
		return generarCuotaAnual;
	}

	public void setGenerarCuotaAnual(Boolean generarCuotaAnual) {
		this.generarCuotaAnual = generarCuotaAnual;
	}
	
	public Integer getEdad(){
		Integer edad = null;
		if(this.getFechaNacimiento() != null){
			Calendar fechaNacimientoCalendar = new GregorianCalendar();
			Calendar currentCalendar = new GregorianCalendar();
			
			fechaNacimientoCalendar.setTime(this.getFechaNacimiento());
			currentCalendar.setTime(new Date());
			
			edad = currentCalendar.get(Calendar.YEAR)-fechaNacimientoCalendar.get(Calendar.YEAR);
		}
		
		return edad;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	

	// ----------------- FUNCIONES ADICIONALES -----------------
	public Object clone()
    {
        HermanoVO clone = new HermanoVO();
        
        if(this.getId() != null){
        	clone.setId(new Long(this.getId()));
        }
        if(this.getNumHermano() != null){
        	clone.setNumHermano(new Long(this.getNumHermano()));
        }
        if(this.getNumIngreso() != null){
        	clone.setNumIngreso(new Long(this.getNumIngreso()));
        }
        if(this.getNombre() != null && !this.getNombre().isEmpty()){
        	clone.setNombre(new String(this.getNombre()));
        }
        if(this.getApellidos() != null && !this.getApellidos().isEmpty()){
        	clone.setApellidos(new String(this.getApellidos()));
        }
        if(this.getNif() != null && !this.getNif().isEmpty()){
        	clone.setNif(new String(this.getNif()));
        }
        if(this.getIdCalle() != null){
        	clone.setIdCalle(new Long(this.getIdCalle()));
        }
        if(this.getIdTipoVia() != null){
        	clone.setIdTipoVia(new Long(this.getIdTipoVia()));
        }
        if(this.getNombreVia() != null && !this.getNombreVia().isEmpty()){
        	clone.setNombreVia(new String(this.getNombreVia()));
        }
        if(this.getNumKm() != null && !this.getNumKm().isEmpty()){
        	clone.setNumKm(new String(this.getNumKm()));
        }
        if(this.getBloque() != null && !this.getBloque().isEmpty()){
        	clone.setBloque(new String(this.getBloque()));
        }
        if(this.getEscalera() != null && !this.getEscalera().isEmpty()){
        	clone.setEscalera(new String(this.getEscalera()));
        }
        if(this.getPiso() != null && !this.getPiso().isEmpty()){
        	clone.setPiso(new String(this.getPiso()));
        }
        if(this.getPuerta() != null && !this.getPuerta().isEmpty()){
        	clone.setPuerta(new String(this.getPuerta()));
        }
        if(this.getIdLocalidad() != null){
        	clone.setIdLocalidad(new Long(this.getIdLocalidad()));
        }
        if(this.getFechaNacimiento() != null){
        	clone.setFechaNacimiento((Date)this.getFechaNacimiento().clone());
        }
        if(this.getTelefonoFijo() != null && !this.getTelefonoFijo().isEmpty()){
        	clone.setTelefonoFijo(new String(this.getTelefonoFijo()));
        }
        if(this.getTelefonoMovil() != null && !this.getTelefonoMovil().isEmpty()){
        	clone.setTelefonoMovil(new String(this.getTelefonoMovil()));
        }
        if(this.getEmail() != null && !this.getEmail().isEmpty()){
        	clone.setEmail(new String(this.getEmail()));
        }
        if(this.getNombrePadre() != null && !this.getNombrePadre().isEmpty()){
        	clone.setNombrePadre(new String(this.getNombrePadre()));
        }
        if(this.getNombreMadre() != null && !this.getNombreMadre().isEmpty()){
        	clone.setNombreMadre(new String(this.getNombreMadre()));
        }
        if(this.getNombreTutor() != null && !this.getNombreTutor().isEmpty()){
        	clone.setNombreTutor(new String(this.getNombreTutor()));
        }
        if(this.getLugarBautismo() != null && !this.getLugarBautismo().isEmpty()){
        	clone.setLugarBautismo(new String(this.getLugarBautismo()));
        }
        if(this.getFechaBautismo() != null){
        	clone.setFechaBautismo((Date)this.getFechaBautismo().clone());
        }
        if(this.getProfesion() != null && !this.getProfesion().isEmpty()){
        	clone.setProfesion(new String(this.getProfesion()));
        }
        if(this.getLibro() != null && !this.getLibro().isEmpty()){
        	clone.setLibro(new String(this.getLibro()));
        }
        if(this.getFechaSolicitud() != null){
        	clone.setFechaSolicitud((Date)this.getFechaSolicitud().clone());
        }
        if(this.getFechaJura() != null){
        	clone.setFechaJura((Date)this.getFechaJura().clone());
        }
        if(this.getFechaBaja() != null){
        	clone.setFechaBaja((Date)this.getFechaBaja().clone());
        }
        if(this.getMotivoBaja() != null && !this.getMotivoBaja().isEmpty()){
        	clone.setMotivoBaja(new String(this.getMotivoBaja()));
        }
        if(this.getIdSector() != null){
        	clone.setIdSector(new Long(this.getIdSector()));
        }
        if(this.getIdCalleCobro() != null){
        	clone.setIdCalleCobro(new Long(this.getIdCalleCobro()));
        }
        if(this.getIdTipoViaCobro() != null){
        	clone.setIdTipoViaCobro(new Long(this.getIdTipoViaCobro()));
        }
        if(this.getNombreViaCobro() != null && !this.getNombreViaCobro().isEmpty()){
        	clone.setNombreViaCobro(new String(this.getNombreViaCobro()));
        }
        if(this.getNumKmCobro() != null && !this.getNumKmCobro().isEmpty()){
        	clone.setNumKmCobro(new String(this.getNumKmCobro()));
        }
        if(this.getBloqueCobro() != null && !this.getBloqueCobro().isEmpty()){
        	clone.setBloqueCobro(new String(this.getBloqueCobro()));
        }
        if(this.getEscaleraCobro() != null && !this.getEscaleraCobro().isEmpty()){
        	clone.setEscaleraCobro(new String(this.getEscaleraCobro()));
        }
        if(this.getPisoCobro() != null && !this.getPisoCobro().isEmpty()){
        	clone.setPisoCobro(new String(this.getPisoCobro()));
        }
        if(this.getPuertaCobro() != null && !this.getPuertaCobro().isEmpty()){
        	clone.setPuertaCobro(new String(this.getPuertaCobro()));
        }
        if(this.getIdLocalidadCobro() != null){
        	clone.setIdLocalidadCobro(new Long(this.getIdLocalidadCobro()));
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
			HermanoVO hermano = (HermanoVO) obj;
			if((hermano.getId() == null && this.id == null) ||
					hermano.getId().compareTo(this.id) == 0 ||
					hermano.getNif().compareTo(this.getNif()) == 0){
				result = true;
			}
		}
		
		return result;
	}
}