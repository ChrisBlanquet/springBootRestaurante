package itch.tecnm.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import itch.tecnm.model.Reservar;

public interface ReservarRepo extends JpaRepository<Reservar, Integer> {
	
	List<Reservar> findByFechaBetween(LocalDate fecha,LocalDate Fecha2);
	
	List<Reservar> findByFechaGreaterThanEqual(LocalDate fecha);
	
	List<Reservar> findByCliente_IdAndEstatus(Integer idCliente, int estatus);
	
	List<Reservar> findByCliente_IdAndEstatusAndPedidosIsEmpty(Integer idCliente, Integer estatus);
	
	@Query("SELECT r FROM Reservar r WHERE r.cliente.id = :idCliente")
	List<Reservar> buscarPorCliente(@Param("idCliente") Integer idCliente);


}
