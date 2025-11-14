package itch.tecnm.service.jpa;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import itch.tecnm.Repository.Pedidorepository;
import itch.tecnm.model.Pedido;
import itch.tecnm.service.IPedidoService;

@Service
public class PedidoServiceJpa implements IPedidoService{
	
	@Autowired
	Pedidorepository pedidoRepo;

	@Override
	public List<Pedido> obtenerPedidos() {
		return pedidoRepo.findAll();
	}

	@Override
	public void guardarPedido(Pedido pedido) {
		pedidoRepo.save(pedido);
	}

	@Override
	public Pedido encontrarPedidoID(Integer idPedido) {
		return pedidoRepo.findById(idPedido).orElse(null);
	}

	@Override
	public void eliminarPedido(Integer idPedido) {
		pedidoRepo.deleteById(idPedido);
	}

  @Override
    public List<Pedido> buscarPorFechaExacta(LocalDate fecha) {
        return pedidoRepo.findByFecha(fecha);
    }

    @Override
    public List<Pedido> buscarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return pedidoRepo.findByFechaBetween(inicio, fin);
    }

    @Override
    public List<Pedido> buscarPorCliente(String nombreOApellido) {
        return pedidoRepo.findByCliente_NombreContainingIgnoreCaseOrCliente_ApellidosContainingIgnoreCase(
                nombreOApellido, nombreOApellido);
    }

    @Override
    public List<Pedido> buscarPorEmpleado(String nombreEmpleado) {
        return pedidoRepo.findByAtenciones_Empleado_NombreCompletoContainingIgnoreCase(nombreEmpleado);
    }
	

}
