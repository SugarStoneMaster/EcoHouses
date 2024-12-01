package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "abitazione")
public class Abitazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAbitazione;

    @NotBlank(message = "Il nome della casa non può essere vuoto")
    @Column(nullable = false, unique = true)
    private String nomeCasa;

    @Column
    private String immagine;

    @NotNull(message = "La metratura non può essere nulla")
    @Positive(message = "La metratura deve essere un valore positivo")
    @Column(nullable = false)
    private float metratura;

    @NotBlank(message = "La classe energetica non può essere vuota")
    @Column(nullable = false)
    private String classeEnergetica;

    @NotNull(message = "Il numero di persone non può essere nullo")
    @Positive(message = "Il numero di persone deve essere un valore positivo")
    @Column(nullable = false)
    private int numeroPersone;

    @NotBlank(message = "Il comune non può essere vuoto")
    @Column(nullable = false)
    private String comune;

    @Column
    private float produzioneTotale;

    @Column
    private float consumoTotale;

    @Column
    private int punteggioTotale;

    @OneToMany(mappedBy = "abitazione", cascade = CascadeType.ALL)
    private List<ConsumoEnergetico> consumi;

    @OneToMany(mappedBy = "abitazione", cascade = CascadeType.ALL)
    private List<ProduzioneEnergia> produzioni;

    @OneToMany(mappedBy = "abitazione", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Utente> utentiAssociati = new ArrayList<>();

    public Abitazione(String nomeCasa, String immagine, float metratura, String classeEnergetica, int numeroPersone, String comune, Utente gestore) {
        if (gestore == null) {
            throw new IllegalArgumentException("L'abitazione deve avere un gestore associato.");
        }
        this.nomeCasa = nomeCasa;
        this.immagine = immagine;
        this.metratura = metratura;
        this.classeEnergetica = classeEnergetica;
        this.numeroPersone = numeroPersone;
        this.comune = comune;
        utentiAssociati.add(gestore);
    }

    @PrePersist
    @PreUpdate
    private void validateGestore() {
        if (utentiAssociati.isEmpty()) {
            throw new IllegalStateException("L'abitazione deve avere almeno un utente associato come gestore.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Abitazione that = (Abitazione) o;
        return Objects.equals(idAbitazione, that.idAbitazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAbitazione);
    }

    @Override
    public String toString() {
        return "Abitazione{" +
                "id=" + idAbitazione +
                ", nomeCasa='" + nomeCasa + '\'' +
                ", immagine='" + immagine + '\'' +
                ", metratura=" + metratura +
                ", classeEnergetica='" + classeEnergetica + '\'' +
                ", numeroPersone=" + numeroPersone +
                ", comune='" + comune + '\'' +
                ", produzioneTotale=" + produzioneTotale +
                ", consumoTotale=" + consumoTotale +
                ", punteggioTotale=" + punteggioTotale +
                '}';
    }
}
