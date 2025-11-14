package itch.tecnm.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class DetallePedidoId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idPedido;
    private Integer idProducto;

    // Getters y Setters
    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    // equals y hashCode obligatorios para clave compuesta
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetallePedidoId)) return false;
        DetallePedidoId that = (DetallePedidoId) o;
        return idPedido.equals(that.idPedido) && idProducto.equals(that.idProducto);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(idPedido, idProducto);
    }
}
