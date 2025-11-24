package itch.tecnm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private DatosPendientesInterceptor datosPendientesInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(datosPendientesInterceptor)
        .excludePathPatterns(
            "/css/**",
            "/js/**",
            "/img/**",
            "/images/**",
            "/webjars/**",
            "/uploads/**",
            "/favicon.ico",

            // Login y registro
            "/Login","/login/**","/registro/**",
            "/inicio","/Inicio","/403","/error/**",

            // Completar datos
            "/cliente/completar-datos",
            "/empleado/completar-datos",

            // Guardados
            "/cliente/guardar",
            "/empleado/guardar"
        );
    }
}
