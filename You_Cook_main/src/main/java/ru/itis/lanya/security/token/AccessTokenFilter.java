package ru.itis.lanya.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.lanya.service.redis.JwtBlacklistService;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("A-TOKEN");
        if (accessToken != null){
            if(jwtBlacklistService.existsAccessToken(accessToken)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            TokenAuthentication tokenAuthentication = new TokenAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }
        filterChain.doFilter(request,response);
    }
}
