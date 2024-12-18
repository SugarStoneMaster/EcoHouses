package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.Classifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassificaRepository extends JpaRepository<Classifica, Long> {

    @Query("SELECT c FROM Classifica c " +
            "WHERE c.comune = :comune " +
            "AND c.tipoClassifica = true " +
            "ORDER BY c.dataCreazione DESC")
    Optional<Classifica> findLatestLocalByComune(String comune);


    @Query("SELECT c.idClassifica FROM Classifica c " +
            "WHERE c.tipoClassifica = false " +
            "ORDER BY c.dataCreazione DESC")
    Optional<Classifica> findLatestGlobal();

    boolean existsByComune(String comune);
}


