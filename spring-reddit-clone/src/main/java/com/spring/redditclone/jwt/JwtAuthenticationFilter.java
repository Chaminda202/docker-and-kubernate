package com.spring.redditclone.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.redditclone.config.JwtConfig;
import com.spring.redditclone.handler.CustomGlobalExceptionHandler;
import com.spring.redditclone.model.CustomErrorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final JwtConfig jwtConfig;
    private final CustomGlobalExceptionHandler customGlobalExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        try {
            if (StringUtils.hasText(jwt) && this.jwtProvider.validateToken(jwt)) {
                String username = this.jwtProvider.getUsernameJwt(jwt);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {

            ResponseEntity<CustomErrorDTO> responseEntity = this.customGlobalExceptionHandler.handleTokenExpiredException(e, request);

            //set the response object
            response.setStatus(responseEntity.getBody().getStatusCode());
            response.setContentType("application/json");
            response.setHeader("access-control-allow-headers", "*");
            response.setHeader("access-control-allow-methods", "*");
            response.setHeader("access-control-allow-origin", "*");

            //pass down the actual obj that exception handler normally send
            ObjectMapper mapper = new ObjectMapper();
            PrintWriter out = response.getWriter();
            out.print(mapper.writeValueAsString(responseEntity.getBody()));
            out.flush();
            return;
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(this.jwtConfig.getAuthorizationHeader());

        if (StringUtils.startsWithIgnoreCase(bearerToken, this.jwtConfig.getTokenPrefix())) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
