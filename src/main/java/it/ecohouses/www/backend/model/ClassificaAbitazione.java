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
@Table(name = "classifica_abitazione")
public class ClassificaAbitazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClassificaAbitazione;

    @ManyToOne
    private Classifica classifica;

    @ManyToOne
    private Abitazione abitazione;

    @Column(nullable = false)
    private int posizione;

    @Column(nullable = false)
    private int punteggio;

    // Costruttore per inizializzare ClassificaAbitazione
    public ClassificaAbitazione(Classifica classifica, Abitazione abitazione, int posizione, int punteggio) {
        this.classifica = classifica;
        this.abitazione = abitazione;
        this.posizione = posizione;
        this.punteggio = punteggio;
    }

    @Override
    public String toString() {
        return "ClassificaAbitazione{" +
                "idClassificaAbitazione=" + idClassificaAbitazione +
                ", classifica=" + classifica +
                ", abitazione=" + abitazione +
                ", posizione=" + posizione +
                ", punteggio=" + punteggio +
                '}';
    }
}

