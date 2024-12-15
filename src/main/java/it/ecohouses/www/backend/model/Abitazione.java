package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
    //@Size(max = 20, message = "Il nome della casa può essere al massimo di 20 caratteri")
    @Column(nullable = false, unique = true)
    private String nomeCasa;

    //@Pattern(regexp = ".*\\.(png|jpg)$", message = "L'immagine deve essere in formato .png o .jpg")
    @Column
    private String immagine;

    @NotNull(message = "La metratura non può essere nulla")
    @Min(value = 10, message = "La metratura deve essere almeno 10 mq")
    @Column(nullable = false)
    private float metratura;

    @NotBlank(message = "La classe energetica non può essere vuota")
    /*@Pattern(
            regexp = "A4|A3|A2|A1|B|C|D|E|F|G",
            message = "La classe energetica deve essere un valore tra A4, A3, A2, A1, B, C, D, E, F, G"
    )*/
    @Column(nullable = false)
    private String classeEnergetica;

    @NotNull(message = "Il numero di persone non può essere nullo")
   // @Min(value = 1, message = "La casa deve contenere almeno una persona")
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

    public Abitazione(String nomeCasa, String immagine, float metratura, String classeEnergetica, int numeroPersone, String comune) {
        this.nomeCasa = nomeCasa;
        this.immagine = immagine;
        this.metratura = metratura;
        this.classeEnergetica = classeEnergetica;
        this.numeroPersone = numeroPersone;
        this.comune = comune;
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
