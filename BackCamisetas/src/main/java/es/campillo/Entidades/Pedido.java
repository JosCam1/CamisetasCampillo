package es.campillo.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pedidos")
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Fecha")
    private Date fecha;

    @Column(name = "Estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "pedido_camiseta",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "camiseta_id")
    )
    private List<Camiseta> camisetas;

}