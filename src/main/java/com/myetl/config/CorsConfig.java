package com.myetl.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración global de CORS para la aplicación.
 *
 * <p>Esta clase define las reglas de acceso CORS permitiendo todas las métodos y encabezados.</p>
 *
 * <p>Común en aplicaciones que manejan solicitudes HTTP desde diferentes
 * dominios.</p>
 */
@Configuration
public class CorsConfig {

    /**
     * Configura y proporciona la fuente de configuración CORS.
     *
     * <p>Permite solicitudes desde cualquier origen y todos los métodos HTTP.</p>
     *
     * @return la configuración CORS aplicada a todas las rutas.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}