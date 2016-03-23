package com.jarvis.demo.gui.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Jarvis on 3/23/16.
 */
public class XssSecurityFilter implements Filter {

    @SuppressWarnings("unused")
    private FilterConfig filterConfig;

    public void destroy() {
        this.filterConfig = null;

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new CrossSiteScriptWrapper((HttpServletRequest) request), response);

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;

    }
}
