package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.ProduzioneEnergia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProduzioneEnergiaRepository extends JpaRepository<ProduzioneEnergia, Long> {
    List<ProduzioneEnergia> findByAbitazione_IdAbitazioneAndDataBetween(Long idAbitazione, LocalDateTime inizio, LocalDateTime fine);
}
