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
public class ProduzioneEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduzione;

    @ManyToOne
    @JsonIgnore
    private Abitazione abitazione;

    private double valoreProduzione; // kWh consumati
    private LocalDateTime data;   // Data del consumo

    // Costruttore con argomenti per agevolare la creazione di oggetti
    public ProduzioneEnergia(double kw, LocalDateTime now) {
        this.valoreProduzione = kw;
        this.data = now;
    }
}
