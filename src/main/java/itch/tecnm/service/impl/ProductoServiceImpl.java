package itch.tecnm.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import itch.tecnm.model.Producto;
import itch.tecnm.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService{
	
	List<Producto> listaProducto=null;
	
	public ProductoServiceImpl(){
		listaProducto = new LinkedList<Producto>();
		
		Producto producto1=new Producto();
		producto1.setId(1);
		producto1.setNombre("Enchiladas Suizas");
		producto1.setDescripcion("Tortillas rellenas de pollo, bañadas en salsa cremosa de tomate verde y gratinadas con queso.");
		producto1.setPrecio(80.00);
		producto1.setTipo(1);
		producto1.setFoto("enchiladas.png");


		Producto producto2=new Producto();
		producto2.setId(2);
		producto2.setNombre("Chilate");
		producto2.setDescripcion("Bebida tradicional a base de cacao, arroz y canela, servida fría y refrescante.");
		producto2.setPrecio(30.00);
		producto2.setTipo(2);
		producto2.setFoto("chilate.jpg");


		Producto producto3=new Producto();
		producto3.setId(3);
		producto3.setNombre("Pastel de Chocolate");
		producto3.setDescripcion("Suave pastel esponjoso con cobertura cremosa de chocolate oscuro.");
		producto3.setPrecio(80.00);
		producto3.setTipo(3);
		producto3.setFoto("pastel_chocolate.png");


		Producto producto4=new Producto();
		producto4.setId(4);
		producto4.setNombre("Tacos al Pastor");
		producto4.setDescripcion("Deliciosa carne de cerdo marinada, servida en tortilla de maíz con piña, cebolla y cilantro.");
		producto4.setPrecio(60.00);
		producto4.setTipo(1);
		producto4.setFoto("tacos_al_pastor.png");


		Producto producto5=new Producto();
		producto5.setId(5);
		producto5.setNombre("Agua de Horchata");
		producto5.setDescripcion("Refrescante bebida preparada con arroz, canela y leche, servida con mucho hielo.");
		producto5.setPrecio(25.00);
		producto5.setTipo(2);
		producto5.setFoto("horchata.png");


		Producto producto6=new Producto();
		producto6.setId(6);
		producto6.setNombre("Flan Napolitano");
		producto6.setDescripcion("Postre clásico con textura suave y un toque de caramelo.");
		producto6.setPrecio(50.00);
		producto6.setTipo(3);
		producto6.setFoto("flan.png");


		Producto producto7=new Producto();
		producto7.setId(7);
		producto7.setNombre("Pozole Rojo");
		producto7.setDescripcion("Caldo tradicional con maíz, carne de cerdo y guarniciones frescas.");
		producto7.setPrecio(90.00);
		producto7.setTipo(1);
		producto7.setFoto("pozole.png");


		Producto producto8=new Producto();
		producto8.setId(8);
		producto8.setNombre("Café de Olla");
		producto8.setDescripcion("Café aromático preparado con piloncillo y canela, servido caliente.");
		producto8.setPrecio(20.00);
		producto8.setTipo(2);
		producto8.setFoto("cafe_olla.png");


		Producto producto9=new Producto();
		producto9.setId(9);
		producto9.setNombre("Pay de Limón");
		producto9.setDescripcion("Base crujiente con relleno cremoso y un toque cítrico refrescante.");
		producto9.setPrecio(70.00);
		producto9.setTipo(3);
		producto9.setFoto("pay_limon.png");


		listaProducto.add(producto1);
		listaProducto.add(producto2);
		listaProducto.add(producto3);
		listaProducto.add(producto4);
		listaProducto.add(producto5);
		listaProducto.add(producto6);
		listaProducto.add(producto7);
		listaProducto.add(producto8);
		listaProducto.add(producto9);

	}
	
	@Override
	public List<Producto> bucarTodosProductos() {
		return listaProducto;
	}
	
	
	@Override
	public Producto buscarPorIdProducto (Integer idProducto) {
		for(Producto pro:listaProducto)
			if(pro.getId() == idProducto)
				return pro;
		return null;
	}
	
	public void guardarProducto(Producto producto) {
		listaProducto.add(producto);
	}

	@Override
	public void eliminarProducto(Integer idProducto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizarProducto(Producto producto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Producto> buscarPorRango(Double precioMin, Double precioMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> buscarPorTipo(Integer tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> buscarPorTipoYRango(Integer tipo, Double precioMin, Double precioMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> buscarPorNombreYTipo(String nombre, Integer tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> buscarPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}
}
