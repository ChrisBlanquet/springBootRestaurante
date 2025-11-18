package itch.tecnm.service;

import itch.tecnm.model.UsuarioDetalle;

public interface IUsuarioDetalleService {
	
	   UsuarioDetalle buscarPorUsername(String username);

	    void guardar(UsuarioDetalle detalle);

}
