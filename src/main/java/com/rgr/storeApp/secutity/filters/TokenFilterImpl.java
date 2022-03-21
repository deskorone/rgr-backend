package com.rgr.storeApp.secutity.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.UserDetailsServiceImpl;
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


    @Autowired
    public TokenFilterImpl(JwtBuilder jwtBuilder, UserDetailsServiceImpl userDetailsService) {
        this.jwtBuilder = jwtBuilder;
        this.userDetailsService = userDetailsService;
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if(StringUtils.hasText(header) && header.startsWith("Bearer")){
            return header.substring(7);
        }
        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        // TODO maybe ignore authURL?
        try {
            try {
                String token = getTokenFromRequest(request);//add in cookie
                if (token != null && jwtBuilder.validateToken(token)) {
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