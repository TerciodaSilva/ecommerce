package com.product.gateway.filter;

import com.product.gateway.exceptions.ForbbidenResourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter() {
        super(AuthenticationFilter.Config.class);
    }

    @Autowired
    private CheckPermissions checkPermissions;

    @Override
    public GatewayFilter apply(AuthenticationFilter.Config config) {
        return checkPermissions.getRoles("ROLE_USER");
    }

    public static class Config {

    }
}
