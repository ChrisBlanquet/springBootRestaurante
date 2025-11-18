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

        http.authorizeHttpRequests(auth -> auth

            // ============================================
            //  RECURSOS PÚBLICOS
            // ============================================
        		.requestMatchers(
        			    "/css/**", "/js/**", "/imagen/**",
        			    "/Login", "/registro/**",
        			    "/inicio", "/Inicio",
        			    "/Inicio.css", "/403",
        			    "/detalleCliente.css",
        			    "/animation/**","/registro", "/registro/**"
        			).permitAll()

            // ============================================
            //  COMPLETAR DATOS EMPLEADO
            //  MESERO/COCINERO/CAJERO/SUPERVISOR/ADMIN
            // ============================================
            .requestMatchers("/empleado/completar-datos", "/empleado/guardar")
                .hasAnyAuthority("MESERO","COCINERO","CAJERO","SUPERVISOR","ADMIN")

            // ============================================
            //  CRUD EMPLEADOS (SUPERVISOR, ADMIN)
            // ============================================
            .requestMatchers("/empleado/crearEmpleado",
                             "/empleado/listadoEmpleados",
                             "/empleado/editar/**",
                             "/empleado/eliminar/**",
                             "/empleado/buscar")
                .hasAnyAuthority("SUPERVISOR","ADMIN")

            // ============================================
            //  CLIENTE COMPLETA SUS DATOS
            // ============================================
            .requestMatchers("/cliente/completar-datos",
                             "/cliente/guardar")
                .hasAnyAuthority("CLIENTE","ADMIN")

            // ============================================
            //  CRUD CLIENTES (CAJERO + ADMIN)
            // ============================================
            .requestMatchers("/cliente/crear", "/cliente/ver/**",
                             "/cliente/listadocli", "/cliente/editar/**",
                             "/cliente/eliminar/**")
                .hasAnyAuthority("CAJERO","ADMIN")

            // ============================================
            //  RESERVACIONES
            // ============================================

            // CLIENTE — solo crear, guardar y ver sus reservas
            .requestMatchers("/reservar/mis-reservas",
                             "/reservar/crear",
                             "/reservar/guardar")
                .hasAnyAuthority("CLIENTE","ADMIN")

            // LISTADO GENERAL — cajero y admin (también cocinero y mesero pueden verlo)
            .requestMatchers("/reservar/listado")
                .hasAnyAuthority("CAJERO","ADMIN","COCINERO","MESERO")

            // editar/eliminar/confirmar — solo cajero y admin
            .requestMatchers("/reservar/editar/**",
                             "/reservar/eliminar/**",
                             "/reservar/confirmar/**")
                .hasAnyAuthority("CAJERO","ADMIN")

             // ============================================
            //  PEDIDOS
            // ============================================

            // Crear pedidos (MESERO y CAJERO pueden crear)
            .requestMatchers("/pedido/crear", "/pedido/guardar")
            .hasAnyAuthority("MESERO","CAJERO","ADMIN")

            // Editar pedido (solo MESERO dueño, pero eso ya lo controlas en controlador)
            .requestMatchers("/pedido/editar/**")
            .hasAnyAuthority("MESERO","CAJERO","ADMIN")

            // Ver detalles de pedidos
            .requestMatchers("/pedido/ver/**", "/pedido/ticket/**")
            .hasAnyAuthority("MESERO","CAJERO","ADMIN","COCINERO")

            // Listado de pedidos
            .requestMatchers("/pedido/listado")
            .hasAnyAuthority("MESERO","CAJERO","ADMIN","COCINERO")

            // ============================================
            //  TODO LO DEMÁS REQUIERE AUTENTICACIÓN
            // ============================================
            .anyRequest().authenticated()
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
