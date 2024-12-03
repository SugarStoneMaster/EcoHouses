package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Post;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.PostRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocialService {

    private final PostRepository postRepository;
    private final UtenteRepository utenteRepository;

    public SocialService(PostRepository postRepository, UtenteRepository utenteRepository) {
        this.postRepository = postRepository;
        this.utenteRepository = utenteRepository;
    }

    public Post creaPost(String nickname, String testo, String immagine) {
        // Trova l'utente autore tramite il nickname
        Optional<Utente> optionalUtente = utenteRepository.findByNickname(nickname);

        if (optionalUtente.isPresent()) {
            Utente autore = optionalUtente.get();

            // Crea un nuovo post
            Post nuovoPost = new Post();
            nuovoPost.setAutore(autore);
            nuovoPost.setTesto(testo);
            nuovoPost.setImmagine(immagine);

            // Salva il post nel database
            return postRepository.save(nuovoPost);
        } else {
            throw new IllegalArgumentException("Utente con nickname " + nickname + " non trovato.");
        }
    }
}
