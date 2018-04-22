package com.lw.develop;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class CustomerSessionFilter
 */
public class CustomerSessionFilter implements Filter {

	private String setSession = "false";
	
    /**
     * Default constructor. 
     */
    public CustomerSessionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(setSession.equals("true")){
			HttpServletRequest hRequest = (HttpServletRequest) request;
			HttpSession session = hRequest.getSession();
			Enumeration<String> aNames = session.getAttributeNames();
			boolean hasSet = false;
			while(aNames.hasMoreElements()){
				String name = aNames.nextElement();
				if(name.equals("userId")){
					hasSet = true;
				}
			}
			//没有设置登录时的Session中属性就设置一个临时的，为了测其他内容，一直登录挺麻烦的
			if(!hasSet){
				session.setAttribute("username", "temp");
				session.setAttribute("userId",2);
				session.setAttribute("customerId",1);
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		setSession = fConfig.getInitParameter("setSession");
	}

}
