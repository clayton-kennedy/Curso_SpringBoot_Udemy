package com.cursospringboot.libraryapi.Security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cursospringboot.libraryapi.Model.Usuario;
import com.cursospringboot.libraryapi.Service.UsuarioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UsuarioService UsuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2user = auth2AuthenticationToken.getPrincipal();

        String email = oauth2user.getAttribute("email");   

        Usuario usuario = UsuarioService.obterPorEmail(email);

        authentication = new CustomAuthentication(usuario);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
