package com.ascending.mingqian.filter;

import com.ascending.mingqian.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebFilter(filterName = "LogFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class LogFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
   // private static String LOG_URI = "/log";

    @Override
    public void init(FilterConfig filterConfig){

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        int statusCode = authorization(req);
        if (statusCode == HttpServletResponse.SC_ACCEPTED) filterChain.doFilter(request, response);
        else ((HttpServletResponse)response).sendError(statusCode);
    }
    @Override
    public void destroy(){

    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_ACCEPTED;
        String uri = req.getRequestURI();
        String verb = req.getMethod();

        try {

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);

            logger.debug(String.format("Verb: %s, uri: %s, Date: " + formattedDate, verb,uri));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }


        return statusCode;
    }
}
