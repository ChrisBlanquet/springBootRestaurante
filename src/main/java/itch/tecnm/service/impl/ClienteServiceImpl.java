package itch.tecnm.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import itch.tecnm.model.Cliente;
import itch.tecnm.service.IClienteService;


@Service
public class ClienteServiceImpl implements IClienteService{
	
	
	List<Cliente> listaCliente=null;
	
	
	public ClienteServiceImpl() {
		
		listaCliente = new LinkedList<Cliente>();
		
		Cliente cliente1=new Cliente();
		cliente1.setId(1);
		cliente1.setNombre("Christopher");
		cliente1.setApellidos("Blanquet Mendez");
		cliente1.setEmail("blanqiet@gmail.com");
		cliente1.setCredito(13000.00);
		cliente1.setTelefono("7474701209");
		cliente1.setDestacado(1);
		cliente1.setFotocliente("imagenH1.png");
		


		Cliente cliente2=new Cliente();
		cliente2.setId(2);
		cliente2.setNombre("Melvin Jesus");
		cliente2.setApellidos("Villanueva Gomez");
		cliente2.setEmail("mjvg@gmail.com");
		cliente2.setCredito(10000.00);
		cliente2.setTelefono("7472242787");
		cliente2.setDestacado(0);
		cliente2.setFotocliente("imagenH2.png");
		
		
		Cliente cliente3=new Cliente();
		cliente3.setId(3);
		cliente3.setNombre("Juliette Irina");
		cliente3.setApellidos("Celis morales");
		cliente3.setEmail("jicm@gmail.com");
		cliente3.setCredito(2500.00);
		cliente3.setTelefono("3322414587");
		cliente3.setDestacado(0);
		cliente3.setFotocliente("imagenM1.png");
		
		
		listaCliente.add(cliente1);
		listaCliente.add(cliente2);
		listaCliente.add(cliente3);
	}

	@Override
	public List<Cliente> bucarTodosClientes() {
		return listaCliente;
	}
	
	@Override
	public Cliente buscarPorIdCliente (Integer idCliente) {
		for(Cliente cli:listaCliente)
			if(cli.getId() == idCliente)
				return cli;
		return null;
	}
	
	public void guardarCliente(Cliente cliente) {
		listaCliente.add(cliente);
	}
	
	public void actualizarCliente(Cliente cliente) {
	}

	@Override
	public void eliminarCliente(Integer idCliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente guardarYRetornar(Cliente c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente buscarPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> buscarContengaCadena(String cadena) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente BuscarPorEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> buscarDominioEmail(String cadena) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> buscarEntreCreditos(Double valor1, Double valor2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> buscarCreditoMayor(Double valor1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> buscarDestacados(Integer valor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> buscarPorNombreYCreditoMayor(String nombre, Double credito) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> buscarFotoCliente(String fotocliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> buscarDestacadosYcreditoMayor(Integer destacado, Double credito) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> BuscarMayoresCreditos() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
