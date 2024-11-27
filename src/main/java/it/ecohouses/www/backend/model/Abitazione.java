package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(nullable = false, updatable = false)
    private Long id;

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

    @Column(nullable = false)
    private float produzioneTotale;

    @Column(nullable = false)
    private float consumoTotale;

    @Column(nullable = false)
    private int punteggioTotale;

   /* @OneToMany(mappedBy = "abitazione", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsumoEnergia> consumiEnergia;

    @OneToMany(mappedBy = "abitazione", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProduzioneEnergia> produzioniEnergia;

    @OneToMany(mappedBy = "abitazione", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DispositivoIoT> dispositiviIoT; */

    @NotBlank(message = "Il nickname del gestore non può essere vuoto")
    @Column(name = "gestore_nickname", nullable = false)
    private String gestoreNickname;

    public Abitazione(String nomeCasa, String immagine, float metratura, String classeEnergetica, int numeroPersone, String comune, String gestoreNickname) {
        this.nomeCasa = nomeCasa;
        this.immagine = immagine;
        this.metratura = metratura;
        this.classeEnergetica = classeEnergetica;
        this.numeroPersone = numeroPersone;
        this.comune = comune;
        this.gestoreNickname = gestoreNickname;
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Abitazione{" +
                "id=" + id +
                ", nomeCasa='" + nomeCasa + '\'' +
                ", immagine='" + immagine + '\'' +
                ", metratura=" + metratura +
                ", classeEnergetica='" + classeEnergetica + '\'' +
                ", numeroPersone=" + numeroPersone +
                ", comune='" + comune + '\'' +
                ", produzioneTotale=" + produzioneTotale +
                ", consumoTotale=" + consumoTotale +
                ", punteggioTotale=" + punteggioTotale +
                ", gestoreNickname='" + gestoreNickname + '\'' +
                '}';
    }
}
