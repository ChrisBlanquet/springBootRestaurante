package itch.tecnm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import itch.tecnm.model.UsuarioDetalle;
import itch.tecnm.service.IUsuarioDetalleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DatosPendientesInterceptor implements HandlerInterceptor {

    @Autowired
    private IUsuarioDetalleService usuarioDetalleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = request.getRequestURI();

        // 1. Recursos estáticos
        if (uri.startsWith("/css/") ||
            uri.startsWith("/js/") ||
            uri.startsWith("/img/") ||
            uri.startsWith("/images/") ||
            uri.endsWith(".css") ||
            uri.endsWith(".js") ||
            uri.endsWith(".png") ||
            uri.endsWith(".jpg") ||
            uri.endsWith(".jpeg") ||
            uri.endsWith(".gif") ||
            uri.endsWith(".svg") ||
            uri.endsWith(".ico")) 
        {
            return true;
        }

        // 2. Login / Registro
        if (uri.startsWith("/Login") ||
            uri.startsWith("/login") ||
            uri.startsWith("/registro")) 
        {
            return true;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return true;
        }

        // ⛔ PRIMERO evaluar roles (ANTES de cargar detalle)
        boolean esAdmin = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ADMIN"));

        boolean esSupervisor = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("SUPERVISOR"));

        boolean esCliente = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("CLIENTE"));

        boolean esEmpleado = auth.getAuthorities().stream()
                .anyMatch(r ->
                    r.getAuthority().equals("MESERO") ||
                    r.getAuthority().equals("CAJERO") ||
                    r.getAuthority().equals("COCINERO")
                );

        // 🔥 3. SI ES ADMIN O SUPERVISOR → PASA DIRECTO
        if (esAdmin || esSupervisor) {
            return true;
        }

        // 4. Cargar detalle solo cuando NO es admin/supervisor
        String username = auth.getName();
        UsuarioDetalle detalle = usuarioDetalleService.buscarPorUsername(username);

        // 5. Validar Cliente
        if (esCliente && (detalle == null || detalle.getIdCliente() == null)) {
            response.sendRedirect("/cliente/completar-datos");
            return false;
        }

        // 6. Validar Empleado
        if (esEmpleado && (detalle == null || detalle.getClaveEmpleado() == null)) {
            response.sendRedirect("/empleado/completar-datos");
            return false;
        }

        return true;
    }

}
