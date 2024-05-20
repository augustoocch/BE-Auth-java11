package com.augustoocc.demo.globant.security;

import javax.servlet.FilterChain;

import com.augustoocc.demo.globant.service.impl.UserDetailedService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailedService userDetailsService;

    public JwtFilter(JwtService jwtService, UserDetailedService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String jwt = getToken(request);
        if (jwt== null) {
            filterChain.doFilter(request, response);
            return;
        }

        String userNameFromJwt = this.jwtService.getUsernameFromToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userNameFromJwt);

        if(!this.jwtService.isTokenValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header == null) {
            return header;
        }
        if (!header.startsWith("Bearer")) {
            return header;
        }
        return header.replace("Bearer", "");
    }

}
