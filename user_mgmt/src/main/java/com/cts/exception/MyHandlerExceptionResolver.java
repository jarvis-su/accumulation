package com.cts.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        ModelAndView mv = new ModelAndView("exception");
        mv.addObject("msg", ex.getMessage());

        if (ex instanceof ParameterException) {
            logger.error("ParameterException: " + ex.getMessage());
        } else if (ex instanceof BusinessException) {
            logger.error("BusinessException: " + ex.getMessage());
        } else {
            logger.error(ex.getMessage());
        }

        return mv;
    }

}
