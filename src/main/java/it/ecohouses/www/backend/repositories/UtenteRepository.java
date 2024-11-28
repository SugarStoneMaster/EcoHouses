package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String> {

    Optional<Utente> findByNickname(String nickname);
    Optional<Utente> findByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);


}
