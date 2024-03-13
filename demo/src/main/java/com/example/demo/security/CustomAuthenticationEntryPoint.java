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
 * This class handles unsuccessful basis authentication.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

   /**
    * Here we are injected the DefaultHandlerExceptionResolver and delegate then
    * handler to this resolver.
    * This exception can now be handled with controller advice with an exception
    * handler method.
    */
   private final HandlerExceptionResolver resolver;

   public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
      this.resolver = resolver;
   }

   @Override
   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
         throws IOException, ServletException {
      response.addHeader("WWW-Authenticate", "Basic realm=\"Realm\"");
      this.resolver.resolveException(request, response, null, authException);
   }

}
