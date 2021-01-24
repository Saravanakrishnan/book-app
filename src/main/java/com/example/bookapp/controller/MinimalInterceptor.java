package com.example.bookapp.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.bookapp.util.Constants.SESSION_CART_KEY;

public class MinimalInterceptor implements HandlerInterceptor {
    
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) {
        Object attribute = request.getAttribute(SESSION_CART_KEY);
        if (ObjectUtils.isEmpty(attribute)) {
            request.setAttribute(SESSION_CART_KEY, "SK-1");
        }
    }
    
}
