package com.free.decision.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 返回一个错误提示页面
     * @param msg
     * @return
     */
    protected ModelAndView errorTipView(String msg){
        ModelAndView modelAndView = new ModelAndView("/error");
        modelAndView.addObject("errMsg", msg);
        return modelAndView;
    }

    /**
     * 返回一个错误提示页面
     * @param msg
     * @param url
     * @return
     */
    protected ModelAndView errorTipView(String msg,String url){
        ModelAndView modelAndView = new ModelAndView("/error");
        modelAndView.addObject("errMsg", msg);
        modelAndView.addObject("errUrl", url);
        return modelAndView;
    }

    /**
     * Return a Object from session.
     * @param key a String specifying the key of the Object stored in session
     */
    protected <T> T getSessionAttr(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        return session != null ? (T)session.getAttribute(key) : null;
    }

    /**
     * Store Object to session.
     * @param key a String specifying the key of the Object stored in session
     * @param value a Object specifying the value stored in session
     */
    protected void setSessionAttr(HttpServletRequest request, String key, Object value) {
        request.getSession(true).setAttribute(key, value);
    }

    /**
     * Remove Object in session.
     * @param key a String specifying the key of the Object stored in session
     */
    protected void removeSessionAttr(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.removeAttribute(key);
    }

    protected HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    /**
     * Get cookie value by cookie name.
     */
    protected String getCookie(HttpServletRequest request, String name) {
        Cookie cookie = getCookieObject(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * Get cookie object by cookie name.
     */
    protected Cookie getCookieObject(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals(name))
                    return cookie;
        return null;
    }

    /**
     * Get all cookie objects.
     */
    protected Cookie[] getCookieObjects(HttpServletRequest request) {
        Cookie[] result = request.getCookies();
        return result != null ? result : new Cookie[0];
    }

    /**
     * Set Cookie to response.
     */
    protected BaseController setCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
        return this;
    }

    /**
     * Set Cookie to response.
     * @param name cookie name
     * @param value cookie value
     * @param maxAgeInSeconds -1: clear cookie when close browser. 0: clear cookie immediately.  n>0 : max age in n seconds.
     * @param path see Cookie.setPath(String)
     * @param domain the domain name within which this cookie is visible; form is according to RFC 2109
     * @param isHttpOnly true if this cookie is to be marked as HttpOnly, false otherwise
     */
    protected void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds, String path, String domain, boolean isHttpOnly) {
        doSetCookie(response, name, value, maxAgeInSeconds, path, domain, isHttpOnly);
    }

    /**
     * Remove Cookie.
     */
    protected void removeCookie(HttpServletResponse response, String name, String path, String domain) {
        doSetCookie(response, name, null, 0, path, domain, null);
    }

    private void doSetCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds, String path, String domain, Boolean isHttpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAgeInSeconds);
        // set the default path value to "/"
        if (path == null) {
            path = "/";
        }
        cookie.setPath(path);

        if (domain != null) {
            cookie.setDomain(domain);
        }
        if (isHttpOnly != null) {
            cookie.setHttpOnly(isHttpOnly);
        }
        response.addCookie(cookie);
    }
}
