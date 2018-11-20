package com.infoCofrade.administration.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.infoCofrade.administration.userView.vo.UserViewVO;

public class LoginFilter implements Filter {
	private static final String initPage = "index";
	private static final String errorPage = "pages/error";
	private static final String sessionExpiredPage = "pages/sessionexpired";
	
	private String cookieUserID = "userSession";
	public static final int expireSessionTimeInSeconds = 3600;

	public LoginFilter() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(false);
		if(session == null){
			session = req.getSession(true);
			session.removeAttribute("authenticated");
			
	        Cookie userCookie = new Cookie(cookieUserID, session.getId());
	        userCookie.setMaxAge(expireSessionTimeInSeconds);
	        userCookie.setPath("/");
	        
	        res.addCookie(userCookie);
	        
	        // Redirect to the login page
			res.sendRedirect(req.getContextPath() + "/" + initPage);
		} else {
			String[] destinyKeyArray = req.getRequestURI().split("/");
			String destinyKey = null;
			if (destinyKeyArray.length > 2) {
				destinyKey = destinyKeyArray[destinyKeyArray.length - 2] + "/"
						+ destinyKeyArray[destinyKeyArray.length - 1];
			}
	
			// If has permissions, continue the request
			if (hasPermissions(req, res, session, destinyKey)) {
				if (session != null && !session.isNew()) {
					chain.doFilter(request, res);
				} else {
					// Redirect to the login page
					res.sendRedirect(req.getContextPath() + "/" + initPage);
				}
			} else {
				// If not has permissions and the user is authenticated, redirect to
				// the error page
				if (session.getAttribute("authenticated") != null
						&& destinyKey != null
						&& LoginBean.getMapUserPermission() != null
						&& !destinyKey.startsWith("javax.faces.resource")
						&& !LoginBean.getMapUserPermission()
								.containsKey(destinyKey)) {
					res.sendRedirect(req.getContextPath() + "/" + errorPage);
				} else {
					// Redirect to the login page
					res.sendRedirect(req.getContextPath() + "/" + initPage);
				}
			}
		}
	}

	private Boolean hasPermissions(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			String destinyKey) {
		Boolean hasPermissions = false;
		UserViewVO loggedUser = (UserViewVO)session.getAttribute("authenticated");

		// If the user is the super admin, he has all permissions
		if (session.getAttribute("authenticated") != null
				&& ((UserViewVO) session.getAttribute("authenticated"))
						.getIdUserType().compareTo(new Long(-1)) == 0) {
			hasPermissions = true;
		} else if (req.getRequestURI().indexOf(".css") > 0
				|| req.getRequestURI().indexOf(".js") > 0
				|| req.getRequestURI().indexOf(".png") > 0
				|| req.getRequestURI().endsWith("index")
				|| req.getRequestURI().endsWith("indexSuperUser")
				|| req.getRequestURI().endsWith("error")
				|| req.getRequestURI().endsWith("sessionexpired")
				|| req.getRequestURI().endsWith("dynamiccontent.properties.xhtml")
				|| (session.getAttribute("authenticated") != null
						&& destinyKey != null
						&& loggedUser != null
						&& loggedUser.getMapUserPermission() != null
						&& !destinyKey.startsWith("javax.faces.resource") && loggedUser
						.getMapUserPermission().containsKey(destinyKey))) {
			hasPermissions = true;
		}
		
		if(session != null && loggedUser != null){
			// Update the cookie expiration time
			Cookie[] cookies = req.getCookies();
			if(cookies != null){
				if(!req.getRequestURI().endsWith("index")){
					Cookie userCookie = new Cookie(cookieUserID, session.getId());
			        userCookie.setMaxAge(expireSessionTimeInSeconds);
			        userCookie.setPath("/");
			        
			        res.addCookie(userCookie);
				}
			}
		}

		return hasPermissions;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}