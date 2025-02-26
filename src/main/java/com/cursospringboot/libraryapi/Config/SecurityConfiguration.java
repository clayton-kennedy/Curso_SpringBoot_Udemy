package com.cursospringboot.libraryapi.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cursospringboot.libraryapi.Security.CustomUserDetailsService;
import com.cursospringboot.libraryapi.Security.LoginSocialSucessHandler;
import com.cursospringboot.libraryapi.Service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain //injeção de dependencia
            (HttpSecurity http, LoginSocialSucessHandler sucessHandler) throws Exception {
        return http
                //csrf exige um token de validação em cada requisição POST, PUT, DELETE, etc, impede/permite que paginas de outras aplicaçoes possam enviar requisiçoes para nossa api
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> configurer.loginPage("/login.html").permitAll()
                .successForwardUrl("http://localhost:8084/livros"))
                //nesse codigo ele direciona a pagina de login e se for sucesso envia para home
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.GET, "/livros/**").hasAnyRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.POST, "/livros/**").hasAnyRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.PUT, "/livros/**").hasAnyRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.DELETE, "/livros/**").hasAnyRole("ADMIN");
                    // authorize.requestMatchers("/livros/**").hasAnyRole("ADMIN", "USER");
                    //qualquer requisiçao para essa api o usuario deverá estar autenticado
                    //anyRequst sempre deverá ser a ultima
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .loginPage("/login")
                            .successHandler(sucessHandler);
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    public UserDetailsService userDetailsService(UsuarioService usuarioService) {//(PasswordEncoder encoder) {

        // UserDetails user1 = User.builder()
        //         .username("usuario1")
        //         .password(encoder.encode("123"))
        //         .roles("USER")
        //         .build();
        // UserDetails user2 = User.builder()
        //         .username("usuario2")
        //         .password(encoder.encode("321"))
        //         .roles("ADMIN")
        //         .build();
        // return new InMemoryUserDetailsManager(user1, user2);
        return new CustomUserDetailsService(usuarioService);
    }
}
