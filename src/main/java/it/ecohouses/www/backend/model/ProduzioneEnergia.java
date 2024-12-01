package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProduzioneEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduzione;

    @ManyToOne
    private Abitazione abitazione;

    private double valoreProduzione; // kWh prodotti
    private LocalDateTime data;      // Data della produzione

    // Getters e Setters
    public Long getIdProduzione() {
        return idProduzione;
    }

    public void setIdProduzione(Long idProduzione) {
        this.idProduzione = idProduzione;
    }

    public Abitazione getAbitazione() {
        return abitazione;
    }

    public void setAbitazione(Abitazione abitazione) {
        this.abitazione = abitazione;
    }

    public double getValoreProduzione() {
        return valoreProduzione;
    }

    public void setValoreProduzione(double valoreProduzione) {
        this.valoreProduzione = valoreProduzione;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
