package itch.tecnm.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itch.tecnm.Repository.IReservar;
import itch.tecnm.model.Reservar;
import itch.tecnm.model.UsuarioDetalle;
import itch.tecnm.service.IClienteService;
import itch.tecnm.service.IMesa;
import itch.tecnm.service.IUsuarioDetalleService;

@Controller
@RequestMapping("/reservar")
public class ReservarController {

    @Autowired
    private IReservar serviceReservar;
    
    @Autowired
    private IClienteService serviceCliente;

    @Autowired
    private IMesa serviceMesa;
    
    @Autowired
    private IUsuarioDetalleService usuarioDetalleService;


    @GetMapping("/listado")
    public String listarReservas(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fin,
            Model model,
            Authentication auth) {

        List<Reservar> reservas;

        // === OBTENER ROLES DEL USUARIO LOGUEADO ===
        boolean esCliente = auth.getAuthorities().stream()
                         .anyMatch(a -> a.getAuthority().equals("CLIENTE"));

        if (esCliente) {
            // 🔥 SOLO SUS RESERVAS
            String username = auth.getName();
            UsuarioDetalle detalle = usuarioDetalleService.buscarPorUsername(username);

            if (detalle != null && detalle.getIdCliente() != null) {
                reservas = serviceReservar.buscarPorCliente(detalle.getIdCliente());
            } else {
                reservas = List.of();
            }

        } else {
            // 🔥 ADMIN, CAJERO, ETC → FILTRO NORMAL
            if (inicio != null && fin != null) {
                reservas = serviceReservar.buscarEntreFechas(inicio, fin);
            } else if (inicio != null) {
                reservas = serviceReservar.buscarEntreUnaFecha(inicio);
            } else {
                reservas = serviceReservar.todasLasReservas();
            }
        }

        model.addAttribute("reservaLista", reservas);
        return "reservar/listaReservar";
    }

    
    
    
    @GetMapping("/mis-reservas")
    public String misReservas(Model model, Authentication auth) {

        String username = auth.getName();  
        UsuarioDetalle detalle = usuarioDetalleService.buscarPorUsername(username);

        if (detalle == null || detalle.getIdCliente() == null) {
            return "redirect:/cliente/completar-datos";
        }

        Integer idCliente = detalle.getIdCliente();

        List<Reservar> reservas = serviceReservar.buscarPorCliente(idCliente);

        model.addAttribute("reservaLista", reservas);
        return "reservar/listaReservar";
    }
    
    

    @GetMapping("/crear")
    public String crearReserva(Model model, Authentication auth) {

        Reservar nueva = new Reservar();

        model.addAttribute("reserva", nueva);
        model.addAttribute("mesas", serviceMesa.listarTodasLasMesas());

        boolean esCliente = auth.getAuthorities()
                                .stream()
                                .anyMatch(a -> a.getAuthority().equals("CLIENTE"));

        if (esCliente) {
            String username = auth.getName();
            UsuarioDetalle detalle = usuarioDetalleService.buscarPorUsername(username);

            if (detalle != null && detalle.getIdCliente() != null) {

                model.addAttribute("clientes",
                    List.of(serviceCliente.buscarPorIdCliente(detalle.getIdCliente())));
            }

        } else {

            model.addAttribute("clientes", serviceCliente.bucarTodosClientes());
        }

        return "reservar/crearReserva";
    }


    
    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute Reservar reserva) {

        if (reserva.getCliente() == null || reserva.getCliente().getId() == null) {
            throw new RuntimeException("ERROR: La reserva llegó sin cliente");
        }

        reserva.setCliente(
            serviceCliente.buscarPorIdCliente(reserva.getCliente().getId())
        );

        if (reserva.getMesa() != null && reserva.getMesa().getIdMesa() != null) {
            reserva.setMesa(serviceMesa.buscarMesaPorId(reserva.getMesa().getIdMesa()));
        }

        serviceReservar.guardarReserva(reserva);
        return "redirect:/reservar/listado";
    }


    @GetMapping("/editar/{id}")
    public String editarReserva(@PathVariable("id") Integer id, Model model) {
        Reservar reserva = serviceReservar.obtenerReservaID(id);
        
       System.out.print(reserva);

        if (reserva == null) {
            return "redirect:/reservar/listado";
        }

        model.addAttribute("reserva", reserva);
        model.addAttribute("clientes", serviceCliente.bucarTodosClientes());
        model.addAttribute("mesas", serviceMesa.listarTodasLasMesas());

        return "reservar/crearReserva";
    }


    //ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable("id") Integer id) {
        serviceReservar.eliminarporID(id);
        return "redirect:/reservar/listado";
    }
    
    @GetMapping("/confirmar/{id}")
    public String confirmarReserva(@PathVariable("id") Integer id) {
        Reservar reserva = serviceReservar.obtenerReservaID(id);
        if (reserva != null) {
            reserva.setEstatus(2);
            serviceReservar.guardarReserva(reserva);
        }
        return "redirect:/pedido/crear";
    }

}
