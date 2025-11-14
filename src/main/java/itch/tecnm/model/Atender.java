package itch.tecnm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ATENDER")
@IdClass(AtenderId.class)
public class Atender {
	
	//Relacion con pedido
    @Id
    @ManyToOne
    @JoinColumn(name = "idPedido", referencedColumnName = "idPedido")
    private Pedido pedido;

    //Relacion con empleado
    @Id
    @ManyToOne
    @JoinColumn(name = "idEmpleado", referencedColumnName = "clave")
    private Empleado empleado;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		return "Atender [pedido=" + pedido + ", empleado=" + empleado + "]";
	}
    
    
	
}
