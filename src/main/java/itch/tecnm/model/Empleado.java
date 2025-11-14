package itch.tecnm.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "EMPLEADO")
public class Empleado {

    @Id
    @Column(length = 8)
    private String clave;

    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    private Integer puesto;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Atender> atenciones = new ArrayList<>();

    // Getters y Setters
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getPuesto() {
        return puesto;
    }

    public void setPuesto(Integer puesto) {
        this.puesto = puesto;
    }

    public List<Atender> getAtenciones() {
        return atenciones;
    }

    public void setAtenciones(List<Atender> atenciones) {
        this.atenciones = atenciones;
    }

		@Override
		public String toString() {
			return "Empleado [clave=" + clave + ", nombreCompleto=" + nombreCompleto + ", puesto=" + puesto + "]";
		}

		
		
	    

}
