    package com.GreenCart.GreenCart.security;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    public class SecurityConfig {


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .cors().and()
                    .csrf().disable()
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/pedidoitems/**").permitAll()
                            .requestMatchers("/login", "/registro", "/productos/**", "/categorias/**", "/uploads/**", "/pedidos/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .httpBasic().disable();

            return http.build();
        }
    }
