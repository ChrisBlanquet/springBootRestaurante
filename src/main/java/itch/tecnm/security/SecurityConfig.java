package itch.tecnm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth

                // Recursos públicos
                .requestMatchers("/css/**", "/js/**", "/imagen/**", "/Login", "/registro/**",
                        "/inicio", "/Inicio.css", "/403","/detalleCliente.css","/animation/**")
                    .permitAll()
                    
                    
                .requestMatchers("pedido/listado")
                .hasAnyAuthority("CAJERO","ADMIN","COCINERO")

                // CAJERO 
                .requestMatchers("/cliente/crear","/cliente/guardar","/cliente/ver/**",
                                 "/cliente/listadocli","/pedido/crear","/pedido/guardar","/reservar/listado","/reservar/crear",
                                 "/reservar/guardar")
                    .hasAnyAuthority("CAJERO","ADMIN")

                // COCINERO 
                .requestMatchers("/pedido/ver/**")
                    .hasAnyAuthority("COCINERO","ADMIN")

                // CLIENTE 
                .requestMatchers("/reservar/**")
                    .hasAnyAuthority("CLIENTE","ADMIN")

                // SUPERVISOR
                .requestMatchers("/mesa/**","/producto/**","/empleado/**")
                    .hasAnyAuthority("SUPERVISOR","ADMIN")

                // Lo demás solo admin
                .anyRequest().hasAuthority("ADMIN")
            )
            .exceptionHandling(ex -> ex.accessDeniedPage("/403"))
            .formLogin(form -> form
                .loginPage("/Login")
                .loginProcessingUrl("/perform_login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio", true)
                .failureUrl("/Login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/salir")
                .logoutSuccessUrl("/Login?logout=true")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
