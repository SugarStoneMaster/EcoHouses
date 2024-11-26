package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.Abitazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbitazioneRepository extends JpaRepository<Abitazione, Long> {
    //aggiungere altri metodi
    boolean existsByNomeCasa(String nomeCasa);
}
