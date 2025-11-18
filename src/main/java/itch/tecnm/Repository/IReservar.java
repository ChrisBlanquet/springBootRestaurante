package itch.tecnm.Repository;

import java.time.LocalDate;
import java.util.List;

import itch.tecnm.model.Reservar;

public interface IReservar {
	
	List<Reservar> todasLasReservas();
	
	void guardarReserva(Reservar Reserva);
	
	Reservar obtenerReservaID(Integer idReserva);
	
	void eliminarporID(Integer idReserva);
	
	List<Reservar> buscarEntreFechas(LocalDate fecha,LocalDate fecha2);
	
	List<Reservar> buscarEntreUnaFecha(LocalDate fecha);
	
	public List<Reservar> buscarClienteyEstatus(Integer idCliente, int estatus);
	
	public List<Reservar> buscarClienteyEstatusyPedidoNull(Integer idCliente, Integer estatus);
	
	List<Reservar> buscarPorCliente(Integer idCliente);


	
	

}
