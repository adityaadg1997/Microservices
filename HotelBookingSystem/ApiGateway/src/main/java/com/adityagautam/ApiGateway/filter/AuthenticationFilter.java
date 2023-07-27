package com.adityagautam.ApiGateway.filter;


import com.adityagautam.ApiGateway.exception.HeaderNotFoundException;
import com.adityagautam.ApiGateway.exception.UnAuthorizedException;
import com.adityagautam.ApiGateway.utils.JwtUtil;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    HttpServletResponse response;

    Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.info("Missing Authorization Header");
                     throw new HeaderNotFoundException("Missing Authorization Header");

                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);

                } catch (Exception exception) {
                    logger.info("invalid access...!  : {}", exception.getMessage());
                    throw new UnAuthorizedException("UnAuthorized Access to Application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config{
    }
}
