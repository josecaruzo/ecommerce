package br.com.fiap.msproduct.security.config;

import br.com.fiap.msproduct.security.authentication.CustomUserAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    public static final String ADMIN = "ADMIN";
    public static final String COMMON = "COMMON";
    private final CustomUserAuthenticationFilter authenticationFilter;

    public static final String[] ENDPOINTS_WITHOUT_AUTH = {
            "/api/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/index.html/**",
            "/swagger-ui/index.html",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/h2-console/**"
    };

    private static final String[] ENDPOINTS_WITH_ADMIN_AUTH = {
            "/product/createProduct",
            "/product/updateProduct/**",
            "/product/deleteProduct/**"
    };

    private static final String[] ENDPOINTS_WITH_COMMON_AUTH = {
            "/product/getProductById/**",
            "/product/getAllProducts"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(ENDPOINTS_WITHOUT_AUTH).permitAll();
                    request.requestMatchers(HttpMethod.GET, ENDPOINTS_WITH_COMMON_AUTH).hasAnyRole(ADMIN, COMMON);
                    request.requestMatchers(HttpMethod.POST, ENDPOINTS_WITH_ADMIN_AUTH).hasRole(ADMIN);
                    request.requestMatchers(HttpMethod.PUT, ENDPOINTS_WITH_ADMIN_AUTH).hasRole(ADMIN);
                    request.requestMatchers(HttpMethod.DELETE, ENDPOINTS_WITH_ADMIN_AUTH).hasRole(ADMIN);
                })
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
