package es.campillo.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Equipos")
@Builder
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Lob
    @Column(name = "Foto", nullable = false)
    private Blob foto;

    @ManyToOne
    @JoinColumn(name = "liga_id")
    private Liga liga;
}
