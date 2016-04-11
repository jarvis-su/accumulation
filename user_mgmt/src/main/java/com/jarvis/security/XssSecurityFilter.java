package com.jarvis.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Jarvis on 4/11/16.
 */
public class XssSecurityFilter implements Filter {


    @SuppressWarnings("unused")
    private FilterConfig filterConfig;

    @Override
    public void destroy() {
        this.filterConfig = null;

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new CrossSiteScriptWrapper((HttpServletRequest) request), response);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;

    }


}
