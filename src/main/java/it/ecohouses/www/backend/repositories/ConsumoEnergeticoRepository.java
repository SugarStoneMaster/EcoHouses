package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.ConsumoEnergetico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsumoEnergeticoRepository extends JpaRepository<ConsumoEnergetico, Long> {
    @Query("SELECT c FROM ConsumoEnergetico c WHERE c.abitazione.idAbitazione = :idAbitazione")
    List<ConsumoEnergetico> findConsumiByAbitazione(@Param("idAbitazione") Long idAbitazione);

    @Query("SELECT AVG(c.valoreConsumo) FROM ConsumoEnergetico c " +
            "JOIN c.abitazione a " +
            "WHERE a.comune = :comune " +
            "AND a.metratura BETWEEN :superficieMin AND :superficieMax " +
            "AND a.numeroPersone = :numeroPersone " +
            "AND a.classeEnergetica = :classeEnergetica " +
            "AND c.data BETWEEN :dataInizio AND :dataFine")
    Double findMediaConsumiByComuneCaratteristicheEPeriodo(
            @Param("comune") String comune,
            @Param("superficieMin") Double superficieMin,
            @Param("superficieMax") Double superficieMax,
            @Param("numeroPersone") Integer numeroPersone,
            @Param("classeEnergetica") String classeEnergetica,
            @Param("dataInizio") LocalDateTime dataInizio,
            @Param("dataFine") LocalDateTime dataFine);


}
