package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.ConsumoEnergetico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsumoEnergeticoRepository extends JpaRepository<ConsumoEnergetico, Long> {
    List<ConsumoEnergetico> findByAbitazione_IdAbitazioneAndDataBetween(Long idAbitazione, LocalDateTime inizio, LocalDateTime fine);
}