package com.cursospringboot.libraryapi.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain 
    //injeção de dependencia
    (HttpSecurity http) throws Exception {
        return http
        //csrf exige um token de validação em cada requisição POST, PUT, DELETE, etc, impede/permite que paginas de outras aplicaçoes possam enviar requisiçoes para nossa api
                .csrf(AbstractHttpConfigurer::disable) 
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())//qualquer requisiçao para essa api o usuario deverá estar autenticado
                //.formLogin(configurer -> configurer.loginPage("/login.html").successForwardUrl("/home.html")) = nesse codigo comentado ele direciona a pagina de login e se for sucesso envia para home
                .build();
    }

}
