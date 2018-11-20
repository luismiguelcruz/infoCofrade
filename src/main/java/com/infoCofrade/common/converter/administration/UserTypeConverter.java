package com.infoCofrade.common.converter.administration;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.administration.userType.UserTypeBean;
import com.infoCofrade.administration.userType.bo.UserTypeBO;
import com.infoCofrade.administration.userType.vo.UserTypeVO;

@ManagedBean(name="userTypeConverter")
public class UserTypeConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * Return all the samples with the same individual
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StringBuilder result = new StringBuilder();

		if (value != null) {
			Long idUserType = Long.valueOf(value.toString());
			UserTypeVO userType;
			
			UserTypeBean userTypeBean = (UserTypeBean) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "userTypeBean");
			UserTypeBO userTypeBO = userTypeBean.getUserTypeBO();

			userType = userTypeBO.findByPrimaryKey(null, idUserType);
			if(userType != null){
				result.append(userType.getType());
			}
		}

		return result.toString();
	}
}