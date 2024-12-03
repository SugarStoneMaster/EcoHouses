package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.Post;
import it.ecohouses.www.backend.services.SocialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final SocialService socialService;

    public PostController(SocialService socialService) {
        this.socialService = socialService;
    }

    @PostMapping("/crea")
    public ResponseEntity<Post> creaPost(
            @RequestParam String nickname,
            @RequestParam String testo,
            @RequestParam(required = false) String immagine
    ) {
        try {
            Post nuovoPost = socialService.creaPost(nickname, testo, immagine);
            return ResponseEntity.ok(nuovoPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
