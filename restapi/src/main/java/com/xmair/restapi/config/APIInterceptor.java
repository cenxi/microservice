package com.xmair.restapi.config;

import com.xmair.core.util.MemCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class APIInterceptor implements HandlerInterceptor {
    // 类名.class
    Logger logger = LoggerFactory.getLogger(APIInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean islogin = false;


        Object pcode = request.getSession().getAttribute("pcode");
        if (pcode == null) {//未登陆

            String strBackUrl = "http://" + request.getServerName() //服务器地址
                    + ":"
                    + request.getServerPort()           //端口号
                    + request.getContextPath()      //项目名称
                    + request.getServletPath();      //请求页面或其他地址
        if(request.getQueryString()!=null) {
           strBackUrl+="?" + (request.getQueryString()); //参数
        }
            MemCacheUtil.set(request.getSession().getId(), strBackUrl);
            //登录未登陆跳转到登陆页
            response.sendRedirect("/login/login");
            logger.info(strBackUrl);

        } else {
            islogin = true;
        }

        return islogin;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
