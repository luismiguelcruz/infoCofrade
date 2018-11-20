package com.infoCofrade.common.converter.administration.secretaria;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.administration.secretaria.status.StatusBean;
import com.infoCofrade.administration.secretaria.status.bo.StatusBO;
import com.infoCofrade.administration.secretaria.status.vo.StatusVO;

@ManagedBean(name="statusConverter")
public class StatusConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * Devuelve la status asociada al identificador de status seleccionado
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			Long idStatus = Long.valueOf(value.toString());
			StatusVO status;
			
			StatusBean statusBean = (StatusBean) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "statusBean");
			StatusBO statusBO = statusBean.getStatusBO();

			status = statusBO.findByPrimaryKey(null, idStatus);
			if(status != null){
				result.append(status.getNombre());
			}
		}

		return result.toString();
	}
}