package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class handles unsuccessful jwt authentication exception.
 */

@Component
public class CustomTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

   private final HandlerExceptionResolver resolver;

   public CustomTokenAuthenticationEntryPoint(
         @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
      this.resolver = resolver;
   }

   @Override
   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
         throws IOException, ServletException {

      response.addHeader("WWW-Authenticate", "Basic realm=\"Realm\"");
      this.resolver.resolveException(request, response, null, authException);
   }

}
