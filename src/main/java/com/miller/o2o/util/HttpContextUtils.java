package com.miller.o2o.util;

import com.miller.o2o.entity.PersonInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by miller on 2018/10/6
 * http工具类 线程安全
 * @author Miller
 */
public class HttpContextUtils {

    /**
     * 获取当前线程的Request
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前线程的Response
     * @return
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取当前线程的Session对象
     * @return
     */
    public static HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }

    /**
     * 获取当前线程的登陆用户 未登录返回Null
     * @return
     */
    public static PersonInfo getCurrentUser() {
        Object persion = getHttpSession().getAttribute("");
        if (persion == null) {
            return null;
        }
        return (PersonInfo) persion;
    }
}
