package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.Commento;
import it.ecohouses.www.backend.model.Post;
import it.ecohouses.www.backend.services.SocialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final SocialService socialService;

    public PostController(SocialService socialService) {
        this.socialService = socialService;
    }

    @PostMapping("/crea")
    public ResponseEntity<Post> creaPost(@RequestBody Post post) {
        try {
            Post nuovoPost = socialService.creaPost(post);
            return ResponseEntity.ok(nuovoPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/tutti")
    public ResponseEntity<List<Post>> ottieniTuttiIPost() {
        List<Post> posts = socialService.getTuttiIPost();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{idPost}/commenta")
    public ResponseEntity<Commento> aggiungiCommento(@PathVariable Long idPost, @RequestBody Commento commento) {
        try {
            Commento nuovoCommento = socialService.aggiungiCommento(idPost, commento);
            return ResponseEntity.ok(nuovoCommento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/commenti/{idCommento}/like")
    public ResponseEntity<Commento> mettiLike(@PathVariable Long idCommento) {
        try {
            Commento commentoConLike = socialService.mettiLike(idCommento);
            return ResponseEntity.ok(commentoConLike);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
