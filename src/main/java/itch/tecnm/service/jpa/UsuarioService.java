package itch.tecnm.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itch.tecnm.Repository.UsuarioRepository;
import itch.tecnm.model.Usuario;
import itch.tecnm.service.IUsuario;

@Service
public class UsuarioService implements IUsuario{
	
	@Autowired
	UsuarioRepository usuarioRepo;

	@Override
	public List<Usuario> BuscarTodosLosUsuario() {
		return usuarioRepo.findAll();
	}

	@Override
	public void EliminarUsuario(Integer idUsuario) {
		usuarioRepo.deleteById(idUsuario);
	}

	@Override
	public Usuario BuscarUsuarioId(Integer IdUsuario) {
		return usuarioRepo.findById(IdUsuario).orElse(null);
	}

	@Override
	public Usuario GuardarUsuario(Usuario usuario) {
		return usuarioRepo.save(usuario);
	}
	
	

}
