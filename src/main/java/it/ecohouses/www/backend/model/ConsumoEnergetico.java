package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class ConsumoEnergetico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsumo;

    @ManyToOne
    private Abitazione abitazione;

    private double valoreConsumo; // kWh consumati
    private LocalDateTime data;   // Data del consumo

    public ConsumoEnergetico( double kw, LocalDateTime now) {
    }
}
