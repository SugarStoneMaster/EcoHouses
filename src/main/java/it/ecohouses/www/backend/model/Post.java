package it.ecohouses.www.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPost;

    @ManyToOne
    private Utente autore;

    private String testo;  // Testo del post

    private String immagine; // Percorso o URL immagine, opzionale

    // Costruttore con parametri
    public Post(Utente autore, String testo, String immagine) {
        this.autore = autore;
        this.testo = testo;
        this.immagine = immagine;
    }

    // Getter personalizzato per mostrare solo il nickname dell'autore
    @JsonProperty("autore")
    public String getAutoreNickname() {
        return this.autore != null ? this.autore.getNickname() : null;
    }
}
