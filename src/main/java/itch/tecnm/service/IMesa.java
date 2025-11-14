package itch.tecnm.service;

import java.util.List;

import itch.tecnm.model.Mesa;

public interface IMesa {
	
    List<Mesa> listarTodasLasMesas();
    
    Mesa buscarMesaPorId(Integer idMesa);
    
    void guardarMesa(Mesa mesa);
    
    void eliminarMesa(Integer idMesa);

}
