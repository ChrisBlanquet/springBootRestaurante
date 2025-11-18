package itch.tecnm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import itch.tecnm.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	@Query("SELECT MAX(u.id) FROM Usuario u")
	Integer obtenerUltimoId();



}
