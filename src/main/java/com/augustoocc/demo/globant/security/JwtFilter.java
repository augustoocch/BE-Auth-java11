package com.augustoocc.demo.globant.security;

import javax.servlet.FilterChain;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        if(request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = getToken(request);
        if (jwt== null) {
            filterChain.doFilter(request, response);
            return;
        }

        if(!this.jwtService.validateToken(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }


        String userNameFromJwt = this.jwtService.getUsernameFromToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userNameFromJwt);
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
