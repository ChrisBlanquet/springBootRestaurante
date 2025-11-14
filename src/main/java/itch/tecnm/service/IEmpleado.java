package itch.tecnm.service;

import java.util.List;

import itch.tecnm.model.Empleado;

public interface IEmpleado {
	
	List<Empleado> MostrartodosEmpledos();
	
	Empleado buscarPorClave(String claveEmpleado);
	
	void guardarEmpleado (Empleado empleado);

	void eliminarEmpleado(String clave);
	
	List<Empleado> BuscarPuestoMesero(Integer puesto);
	
	List<Empleado> buscarPorNombre(String nombre);
}
