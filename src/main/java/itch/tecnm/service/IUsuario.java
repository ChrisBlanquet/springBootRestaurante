package itch.tecnm.service;

import java.util.List;

import itch.tecnm.model.Usuario;

public interface IUsuario {
	
	List<Usuario> BuscarTodosLosUsuario();
	
	void EliminarUsuario(Integer idUsuario);
	
	Usuario BuscarUsuarioId(Integer  IdUsuario);
	
	Usuario GuardarUsuario(Usuario usuario);
	
	 Integer generarNuevoId();

}

