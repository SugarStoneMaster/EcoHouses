package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "sfida")


public class Sfida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSfida;

    @Column(nullable = false)
    private boolean tipoSfida; // true = gruppo, false = individuale

    @Column(nullable = false)
    @Pattern(regexp = "FACILE|MEDIA|DIFFICILE", message = "Difficoltà non valida")
    private String difficolta; // FACILE, MEDIA, DIFFICILE

    @Column(nullable = false)
    @Pattern(regexp = "GIORNALIERA|SETTIMANALE|MENSILE", message = "Durata non valida")
    private String durata; // GIORNALIERA, SETTIMANALE, MENSILE

    @Column(nullable = false)
    private int punteggio;

    @Column(nullable = false)
    private float obiettivo;

    @Column(nullable = false)
    private LocalDateTime dataSfida;

    @Column(nullable = false)
    private boolean completamento; // true = completata, false = in corso

    @Column
    private Long idGruppo; // Può essere null (per sfide individuali)

    @Column
    private Boolean attivazione; // Può essere null (per sfide di gruppo)

    @Column
    private Integer numeroPartecipanti; // Può essere null (per sfide di gruppo)

    @ManyToOne
    private Abitazione abitazionePartecipante;

    // Costanti per i valori validi
    public static final String DIFFICOLTA_FACILE = "FACILE";
    public static final String DIFFICOLTA_MEDIA = "MEDIA";
    public static final String DIFFICOLTA_DIFFICILE = "DIFFICILE";

    public static final String DURATA_GIORNALIERA = "GIORNALIERA";
    public static final String DURATA_SETTIMANALE = "SETTIMANALE";
    public static final String DURATA_MENSILE = "MENSILE";


}

