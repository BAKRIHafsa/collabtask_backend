package com.CollabTask.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Ajoutez le chemin approprié pour votre API
            .allowedOrigins("http://localhost:3000") // Autoriser les requêtes depuis le domaine frontend
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Autoriser les méthodes HTTP spécifiées
            .allowedHeaders("*"); // Autoriser tous les en-têtes
    }
}
