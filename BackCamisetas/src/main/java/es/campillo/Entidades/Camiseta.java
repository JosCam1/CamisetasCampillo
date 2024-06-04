package es.campillo.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

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

    @Column(name = "Precio", nullable = false)
    private double precio;

    @Column(name = "Descripción", nullable = false, length = 256)
    private String descripcion;

    @Column(name = "Descuento", nullable = false)
    private double descuento;

    @Lob
    @Column(name = "Foto", nullable = false, columnDefinition = "longblob")
    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "liga_id")
    private Liga liga;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

}
