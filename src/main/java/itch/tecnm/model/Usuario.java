package itch.tecnm.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="USUARIO")
public class Usuario {

    @Id
    private Integer id;
    

    private String username;
    private String nombre;
    private String email;
    private String password;
    private Integer estatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fechaRegistro")
    private LocalDateTime fechaRegistro;

    //fetch=FetchType.EAGER ESTA INTRUCCION ES PARA CUANDO ENCUENTRE UN ATRIBUTO IGUAL
    //TRAIGA DE LA RELACION TODOS AQUELLOS QUE TENGAN EL MIMO ID FORANEA
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name = "USUARIOPERFIL", //
        joinColumns = @JoinColumn(name = "idusuario"), //LA TABLA DONDE SE ESTA HACIENDO LA RELACION ACTUAL EJEMPLO USUARIO
        inverseJoinColumns = @JoinColumn(name = "idperfil") //LA TABLA QUE SE VA A RELACIONAR
    )
    private List<Perfil> perfiles;
    
    public void agregarPerfilUsuario(Perfil tmpPerfil) {
    	if(perfiles==null) {
    		//si no encuentra perfiles,crea una lista enlazada
    		perfiles=new LinkedList<Perfil>();
    	}
    	//si encuentra una lista, le agrega los perfiles encontrados
    	perfiles.add(tmpPerfil);
    }

    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getEstatus() { return estatus; }
    public void setEstatus(Integer estatus) { this.estatus = estatus; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public List<Perfil> getPerfiles() { return perfiles; }
    public void setPerfiles(List<Perfil> perfiles) { this.perfiles = perfiles; }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", perfiles=" + perfiles + "]";
    }
	

}
