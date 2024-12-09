package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Commento;
import it.ecohouses.www.backend.model.Post;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.CommentoRepository;
import it.ecohouses.www.backend.repositories.PostRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialService {

    private final PostRepository postRepository;
    private final UtenteRepository utenteRepository;
    private final CommentoRepository commentoRepository;

    public SocialService(PostRepository postRepository,
                         UtenteRepository utenteRepository,
                         CommentoRepository commentoRepository) {
        this.postRepository = postRepository;
        this.utenteRepository = utenteRepository;
        this.commentoRepository = commentoRepository;
    }

    // Metodo per creare un post
    public Post creaPost(Post post) {
        Optional<Utente> optionalUtente = utenteRepository.findByNickname(post.getAutore().getNickname());

        if (optionalUtente.isPresent()) {
            Utente autore = optionalUtente.get();
            post.setAutore(autore);
            return postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Utente con nickname " + post.getAutore().getNickname() + " non trovato.");
        }
    }

    // Metodo per ottenere tutti i post
    public List<Post> getTuttiIPost() {
        return postRepository.findAll();
    }

    // Metodo per aggiungere un commento a un post
    public Commento aggiungiCommento(Long idPost, Commento commento) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new IllegalArgumentException("Post con ID " + idPost + " non trovato."));

        commento.setPost(post); // Associa il commento al post
        return commentoRepository.save(commento);
    }

    // Metodo per mettere un like a un commento
    public Commento mettiLike(Long idCommento) {
        Commento commento = commentoRepository.findById(idCommento)
                .orElseThrow(() -> new IllegalArgumentException("Commento con ID " + idCommento + " non trovato."));

        commento.setNumeroLike(commento.getNumeroLike() + 1); // Incrementa i like
        return commentoRepository.save(commento);
    }
}
