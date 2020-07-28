package com.nineclient.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpOperator;
import com.nineclient.utils.CommonUtils;

public class UmpInterceptor implements HandlerInterceptor {
	private final Logger log = LoggerFactory.getLogger(UmpInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		UmpOperator userUmp = (UmpOperator) request.getSession().getAttribute(
				"umpOperator");
		PubOperator userPub = (PubOperator) request.getSession().getAttribute(
				"pubOperator");
		if (userUmp == null && userPub == null) {
			System.out.println("url======================"+request.getRequestURI());
			if (request.getRequestURI().equals("/login.jspx")
					|| request.getRequestURI().equals("/ump/umps")
					|| request.getRequestURI().equals("/ump/companyActivation")
					|| request.getRequestURI().equals("/ump/register")
					|| request.getRequestURI().equals("/ump/forgetPass")
					|| request.getRequestURI().endsWith(".css")
					|| request.getRequestURI().endsWith(".js")
					|| request.getRequestURI().endsWith(".swf")
					|| request.getRequestURI().endsWith(".xml")
					|| request.getRequestURI().endsWith(".png")
					|| request.getRequestURI().endsWith(".jpg")
					|| request.getRequestURI().endsWith(".mp4")
					|| request.getRequestURI().endsWith(".gif")
					|| request.getRequestURI().equals(
							"/ump/umpcompanys/getUmpVersionByProduct")
					|| request.getRequestURI().equals(
							"/ump/umpcompanys/getUmpChanelByProduct")
					|| request.getRequestURI().equals(
							"/ump/umpcompanys/treeJson")
					|| request.getRequestURI().equals("/ump/checkCompanyCode")
					|| request.getRequestURI().equals("/ump/companyRegister")
					|| request.getRequestURI().equals("/ump/tmallRegister")
					|| request.getRequestURI().equals("/ump/checkEmail")
					|| request.getRequestURI().equals("/ump/checkValue")
					|| request.getRequestURI().equals("/ump/verCode")
					|| request.getRequestURI().equals("/ump/checkCode")
					|| request.getRequestURI().equals("/ump/resetSuccess")
					|| request.getRequestURI().contains("/ump/wxController/")
					|| request.getRequestURI().contains("/ump/wxCommController/")
					|| request.getRequestURI().contains("/ump/page")//取消手机端拦截url页面
					|| request.getRequestURI().equals(
							"/ump/umpoperators/checkValue")
					|| request.getRequestURI().contains("/ump/attached/image")
					|| request.getRequestURI().contains("/ump/activationMail")
					|| request.getRequestURI().contains(
							"/wccmaterialses/showdetail/")
					|| request.getRequestURI().contains("wcclotteryactivitys")
					|| request.getRequestURI().contains("open.weixin.qq.com/connect/oauth2/authorize")
					|| request.getRequestURI().contains("jsessionid")
					|| request.getRequestURI().contains("wcclotteryactivitys")
					|| request.getRequestURI().contains("/ump/vocaccounts/callBack")
					|| request.getRequestURI().contains("/umpcompany/tmallRegister")){
				log.info("===1==");
				return true;
			} else {
				log.info("===2==");
				response.sendRedirect(request.getContextPath() + "/login.jspx");
				return false;
			}
		} else {
			//判断request里面是否存在displayId 如果存在 放入session
			if (request.getParameter("displayId") != null) {
				Long displayId = Long.parseLong(request
						.getParameter("displayId"));
				if (displayId == null) {
					displayId = CommonUtils.getCurrentDisPlayId(request);
				}
				request.getSession().setAttribute("displayId", displayId);
			}
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
