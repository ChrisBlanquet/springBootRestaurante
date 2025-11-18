package itch.tecnm.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itch.tecnm.model.UsuarioDetalle;
import itch.tecnm.Repository.IUsuarioDetalleRepository;
import itch.tecnm.service.IUsuarioDetalleService;

@Service
public class UsuarioDetalleServiceImpl implements IUsuarioDetalleService {

    @Autowired
    private IUsuarioDetalleRepository repo;

    @Override
    public UsuarioDetalle buscarPorUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public void guardar(UsuarioDetalle detalle) {
        repo.save(detalle);
    }
}