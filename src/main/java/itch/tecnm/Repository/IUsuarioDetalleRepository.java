package itch.tecnm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import itch.tecnm.model.UsuarioDetalle;
import jakarta.transaction.Transactional;

public interface IUsuarioDetalleRepository extends JpaRepository<UsuarioDetalle, String> {
	
	UsuarioDetalle findByUsername(String username);
	
	@Modifying
	@Transactional
	@Query("UPDATE UsuarioDetalle d SET d.idCliente = NULL WHERE d.idCliente = :idCliente")
	void limpiarCliente(@Param("idCliente") Integer idCliente);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE UsuarioDetalle d SET d.claveEmpleado = NULL WHERE d.claveEmpleado = :clave")
	void limpiarEmpleado(@Param("clave") String claveEmpleado);



}
