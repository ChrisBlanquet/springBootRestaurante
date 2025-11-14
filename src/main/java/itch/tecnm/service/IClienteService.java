package itch.tecnm.service;

import java.util.List;

import itch.tecnm.model.Cliente;

public interface IClienteService {
	
	List<Cliente> bucarTodosClientes();
	
	
	//Buscara un cliente por el ID
	Cliente buscarPorIdCliente(Integer idCliente);
	
	
	//Guardar un cliente en la lista
	void guardarCliente(Cliente cliente);
	
	void eliminarCliente(Integer idCliente);
	
	void actualizarCliente(Cliente cliente);
	
	Cliente guardarYRetornar(Cliente cliente);
	
	Cliente buscarPorNombre(String nombre);
	
	List<Cliente> buscarContengaCadena(String cadena);
	
	Cliente BuscarPorEmail(String email);
	
	List<Cliente> buscarDominioEmail(String cadena);
	
	List<Cliente> buscarEntreCreditos(Double valor1,Double valor2);
	
	List<Cliente> buscarCreditoMayor (Double valor1);
	
	List<Cliente> buscarDestacados(Integer valor);
	
	List<Cliente> buscarPorNombreYCreditoMayor(String nombre,Double credito);
	
	List<Cliente> buscarFotoCliente(String fotocliente);
	
	List<Cliente> buscarDestacadosYcreditoMayor(Integer destacado,Double credito);
	
	List<Cliente> BuscarMayoresCreditos();
}
