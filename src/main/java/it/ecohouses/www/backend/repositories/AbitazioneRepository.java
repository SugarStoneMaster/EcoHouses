package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.Utente;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbitazioneRepository extends JpaRepository<Abitazione, Long> {
    //aggiungere altri metodi
    boolean existsByNomeCasa(String nomeCasa);
    @Query("SELECT a FROM Abitazione a JOIN a.utentiAssociati u WHERE u = :Utente")
    Optional<Abitazione> findByUtente(@Param("utente") Utente Utente);

}