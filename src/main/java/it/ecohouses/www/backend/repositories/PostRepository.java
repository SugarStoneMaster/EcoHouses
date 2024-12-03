package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Puoi aggiungere query personalizzate qui, se necessario
}
