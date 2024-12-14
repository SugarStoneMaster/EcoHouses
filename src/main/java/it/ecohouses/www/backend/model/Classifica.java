package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "classifica")
public class Classifica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClassifica;

    @Column(nullable = false)
    private boolean tipoClassifica; // true se è locale, false se è globale

    @Column(nullable = false)
    private LocalDate dataCreazione;

    @Column
    private LocalDate dataAggiornamento;

    @Column
    private String comune; // Comune può essere nullo per le classifiche globali

    // Costruttore per inizializzare la classifica
    public Classifica(boolean tipoClassifica, LocalDate dataCreazione, LocalDate dataAggiornamento, String comune) {
        this.tipoClassifica = tipoClassifica;
        this.dataCreazione = dataCreazione;
        this.dataAggiornamento = dataAggiornamento;
        this.comune = comune;
    }

    @Override
    public String toString() {
        return "Classifica{" +
                "idClassifica=" + idClassifica +
                ", tipoClassifica=" + tipoClassifica +
                ", dataCreazione=" + dataCreazione +
                ", dataAggiornamento=" + dataAggiornamento +
                ", comune='" + comune + '\'' +
                '}';
    }
}

