package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.ConsumoEnergetico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsumoEnergeticoRepository extends JpaRepository<ConsumoEnergetico, Long> {
    @Query("SELECT c FROM ConsumoEnergetico c WHERE c.abitazione.idAbitazione = :idAbitazione")
    List<ConsumoEnergetico> findConsumiByAbitazione(@Param("idAbitazione") Long idAbitazione);
}
