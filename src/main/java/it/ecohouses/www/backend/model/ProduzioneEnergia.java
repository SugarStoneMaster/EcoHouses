package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class ProduzioneEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduzione;

    @ManyToOne
    private Abitazione abitazione;

    private double valoreProduzione; // kWh prodotti
    private LocalDateTime data;      // Data della produzione
}
