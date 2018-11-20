package com.infoCofrade.common.converter.administration;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.administration.provincia.ProvinciaBean;
import com.infoCofrade.administration.provincia.bo.ProvinciaBO;
import com.infoCofrade.administration.provincia.vo.ProvinciaVO;

@ManagedBean(name="provinciaConverter")
public class ProvinciaConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * Devuelve la provincia asociada al identificador de provincia seleccionado
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			if(value instanceof Long){
				Long idProvincia = Long.valueOf(value.toString());
				ProvinciaVO provincia;
				
				ProvinciaBean provinciaBean = (ProvinciaBean) FacesContext
						.getCurrentInstance()
						.getELContext()
						.getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "provinciaBean");
				ProvinciaBO provinciaBO = provinciaBean.getProvinciaBO();
	
				provincia = provinciaBO.findByPrimaryKey(null, idProvincia);
				if(provincia != null){
					result.append(provincia.getProvincia());
				}
			} else if (value instanceof ProvinciaVO){
				if(((ProvinciaVO) value).getProvincia() != null){
					result = new StringBuilder(((ProvinciaVO)value).getProvincia());
				}
			}
		}

		return result.toString();
	}
}