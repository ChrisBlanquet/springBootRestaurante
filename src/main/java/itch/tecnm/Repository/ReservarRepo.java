package itch.tecnm.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.tecnm.model.Reservar;

public interface ReservarRepo extends JpaRepository<Reservar, Integer> {
	
	List<Reservar> findByFechaBetween(LocalDate fecha,LocalDate Fecha2);
	
	List<Reservar> findByFechaGreaterThanEqual(LocalDate fecha);
	
	List<Reservar> findByCliente_IdAndEstatus(Integer idCliente, int estatus);
	
	List<Reservar> findByCliente_IdAndEstatusAndPedidosIsEmpty(Integer idCliente, Integer estatus);
	


}
