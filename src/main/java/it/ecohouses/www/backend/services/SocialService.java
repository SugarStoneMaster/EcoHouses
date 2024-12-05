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

    public Post creaPost(Post post) {
        // Trova l'utente autore tramite il nickname
        Optional<Utente> optionalUtente = utenteRepository.findByNickname(post.getAutore().getNickname());

        if (optionalUtente.isPresent()) {
            Utente autore = optionalUtente.get();
            post.setAutore(autore);

            // Salva il post nel database
            return postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Utente con nickname " + post.getAutore().getNickname() + " non trovato.");
        }
    }
}
