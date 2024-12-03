package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.DispositivoIoT;
import it.ecohouses.www.backend.model.ProduzioneEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DispositivoIoTRepository extends JpaRepository<DispositivoIoT, Long> {
    boolean existsByNumeroSerie(Long numeroSerie);
    boolean existsByNomeDispositivo(String nomeDispositivo);

    @Query("SELECT d FROM DispositivoIoT d WHERE d.abitazione.idAbitazione = :idAbitazione")
    List<ProduzioneEnergia> findDispositiviByAbitazione(@Param("idAbitazione") Long idAbitazione);

}
