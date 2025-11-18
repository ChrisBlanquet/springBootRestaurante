package itch.tecnm.service.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itch.tecnm.Repository.IReservar;
import itch.tecnm.Repository.ReservarRepo;
import itch.tecnm.model.Reservar;

@Service
public class ReservarService implements IReservar{
	
	@Autowired
	ReservarRepo reservaRepo;

	@Override
	public List<Reservar> todasLasReservas() {
		return reservaRepo.findAll();
	}

	@Override
	public void guardarReserva(Reservar reserva) {
		reservaRepo.save(reserva);
	}

	@Override
	public Reservar obtenerReservaID(Integer idReserva) {
		return reservaRepo.findById(idReserva).orElse(null);
	}

	@Override
	public void eliminarporID(Integer idReserva) {
		reservaRepo.deleteById(idReserva);
	}

	@Override
	public List<Reservar> buscarEntreFechas(LocalDate fecha, LocalDate fecha2) {
		return reservaRepo.findByFechaBetween(fecha, fecha2);
	}

	@Override
	public List<Reservar> buscarEntreUnaFecha(LocalDate fecha) {
		return  reservaRepo.findByFechaGreaterThanEqual(fecha);
	}
	
	@Override
	public List<Reservar> buscarClienteyEstatus(Integer idCliente, int estatus) {
		 return reservaRepo.findByCliente_IdAndEstatus(idCliente, estatus);
	}

	@Override
	public List<Reservar> buscarClienteyEstatusyPedidoNull(Integer idCliente, Integer estatus) {
		return reservaRepo.findByCliente_IdAndEstatusAndPedidosIsEmpty(idCliente, estatus);
	}

	@Override
	public List<Reservar> buscarPorCliente(Integer idCliente) {
	    return reservaRepo.buscarPorCliente(idCliente);
	}


}
