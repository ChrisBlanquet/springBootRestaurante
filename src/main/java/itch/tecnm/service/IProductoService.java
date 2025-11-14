package itch.tecnm.service;

import java.util.List;

import itch.tecnm.model.Producto;


public interface IProductoService {
	List<Producto> bucarTodosProductos();

	
	//Buscara un producto por el ID
	Producto buscarPorIdProducto(Integer idProducto);
	
	//Guardar un producto en la lista
	void guardarProducto(Producto producto);
	
	//Eliminar
	void eliminarProducto(Integer idProducto);
	
	void actualizarProducto(Producto producto);
	
	
	public List<Producto> buscarPorRango(Double precioMin, Double precioMax);
	
	public List<Producto> buscarPorTipo(Integer tipo);
	
	public List<Producto> buscarPorTipoYRango(Integer tipo, Double precioMin, Double precioMax);
	
    public List<Producto> buscarPorNombreYTipo(String nombre,Integer tipo);
    
    public List<Producto> buscarPorNombre(String nombre);
	
}
