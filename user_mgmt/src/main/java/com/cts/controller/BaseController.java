package com.cts.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cts.exception.BusinessException;
import com.cts.exception.ParameterException;

@Controller
public class BaseController {

    Log logger = LogFactory.getLog(BaseController.class);

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpSession session;

    // @ExceptionHandler
    public String handlerException(HttpServletRequest request, Exception e) {

        if (e instanceof ParameterException) {
            logger.error("parameter exception");
        } else if (e instanceof BusinessException) {
            logger.error("business exception");
        }

        return "exception";
    }
}
