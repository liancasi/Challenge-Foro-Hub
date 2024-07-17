package com.aluracursos.Foro.Hub.domain.Usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;


@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(name = "correo_electronico")
    private String correoElectronico;
    private String contrasena;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )


    private static final PasswordEncoder passwordEncoder;

    static {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario(DatosRegistroUsuario datosRegistroUsuario) {
        this.nombre = datosRegistroUsuario.nombre();
        this.correoElectronico = datosRegistroUsuario.correoElectronico();
        this.contrasena = passwordEncoder.encode(datosRegistroUsuario.contrasena());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void actualizarDatos(DatosActualizaUsuario datosActualizarUsuario) {
        if(datosActualizarUsuario.nombre() != null){
            this.nombre = datosActualizarUsuario.nombre();
        }
        if(datosActualizarUsuario.correoElectronico()!= null){
            this.correoElectronico = datosActualizarUsuario.correoElectronico();
        }
        if (datosActualizarUsuario.contrasena() != null) {
            this.contrasena = passwordEncoder.encode(datosActualizarUsuario.contrasena());
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}
