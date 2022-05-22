package VTTP.Project.VTTP_Project_One.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import VTTP.Project.VTTP_Project_One.models.User;

public class AuthenticationFilter implements Filter{

    //@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                
        HttpServletRequest httpReq = (HttpServletRequest)request;
        HttpServletResponse httpResp = (HttpServletResponse)response;

        // Get the HTTP session
        HttpSession session = httpReq.getSession();
        User user = (User)session.getAttribute("user");
        String username = "";
        if(user!=null){
            username = user.getUsername();
        }

        if ((null == username) || (username.trim().length() <= 0)) {
            httpResp.sendRedirect("/");
            return;
        }

        chain.doFilter(request, response);
    }
    
}