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

            // RECURSOS PÚBLICOS
            .requestMatchers(
                "/css/**",
                "/js/**",
                "/imagen/**",
                "/images/**",
                "/static/**",
                "/login",
                "/registro",
                "/registro/**",
                "/inicio",
                "/Inicio",
                "/Inicio.css",
                "/403",
                "/detalleCliente.css",
                "/animation/**",
                "/producto/listaProducto",
                "uploads/**"
            ).permitAll()

            // COMPLETAR DATOS EMPLEADO
            .requestMatchers("/empleado/completar-datos", "/empleado/guardar")
                .hasAnyAuthority("MESERO","COCINERO","CAJERO","SUPERVISOR","ADMIN")

            // CRUD EMPLEADOS
            .requestMatchers("/empleado/crearEmpleado",
                             "/empleado/listadoEmpleados",
                             "/empleado/editar/**",
                             "/empleado/eliminar/**",
                             "/empleado/buscar")
                .hasAnyAuthority("SUPERVISOR","ADMIN")

            // CLIENTE COMPLETA DATOS
            .requestMatchers("/cliente/completar-datos","/cliente/guardar")
                .hasAnyAuthority("CLIENTE","ADMIN","CAJERO")

            // CRUD CLIENTES
            .requestMatchers("/cliente/crear","/cliente/ver/**",
                             "/cliente/listadocli",
                             "/cliente/editar/**",
                             "/cliente/eliminar/**")
                .hasAnyAuthority("CAJERO","ADMIN","SUPERVISOR")

            // RESERVACIONES
            .requestMatchers("/reservar/listado","/reservar/crear","/reservar/guardar")
                .hasAnyAuthority("CLIENTE","ADMIN","CAJERO")

            .requestMatchers("/reservar/listado")
                .hasAnyAuthority("CAJERO","ADMIN","COCINERO","MESERO")

            .requestMatchers("/reservar/editar/**",
                             "/reservar/eliminar/**",
                             "/reservar/confirmar/**")
                .hasAnyAuthority("CAJERO","ADMIN")

            // PEDIDOS
            .requestMatchers("/pedido/crear", "/pedido/guardar")
                .hasAnyAuthority("MESERO","CAJERO","ADMIN")

            .requestMatchers("/pedido/editar/**")
                .hasAnyAuthority("MESERO","CAJERO","ADMIN")

            .requestMatchers("/pedido/ver/**", "/pedido/ticket/**")
                .hasAnyAuthority("MESERO","CAJERO","ADMIN","COCINERO")

            .requestMatchers("/pedido/listado")
                .hasAnyAuthority("MESERO","CAJERO","ADMIN","COCINERO")

            // PRODUCTOS
            .requestMatchers(
                "/producto/listaProductoAdmin",
                "/producto/listaProductoAdmin/verProducto/**",
                "/producto/crearProducto",
                "/producto/guardarProducto",
                "/producto/eliminarProducto/**",
                "/producto/editarProducto/**"
            ).hasAnyAuthority("SUPERVISOR","ADMIN")

            // MESAS, PERFILES, USUARIO
            .requestMatchers("/mesa/**","/perfil/**","usuario/**")
                .hasAnyAuthority("SUPERVISOR","ADMIN")

            // TODO LO DEMÁS REQUIERE LOGIN
            .anyRequest().authenticated()
        )

        .exceptionHandling(ex -> ex.accessDeniedPage("/403"))

        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/perform_login")
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/inicio", true)
            .failureUrl("/login?error=true")
            .permitAll()
        )

        .logout(logout -> logout
            .logoutUrl("/salir")
            .logoutSuccessUrl("/login?logout=true")
            .permitAll()
        )

        .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
