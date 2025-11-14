package itch.tecnm.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import itch.tecnm.Repository.ProductoRepository;
import itch.tecnm.model.Producto;
import itch.tecnm.service.IProductoService;

@Service
@Primary
public class ProductoServiceJpa implements IProductoService{
	
	@Autowired
	private ProductoRepository productoRepo;

	@Override
	public List<Producto> bucarTodosProductos() {
		return productoRepo.findAll();
	}

	@Override
	public Producto buscarPorIdProducto(Integer idProducto) {
		return productoRepo.findById(idProducto).orElse(null);
	}

	@Override
	public void guardarProducto(Producto producto) {
		productoRepo.save(producto);
	}

	@Override
	public void eliminarProducto(Integer idProducto) {
		productoRepo.deleteById(idProducto);
	}

	@Override
	public void actualizarProducto(Producto producto) {
		productoRepo.save(producto);
	}

	@Override
	public List<Producto> buscarPorRango(Double precioMin, Double precioMax) {
		return productoRepo.findByPrecioBetween(precioMin, precioMax);
	}

	@Override
	public List<Producto> buscarPorTipo(Integer tipo) {
		return productoRepo.findByTipo(tipo);
	}

	@Override
	public List<Producto> buscarPorTipoYRango(Integer tipo, Double precioMin, Double precioMax) {
		return productoRepo.findByTipoAndPrecioBetween(tipo, precioMin, precioMax);
	}

	@Override
	public List<Producto> buscarPorNombreYTipo(String nombre, Integer tipo) {
		
		return productoRepo.findByNombreContainingIgnoreCaseAndTipo(nombre, tipo);
	}

	@Override
	public List<Producto> buscarPorNombre(String nombre) {
		return productoRepo.findByNombreContainingIgnoreCase(nombre);
	}

}
