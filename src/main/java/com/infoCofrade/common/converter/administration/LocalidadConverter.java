package com.infoCofrade.common.converter.administration;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.administration.localidad.LocalidadBean;
import com.infoCofrade.administration.localidad.bo.LocalidadBO;
import com.infoCofrade.administration.localidad.vo.LocalidadVO;

@ManagedBean(name="localidadConverter")
public class LocalidadConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		LocalidadVO localidad = new LocalidadVO();
		LocalidadBean localidadBean = (LocalidadBean) FacesContext
				.getCurrentInstance()
				.getELContext()
				.getELResolver()
				.getValue(FacesContext.getCurrentInstance().getELContext(),
						null, "localidadBean");
		
		LocalidadBO localidadBO = localidadBean.getLocalidadBO();
		
		LocalidadVO localidadSearch = new LocalidadVO();
		localidadSearch.setLocalidad(value);
		
		List<LocalidadVO> listAux = localidadBO.findUsingExactTemplate(null, localidadSearch, null);
		
		if(listAux.size() > 0){
			localidad = listAux.get(0);
		}
		
		return localidad;
	}

	/**
	 * Devuelve la localidad asociada al identificador de localidad seleccionado
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			if(value instanceof Long){
				Long idLocalidad = Long.valueOf(value.toString());
				LocalidadVO localidad;
				
				LocalidadBean localidadBean = (LocalidadBean) FacesContext
						.getCurrentInstance()
						.getELContext()
						.getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "localidadBean");
				LocalidadBO localidadBO = localidadBean.getLocalidadBO();
	
				localidad = localidadBO.findByPrimaryKey(null, idLocalidad);
				if(localidad != null){
					result.append(localidad.getLocalidad());
				}
			} else if (value instanceof LocalidadVO){
				if(((LocalidadVO) value).getLocalidad() != null){
					result = new StringBuilder(((LocalidadVO)value).getLocalidad());
				}
			}
		}

		return result.toString();
	}
}