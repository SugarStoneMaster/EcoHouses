package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduzioneEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduzione;

    @ManyToOne
    private Abitazione abitazione;

    private double valoreProduzione; // kWh prodotti
    private LocalDateTime data;      // Data della produzione
}
