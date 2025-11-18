package itch.tecnm.service;

import java.time.LocalDate;
import java.util.List;

import itch.tecnm.model.Pedido;

public interface IPedidoService{
	
	List<Pedido> obtenerPedidos();
	
	void guardarPedido (Pedido pedido);
	
	Pedido encontrarPedidoID(Integer idPedido);
	
	void eliminarPedido(Integer idPedido);
	

    List<Pedido> buscarPorFechaExacta(LocalDate fecha);

    List<Pedido> buscarPorRangoFechas(LocalDate inicio, LocalDate fin);

    List<Pedido> buscarPorCliente(String nombreOApellido);

    List<Pedido> buscarPorEmpleado(String nombreEmpleado);
    
    List<Pedido> buscarPedidosPorEmpleado(String claveEmpleado);




}
