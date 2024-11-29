package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConsumoEnergetico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsumo;

    @ManyToOne
    @JoinColumn(name = "idAbitazione", nullable = false)
    private Abitazione abitazione;

    private double valoreConsumo; // kWh consumati
    private LocalDateTime data;   // Data del consumo

    // Getters e Setters
    public Long getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(Long idConsumo) {
        this.idConsumo = idConsumo;
    }

    public Abitazione getAbitazione() {
        return abitazione;
    }

    public void setAbitazione(Abitazione abitazione) {
        this.abitazione = abitazione;
    }

    public double getValoreConsumo() {
        return valoreConsumo;
    }

    public void setValoreConsumo(double valoreConsumo) {
        this.valoreConsumo = valoreConsumo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
