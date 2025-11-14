package itch.tecnm.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import itch.tecnm.Repository.ClienteRepository;
import itch.tecnm.model.Cliente;
import itch.tecnm.service.IClienteService;

@Service
@Primary //se pone por que si no da error por que estta dice que los metodos que quiere implementar estan en esta clase
public class ClienteServiceJpa implements IClienteService{
	
	@Autowired
	private ClienteRepository clienteRepo;

	@Override
	public List<Cliente> bucarTodosClientes() {
		return clienteRepo.findAll();
	}

	@Override
	public Cliente buscarPorIdCliente(Integer idCliente) {
		Optional<Cliente> optional=clienteRepo.findById(idCliente);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardarCliente(Cliente cliente) {
		clienteRepo.save(cliente);	
	}
	
	
	@Override
	public void eliminarCliente(Integer cliente) {	
		clienteRepo.deleteById(cliente);;
	}
	
	@Override
	public void actualizarCliente(Cliente cliente) {	
		clienteRepo.save(cliente);
	}

	@Override
	public Cliente guardarYRetornar(Cliente cliente) {
		clienteRepo.saveAndFlush(cliente);
		return null;
	}

	@Override
	public Cliente buscarPorNombre(String nombre) {
		Optional<Cliente> optional=clienteRepo.findByNombre(nombre);
		if(optional.isPresent()) {
			return optional.get();
		}
	    return null;
	    // para hacerlo mas limpio puedo poner esto}
	    //return clienteRepo.findByNombre(nombre).orElse(null);
	}

	@Override
	public List<Cliente> buscarContengaCadena(String nombre) {
		return clienteRepo.findByNombreLike("%" + nombre + "%");
	}
	
	@Override
	public Cliente BuscarPorEmail(String email) {
		return clienteRepo.findByEmail(email).orElse(null);
	}
	
	@Override
	public List<Cliente> buscarDominioEmail(String email){
		return clienteRepo.findByEmailLike("%"+email);
	}
	
	
	@Override
	public List<Cliente> buscarEntreCreditos(Double valor1,Double valor2){
		return clienteRepo.findByCreditoBetween(valor1, valor2);
	}

	@Override
	public List<Cliente> buscarCreditoMayor(Double valor1) {
		return clienteRepo.findByCreditoGreaterThan(valor1);
	}

	@Override
	public List<Cliente> buscarDestacados(Integer valor) {
		return clienteRepo.findByDestacadoEquals(valor);
	}

	@Override
	public List<Cliente> buscarPorNombreYCreditoMayor(String nombre, Double credito) {
		return clienteRepo.findByNombreAndCreditoGreaterThan(nombre, credito);
	}

	@Override
	public List<Cliente> buscarFotoCliente(String fotocliente) {
		return clienteRepo.findByFotoclienteEquals(fotocliente);
	}

	@Override
	public List<Cliente> buscarDestacadosYcreditoMayor(Integer destacado, Double credito) {
		return clienteRepo.findByDestacadoEqualsAndCreditoGreaterThan(destacado, credito);
	}

	@Override
	public List<Cliente> BuscarMayoresCreditos() {
		return clienteRepo.findTop5ByOrderByCreditoDesc();
		
	}
	
	
	
	
	
	
	

}
