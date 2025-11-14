package itch.tecnm.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.tecnm.model.Pedido;


public interface Pedidorepository extends JpaRepository<Pedido, Integer> {
	
    List<Pedido> findByFecha(LocalDate fecha);

    List<Pedido> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Pedido> findByCliente_NombreContainingIgnoreCaseOrCliente_ApellidosContainingIgnoreCase(String nombre, String apellidos);

    List<Pedido> findByAtenciones_Empleado_NombreCompletoContainingIgnoreCase(String nombreCompleto);

}
