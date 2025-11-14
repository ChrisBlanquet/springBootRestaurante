package itch.tecnm.model;

import java.io.Serializable;
import java.util.Objects;

public class AtenderId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer pedido;   // Debe coincidir con el tipo y nombre del campo @Id
    private String empleado;
    
    public AtenderId() {}

    public AtenderId(Integer pedido, String empleado) {
        this.pedido = pedido;
        this.empleado = empleado;
    }

	public Integer getPedido() {
		return pedido;
	}

	public void setPedido(Integer pedido) {
		this.pedido = pedido;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(empleado, pedido);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AtenderId other = (AtenderId) obj;
		return Objects.equals(empleado, other.empleado) && Objects.equals(pedido, other.pedido);
	}
    
    

}
