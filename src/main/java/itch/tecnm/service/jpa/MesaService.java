package itch.tecnm.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itch.tecnm.Repository.MesaRepository;
import itch.tecnm.model.Mesa;
import itch.tecnm.service.IMesa;

@Service
public class MesaService implements IMesa {
	
	@Autowired
	MesaRepository mesaRepo;

	@Override
	public List<Mesa> listarTodasLasMesas() {
		return mesaRepo.findAll();
	}

	@Override
	public Mesa buscarMesaPorId(Integer idMesa) {
		return mesaRepo.findById(idMesa).orElse(null);
	}

	@Override
	public void guardarMesa(Mesa mesa) {
		mesaRepo.save(mesa);
	}

	@Override
	public void eliminarMesa(Integer idMesa) {
		mesaRepo.deleteById(idMesa);
	}

}
