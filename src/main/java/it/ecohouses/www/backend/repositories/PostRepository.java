package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
