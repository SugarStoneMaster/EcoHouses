package it.ecohouses.www.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPost;

    @ManyToOne
    private Utente autore; // L'utente che ha creato il post

    private String testo;  // Testo del post

    private String immagine; // Percorso o URL immagine, opzionale
}
