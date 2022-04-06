package com.rgr.storeApp.secutity.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgr.storeApp.dto.LoginResponse;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.UserDetailsServiceImpl;
import com.rgr.storeApp.service.profile.RefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenFilterImpl extends OncePerRequestFilter {

    private final JwtBuilder jwtBuilder;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshService refreshService;


    @Autowired
    public TokenFilterImpl(JwtBuilder jwtBuilder, UserDetailsServiceImpl userDetailsService, RefreshService refreshService) {
        this.jwtBuilder = jwtBuilder;
        this.userDetailsService = userDetailsService;
        this.refreshService = refreshService;
    }

    private String getTokenFromRequest(HttpServletRequest request, HttpServletResponse response){
        Cookie [] cookies = request.getCookies();
        String accessToken = null;
        String refreshToken = null;
        if(cookies == null){
            return null;
        }
        for (Cookie c : cookies){
            if(c.getName().equals("access_token")){
                accessToken = c.getValue();
            }
            if(c.getName().equals("refresh_token")){
                refreshToken = c.getValue();
            }
        }
        if(accessToken == null){return null;}
        if(jwtBuilder.validateToken(accessToken)){
            return accessToken;
        }
        System.out.println("HELLO");
        if(refreshToken != null){
            LoginResponse loginResponse = refreshService.refresh(refreshToken, response);
            return loginResponse.getAccess_token();
        }
        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            try {
                if (!request.getServletPath().startsWith("/api/auth/")){
                    String token = getTokenFromRequest(request, response);//add in cookie
                    if (token != null) {
                        String email = jwtBuilder.getEmailFromToken(token);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource()
                                        .buildDetails(request));
                        SecurityContextHolder.getContext()
                                .setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            } catch (Exception e) {
                Map<String, String> errors = new HashMap<>();
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                errors.put("error", e.getMessage());
                new ObjectMapper().writeValue(response.getOutputStream(), errors);

            }
            filterChain.doFilter(request, response);

        } catch (IOException e) {
            //log this
        } catch (ServletException e) {
            //log this
        }

    }
}