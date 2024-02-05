package com.product.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class SuperUserFilter extends AbstractGatewayFilterFactory<SuperUserFilter.Config> {

    public SuperUserFilter() {
        super(Config.class);
    }

    @Autowired
    private CheckPermissions checkPermissions;

    @Override
    public GatewayFilter apply(Config config) {
        return checkPermissions.getRoles("ROLE_ADMIN");
    }

    public static class Config {

    }
}
