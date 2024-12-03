package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dispositivoIoT")
public class DispositivoIoT {

    @Id
    private Long numeroSerie;

    @Column(nullable = false)
    private String nomeDispositivo;

    @Column(nullable = false)
    private String tipoDispositivo;

    @Column(nullable = false)
    private boolean statoConnessione;

    @Column(nullable = false)
    private boolean statoAccensione;

    @ManyToOne
    private Abitazione abitazione;

    @PrePersist
    public void prePersist() {
        this.statoConnessione = true; // Imposta statoConnessione a true
        this.statoAccensione = false; // Imposta statoAccensione a false
    }
}
