package com.infoCofrade.common.bean;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionExpiredListener extends AbstractBean implements HttpSessionListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void sessionDestroyed(HttpSessionEvent evt) {
		HttpSession session = evt.getSession();
		session.removeAttribute("authenticated");
		System.out.println("La sesión del usuario ha caducado");
		//System.err.println(evt.getSession().getId());
	}

	@Override
	public void sessionCreated(HttpSessionEvent evt) {
		System.out.println("Creando sesión del usuario");
		//System.err.println(evt.getSession().getId());
	}

	@Override
	public String doNavigate() {
		return null;
	}
}