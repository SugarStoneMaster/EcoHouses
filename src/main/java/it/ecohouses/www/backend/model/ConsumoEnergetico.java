package it.ecohouses.www.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
public class ConsumoEnergetico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsumo;

    @ManyToOne
    @JsonIgnore
    private Abitazione abitazione;

    private double valoreConsumo; // kWh consumati
    private LocalDateTime data;   // Data del consumo


    // Costruttore con argomenti per agevolare la creazione di oggetti
    public ConsumoEnergetico(double kw, LocalDateTime now) {
        this.valoreConsumo = kw;
        this.data = now;
    }
}
