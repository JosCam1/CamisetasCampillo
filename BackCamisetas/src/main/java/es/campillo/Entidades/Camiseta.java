package es.campillo.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Camisetas")
@Builder
public class Camiseta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Precio", nullable = false)
    private double precio;

    @Column(name = "Descripción", nullable = false, length = 256)
    private String descripcion;

    @Lob
    @Column(name = "Foto", nullable = false, columnDefinition = "longblob")
    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

}
