package itch.tecnm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="CLIENTE")

public class Cliente {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Integer Id;
	
	
	private String nombre;
	private String apellidos;
	private String email;
	private double credito;
	private String telefono;
	private Integer destacado;
	private String fotocliente;

	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getCredito() {
		return credito;
	}
	public void setCredito(double credito) {
		this.credito = credito;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Integer getDestacado() {
		return destacado;
	}
	public void setDestacado(Integer destacado) {
		this.destacado = destacado;
	}
	public String getFotocliente() {
		return fotocliente;
	}
	public void setFotocliente(String fotocliente) {
		this.fotocliente = fotocliente;
	}
	
	@Override
	public String toString() {
		return "Cliente [Id=" + Id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", credito=" + credito + ", telefono=" + telefono + ", destacado=" + destacado + ", fotocliente="
				+ fotocliente + "]";
	}
}
