package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "comune")
public class Comune {

    @Id
    @Column(name = "nome_comune", nullable = false)
    private String nomeComune; // chiave primaria

    @Column(name = "media_consumo")
    private float mediaConsumo; // media consumo, tipo float

    // Costruttore per inizializzare l'entità
    public Comune(String nomeComune, float mediaConsumo) {
        this.nomeComune = nomeComune;
        this.mediaConsumo = mediaConsumo;
    }

    @Override
    public String toString() {
        return "Comune{" +
                "nomeComune='" + nomeComune + '\'' +
                ", mediaConsumo=" + mediaConsumo +
                '}';
    }
}

