package com.susanne.Susanne_eindopdrachtVA.config;

import com.susanne.Susanne_eindopdrachtVA.filter.JwtRequestFilter;
import com.susanne.Susanne_eindopdrachtVA.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/users/nogroup").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/{id}/authorities").hasRole("ADMIN")
                //-----------------------------entiteit messages-------------------------------
                .requestMatchers(HttpMethod.GET, "/messages").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/messages/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/messages/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/messages/**").hasRole("ADMIN")
                //-----------------------------entiteit messagesboards-------------------------------
                .requestMatchers(HttpMethod.PUT, "/messageboards/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/messageboards/{id}").hasAnyRole("ADMIN", "USER")
                //-----------------------------entiteit assignments2x-------------------------------
                .requestMatchers(HttpMethod.GET, "/homeworkassignments/group/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/homeworkassignments/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/homeworkassignments/{id}/file").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/handinassignments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/handinassignments/users/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/handinassignments/{id}/file").hasAnyRole("ADMIN", "USER")
                //-----------------------------entiteit groups-------------------------------
                .requestMatchers(HttpMethod.GET, "/groups/users/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/groups/{id}/users}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/groups/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/groups/admin").hasRole("ADMIN")
                .requestMatchers("/users", "/groups", "/messages", "/messageboards").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

