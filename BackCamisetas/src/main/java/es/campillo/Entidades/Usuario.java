package es.campillo.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuarios")
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Apellido", nullable = false, length = 150)
    private String apellido;

    @Column(name = "CodigoPostal", nullable = false)
    private String codigoPostal;

    @Column(name = "Telefono", nullable = false, length = 9)
    private int telefono;

    @Column(name = "Ciudad", nullable = false, length = 150)
    private String ciudad;

    @Column(name = "Direccion", nullable = false)
    private String direccion;

    @Column(name = "Email", nullable = false, length = 120)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Lob
    @Column(name = "Foto", nullable = false, columnDefinition = "longblob")
    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
}
