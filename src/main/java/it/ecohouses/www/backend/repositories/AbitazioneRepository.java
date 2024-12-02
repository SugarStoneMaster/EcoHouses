package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.ConsumoEnergetico;
import it.ecohouses.www.backend.model.ProduzioneEnergia;
import it.ecohouses.www.backend.model.Utente;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbitazioneRepository extends JpaRepository<Abitazione, Long> {

    boolean existsByNomeCasa(String nomeCasa);

    @Query("SELECT c FROM ConsumoEnergetico c WHERE c.abitazione.idAbitazione = :idAbitazione")
    List<ConsumoEnergetico> findByAbitazioneConsumi(@Param("idAbitazione") Long idAbitazione);

    @Query("SELECT p FROM ProduzioneEnergia p WHERE p.abitazione.idAbitazione = :idAbitazione")
    List<ProduzioneEnergia> findByAbitazioneProduzione(@Param("idAbitazione") Long idAbitazione);

}

