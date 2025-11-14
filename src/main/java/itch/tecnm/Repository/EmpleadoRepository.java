package itch.tecnm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.tecnm.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

	List<Empleado> findByPuesto(Integer puesto);
	
	List<Empleado> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}
