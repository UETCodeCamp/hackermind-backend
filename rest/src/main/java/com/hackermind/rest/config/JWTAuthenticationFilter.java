package com.hackermind.rest.config;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private ObjectMapper mapper;
    private String currentRole;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.mapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        if (mapper == null) {
            this.mapper = new ObjectMapper();
        }
            //TODO - need implementation when user login
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            creds.getUsername(),
//                            creds.getPassword(),
//                            grantedAuths)
//            );
            return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        long expireTime;
        if ("PRODUCER".equals(currentRole) || "ADMIN".equals(currentRole) || "MANAGER".equals(currentRole)) {
            expireTime = System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME_ADMIN;
        } else {
            expireTime = System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME;
        }
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(expireTime))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));


        // response here
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        res.addHeader("Content-Type", "application/json");
        PrintWriter out = res.getWriter();
        Map<String, String> data = new HashMap<>();
        data.put(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        out.println(mapper.writeValueAsString(data));
        out.close();
        currentRole = "";
    }
}
