package itch.tecnm.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import itch.tecnm.model.Perfil;
import itch.tecnm.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	@Query("SELECT MAX(u.id) FROM Usuario u")
	Integer obtenerUltimoId();

	boolean existsByUsername(String username);
	
	@Query("SELECT u.perfiles FROM Usuario u WHERE u.username = :username")
	List<Perfil> obtenerPerfilesPorUsername(String username);


}
