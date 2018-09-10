package io.anhkhue.jwtauthoritiesbased.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static io.anhkhue.jwtauthoritiesbased.security.SecurityConstants.*;

public class JWTVerifier extends BasicAuthenticationFilter {

    public JWTVerifier(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader(HEADER_STRING);

        if (tokenHeader != null) {
            String token = tokenHeader.replace(TOKEN_PREFIX, "");

            String username = getDecodedJWT(token).getSubject();

            if (username != null) {
                Collection<GrantedAuthority> authorities = Arrays.stream(getDecodedJWT(token).getClaim(AUTHORITIES_KEY)
                                                                                             .asString()
                                                                                             .split(","))
                                                                 .map(authority -> (GrantedAuthority) () -> authority)
                                                                 .collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(username,
                                                               null,
                                                               authorities);
            }
        }

        return null;
    }

    private DecodedJWT getDecodedJWT(String token) {
        return JWT.require(Algorithm.HMAC512(JWT_SECRET.getBytes()))
                  .build()
                  .verify(token);
    }
}
