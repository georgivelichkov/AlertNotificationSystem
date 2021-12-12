package com.sap.configapi.config;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfigurationDefault;
import com.sap.cloud.security.xsuaa.XsuaaServicePropertySourceFactory;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import com.sap.cloud.security.xsuaa.token.authentication.XsuaaJwtDecoderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@EnableWebSecurity
@PropertySource(factory = XsuaaServicePropertySourceFactory.class, value = { "" })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final XsuaaServiceConfigurationDefault config;

    @Autowired
    public SecurityConfiguration(XsuaaServiceConfigurationDefault config) {
        this.config = config;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .sessionManagement()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(getJwtAuthenticationConverter());
    }

    private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        return new TokenAuthenticationConverter(config);
    }


    //BEANS
    @Bean
    public JwtDecoder jwtDecoder() {
        return new XsuaaJwtDecoderBuilder(config).build();
    }

    @Bean
    public XsuaaServiceConfigurationDefault config() {
        return new XsuaaServiceConfigurationDefault();
    }
}

