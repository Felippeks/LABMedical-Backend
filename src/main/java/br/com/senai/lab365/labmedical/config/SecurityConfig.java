package br.com.senai.lab365.labmedical.config;

import br.com.senai.lab365.labmedical.config.filtros.JwtAuthenticationFilter;
import br.com.senai.lab365.labmedical.config.filtros.JwtExceptionFilter;
import br.com.senai.lab365.labmedical.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtExceptionFilter jwtExceptionFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/usuarios/login").permitAll()
                        .requestMatchers("/api/usuarios").hasRole("ADMIN")
                        .requestMatchers("/api/pacientes").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers("/api/pacientes/{id}").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/pacientes").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.PUT, "/api/pacientes/{id}").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/pacientes/{id}").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/pacientes").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.POST, "/api/consultas").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/consultas").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/consultas/{id}").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/consultas/{id}").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/consultas/{id}").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.POST, "/api/exames").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/exames").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/exames/{id}").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/exames/{id}").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/exames/{id}").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers("/api/pacientes/prontuarios").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers("/api/pacientes/{id}/prontuarios").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers("/api/dashboard").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

        return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}