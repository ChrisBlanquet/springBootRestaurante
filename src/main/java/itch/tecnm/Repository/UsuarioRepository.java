package itch.tecnm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.tecnm.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
