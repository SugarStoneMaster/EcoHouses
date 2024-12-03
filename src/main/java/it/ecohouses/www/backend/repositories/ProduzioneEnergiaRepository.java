package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.ProduzioneEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduzioneEnergiaRepository extends JpaRepository<ProduzioneEnergia, Long> {
    @Query("SELECT p FROM ProduzioneEnergia p WHERE p.abitazione.idAbitazione = :idAbitazione")
    List<ProduzioneEnergia> findProduzioneByAbitazione(@Param("idAbitazione") Long idAbitazione);
}
