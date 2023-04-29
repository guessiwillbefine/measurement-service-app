package ua.ms.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.ms.configuration.security.util.JWTFilter;
import ua.ms.entity.user.Role;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final String adminAuthority = Role.ADMIN.name();
    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/register").hasAuthority(adminAuthority)
                .requestMatchers(HttpMethod.DELETE, "/factories").hasAuthority(adminAuthority)
                .requestMatchers(HttpMethod.POST, "/factories").hasAuthority(adminAuthority)
                .requestMatchers(HttpMethod.DELETE, "/machines").hasAuthority(adminAuthority)
                .requestMatchers(HttpMethod.POST, "/machines").hasAuthority(adminAuthority)
                .requestMatchers("/work_shifts/**").authenticated()
                .requestMatchers("/factories/**").authenticated()
                .requestMatchers("/machines/**").authenticated()
                .requestMatchers("/users/**").authenticated()
                .requestMatchers( "/sensors/**").authenticated()
                .requestMatchers("/measures/**").authenticated()
                .requestMatchers("/auth/_login").permitAll()
                .requestMatchers("/api-docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}