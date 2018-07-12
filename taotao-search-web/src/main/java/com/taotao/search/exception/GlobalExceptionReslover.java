package com.taotao.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionReslover implements HandlerExceptionResolver {
    //创建logger日志对象
    Logger logger = LoggerFactory.getLogger(GlobalExceptionReslover.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        /**
         * 做两个事情
         * 1.展示友好的页面
         * 2.记录日志
         */
        logger.error("报错了",e);//使用对象记录日志
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message","系统发生异常，请稍后重试");
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
