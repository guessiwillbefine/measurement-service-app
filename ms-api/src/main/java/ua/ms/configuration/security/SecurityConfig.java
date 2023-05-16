package ua.ms.configuration.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.ms.configuration.security.util.JWTFilter;
import ua.ms.entity.user.Role;

import static java.lang.String.format;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String adminAuthority = Role.ADMIN.name();
    private final OncePerRequestFilter jwtFilter;
    private final AccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(@Qualifier("jwtFilter") JWTFilter jwtFilter,
                          @Qualifier("customAccessDeniedHandler") AccessDeniedHandler accessDeniedHandler) {
        this.jwtFilter = jwtFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

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
                .requestMatchers("/ws/**", "/topic/**", "/user/**", "/message/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api-docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        log.info("FilterChain configuration set up");
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        log.info(format("new UsernamePasswordAuthenticationFilter - [%s]", jwtFilter.getClass().getPackage() + jwtFilter.getClass().getSimpleName()));
        return http.build();
    }
}