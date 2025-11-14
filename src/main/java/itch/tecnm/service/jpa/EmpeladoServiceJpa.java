package itch.tecnm.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itch.tecnm.Repository.EmpleadoRepository;
import itch.tecnm.model.Empleado;
import itch.tecnm.service.IEmpleado;

@Service
public class EmpeladoServiceJpa implements IEmpleado {
	
	@Autowired
	EmpleadoRepository empleadoRepo;

	@Override
	public List<Empleado> MostrartodosEmpledos() {
		return empleadoRepo.findAll();
	}

	
	@Override
	public Empleado buscarPorClave(String claveEmpleado) {
	    return empleadoRepo.findById(claveEmpleado).orElse(null);
	}


	@Override
	public void guardarEmpleado(Empleado empleado) {
		empleadoRepo.save(empleado);
	}


	@Override
	public void eliminarEmpleado(String clave) {
		empleadoRepo.deleteById(clave);
	}


	@Override
	public List<Empleado> BuscarPuestoMesero(Integer puesto) {
		return empleadoRepo.findByPuesto(puesto);
	}


	@Override
	public List<Empleado> buscarPorNombre(String nombre) {
		return empleadoRepo.findByNombreCompletoContainingIgnoreCase(nombre);
	}


}
