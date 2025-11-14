package itch.tecnm.service;

import java.util.List;
import itch.tecnm.model.Perfil;

public interface IPerfilService {
    List<Perfil> BuscarTodos();
    Perfil BuscarPorId(Integer id);
    void Guardar(Perfil perfil);
    void Eliminar(Integer id);
}
