package com.cursospringboot.libraryapi.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain //injeção de dependencia
            (HttpSecurity http) throws Exception {
        return http
                //csrf exige um token de validação em cada requisição POST, PUT, DELETE, etc, impede/permite que paginas de outras aplicaçoes possam enviar requisiçoes para nossa api
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> configurer.loginPage("/login.html").permitAll()
                .successForwardUrl("http://localhost:8084/livros"))
                //nesse codigo ele direciona a pagina de login e se for sucesso envia para home
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.GET, "/livros/**").hasAnyRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.POST, "/livros/**").hasAnyRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.PUT, "/livros/**").hasAnyRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.DELETE, "/livros/**").hasAnyRole("ADMIN");
                    authorize.requestMatchers("/livros/**").hasAnyRole("ADMIN", "USER");
                    //qualquer requisiçao para essa api o usuario deverá estar autenticado
                    //anyRequst sempre deverá ser a ultima
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails user1 = User.builder()
                .username("usuario1")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("usuario2")
                .password(encoder.encode("321"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
