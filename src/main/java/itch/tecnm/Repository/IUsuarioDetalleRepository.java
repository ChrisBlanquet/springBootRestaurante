package itch.tecnm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.tecnm.model.UsuarioDetalle;

public interface IUsuarioDetalleRepository extends JpaRepository<UsuarioDetalle, String> {
	
	UsuarioDetalle findByUsername(String username);

}
