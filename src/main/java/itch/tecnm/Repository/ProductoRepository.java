package itch.tecnm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.tecnm.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
    // Buscar por tipo
    List<Producto> findByTipo(Integer tipo);

    // Buscar por rango de precios
    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);

    // Buscar por tipo y rango de precios
    List<Producto> findByTipoAndPrecioBetween(Integer tipo, Double precioMin, Double precioMax);
    
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByNombreContainingIgnoreCaseAndTipo(String nombre, Integer tipo);
	
}
