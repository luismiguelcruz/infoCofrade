package com.infoCofrade.common.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean(name="booleanConverter")
public class BooleanConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			if(Boolean.valueOf(value.toString()) != null){
				if(Boolean.valueOf(value.toString())){
					result.append("Sí");
				} else {
					result.append("No");
				}
			}
		}

		return result.toString();
	}
}