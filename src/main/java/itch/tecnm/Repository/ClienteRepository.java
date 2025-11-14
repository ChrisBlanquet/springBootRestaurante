package itch.tecnm.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.tecnm.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	Optional<Cliente> findByNombre(String nombre);
	
	List<Cliente> findByNombreLike(String nombre);
	
	Optional<Cliente> findByEmail(String email);
	
	List<Cliente> findByEmailLike(String email);
	
	List<Cliente> findByCreditoBetween(Double valor1,Double valor2);
	
	List<Cliente> findByCreditoGreaterThan(Double valor1);
	
	List<Cliente> findByDestacadoEquals(Integer valor1);
	
	List<Cliente> findByNombreAndCreditoGreaterThan(String nombre, Double credito);
	
	List<Cliente> findByFotoclienteEquals(String fotocliente);
	
	List<Cliente> findByDestacadoEqualsAndCreditoGreaterThan(Integer destacado,Double credito);
	
	List<Cliente> findTop5ByOrderByCreditoDesc();

}
