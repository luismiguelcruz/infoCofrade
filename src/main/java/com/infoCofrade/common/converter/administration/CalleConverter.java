package com.infoCofrade.common.converter.administration;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.administration.secretaria.calle.CalleBean;
import com.infoCofrade.administration.secretaria.calle.bo.CalleBO;
import com.infoCofrade.administration.secretaria.calle.vo.CalleVO;

@ManagedBean(name="calleConverter")
public class CalleConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		CalleVO calle = new CalleVO();
		CalleBean calleBean = (CalleBean) FacesContext
				.getCurrentInstance()
				.getELContext()
				.getELResolver()
				.getValue(FacesContext.getCurrentInstance().getELContext(),
						null, "calleBean");
		
		CalleBO calleBO = calleBean.getCalleBO();
		
		CalleVO calleSearch = new CalleVO();
		calleSearch.setNombreVia(value);
		
		List<CalleVO> listAux = calleBO.findUsingTemplate(null, calleSearch, null);
		
		if(listAux.size() > 0){
			calle = listAux.get(0);
		}
		
		return calle;
	}

	/**
	 * Devuelve la calle asociada al identificador de calle seleccionado
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			if(value instanceof Long){
				Long idCalle = Long.valueOf(value.toString());
				CalleVO calle;
				
				CalleBean calleBean = (CalleBean) FacesContext
						.getCurrentInstance()
						.getELContext()
						.getELResolver()
						.getValue(FacesContext.getCurrentInstance().getELContext(),
								null, "calleBean");
				CalleBO calleBO = calleBean.getCalleBO();
	
				calle = calleBO.findByPrimaryKey(null, idCalle);
				if(calle != null){
					result.append(calle.getNombreVia());
				}
			} else if (value instanceof CalleVO){
				if(((CalleVO) value).getNombreVia() != null){
					result = new StringBuilder(((CalleVO)value).getNombreVia());
				}
			}
		}

		return result.toString();
	}
}