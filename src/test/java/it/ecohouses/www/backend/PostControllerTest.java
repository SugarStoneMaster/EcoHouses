package it.ecohouses.www.backend;

import it.ecohouses.www.backend.controllers.PostController;
import it.ecohouses.www.backend.model.Post;
import it.ecohouses.www.backend.services.SocialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private SocialService socialService;

    private Post post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        post = new Post();
    }

    //TC_S_1
    @Test
    void testCreaPost_TextoTooLong() {
        post.setTesto("a".repeat(600));
        post.setImmagine("immagine.jpg");

      fail("Testo superiore ai 500 caratteri");
    }

    //TC_S_2
    @Test
    void testCreaPost_ImmagineTooLarge() {
        post.setTesto("Testo valido");
        post.setImmagine("immagine.jpg");

        fail("Immagine troppo grande");
    }

    //TC_S_3
    @Test
    void testCreaPost_ImmagineNonSupportata() {
        post.setTesto("Testo valido");
        post.setImmagine("immagine.draw.io");


       fail("Immagine non supportata");
    }

    //TC_S_4
    @Test
    void testCreaPost_Valid() {
        // Simula un post valido
        post.setTesto("Testo valido");
        post.setImmagine("immagine.jpg");

        when(socialService.creaPost(post)).thenReturn(post);

        ResponseEntity<Post> response = postController.creaPost(post);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

}
