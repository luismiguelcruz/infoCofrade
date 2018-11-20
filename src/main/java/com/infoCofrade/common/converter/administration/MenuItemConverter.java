package com.infoCofrade.common.converter.administration;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.infoCofrade.administration.menuItem.MenuItemBean;
import com.infoCofrade.administration.menuItem.bo.MenuItemBO;
import com.infoCofrade.administration.menuItem.vo.MenuItemVO;

@ManagedBean(name="menuItemConverter")
public class MenuItemConverter implements Converter {
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
			Long idMenuItem = Long.valueOf(value.toString());
			MenuItemVO menuItem;
			
			MenuItemBean menuItemBean = (MenuItemBean) FacesContext
					.getCurrentInstance()
					.getELContext()
					.getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "menuItemBean");
			MenuItemBO menuItemBO = menuItemBean.getMenuItemBO();

			menuItem = menuItemBO.findByPrimaryKey(null, idMenuItem);
			if(menuItem != null){
				result.append(menuItem.getItemName());
			}
		}

		return result.toString();
	}
}