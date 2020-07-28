package com.nineclient.web;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nineclient.model.UmpOperator;
public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hsq = (HttpServletRequest)req;
		UmpOperator user = (UmpOperator) hsq.getSession().getAttribute("loginOperator");  
		if(user == null) {
			System.out.println("=======");
			((HttpServletResponse)resp).sendRedirect(hsq.getContextPath()+"/login");
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
	}

}
