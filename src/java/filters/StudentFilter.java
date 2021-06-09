/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import config.SessionData;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author leon
 */
@WebFilter("/student/*")
public class StudentFilter implements Filter {

    private Boolean set = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init filters");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("filter 1 1 1 1 student");
        request.setCharacterEncoding("UTF-8");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String loginURL = req.getContextPath() + "/account/login.xhtml";
        String errorURL = req.getContextPath() + "/error.xhtml";
        
        if (session == null) {
            chain.doFilter(request, response);
            resp.sendRedirect(loginURL);
            return;
        }
        SessionData sessionData = (SessionData) session.getAttribute("sessionData");
        if (sessionData == null || sessionData.getUser() == null) {
            resp.sendRedirect(loginURL);
        } else if (!sessionData.getUser().type.equals("student")) {
            resp.sendRedirect(errorURL);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
