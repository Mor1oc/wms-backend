package com.warehouse.WMS.security.jwt;

import com.warehouse.WMS.security.SecurityConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;

//        logger.info("authHeader: {}", authHeader);
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

//        logger.info("token: {}", token);
        if(token != null && JwtUtil.isTokenValid(token)) {
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    JwtUtil.getClaims(token).getSubject(),
//                    null,
//                    Collections.emptyList() //roles & authorities
//            );
            String username = JwtUtil.getClaims(token).getSubject();
            List<SimpleGrantedAuthority> authorities = JwtUtil.getRoles(token).stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            logger.info("authorities: {}", authorities);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
