package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class handle unsuccessful JWT authorization
 */

@Component
public class CustomTokenAccessDeniedEntryPoint implements AccessDeniedHandler {
   /**
    * Here we are injected the DefaultHandlerExceptionResolver and delegate then
    * handler to this resolver.
    * This exception can now be handled with controller advice with an exception
    * handler method.
    */
   private final HandlerExceptionResolver resolver;

   public CustomTokenAccessDeniedEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
      this.resolver = resolver;
   }

   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response,
         AccessDeniedException accessDeniedException) throws IOException, ServletException {
      this.resolver.resolveException(request, response, null, accessDeniedException);
   }

}
