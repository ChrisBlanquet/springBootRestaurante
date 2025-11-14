package itch.tecnm.service.jpa;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import itch.tecnm.Repository.IPerfil;
import itch.tecnm.model.Perfil;
import itch.tecnm.service.IPerfilService;

@Service
public class PerfilServiceJPA implements IPerfilService {

    @Autowired
    private IPerfil repoPerfil;

    @Override
    public List<Perfil> BuscarTodos() {
        return repoPerfil.findAll();
    }

    @Override
    public Perfil BuscarPorId(Integer id) {
        return repoPerfil.findById(id).orElse(null);
    }

    @Override
    public void Guardar(Perfil perfil) {
        repoPerfil.save(perfil);
    }

    @Override
    public void Eliminar(Integer id) {
        repoPerfil.deleteById(id);
    }
}
