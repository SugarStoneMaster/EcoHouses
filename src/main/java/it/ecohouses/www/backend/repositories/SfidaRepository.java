package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.Sfida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SfidaRepository extends JpaRepository<Sfida, Long> {

    Sfida findByIdGruppoAndAbitazionePartecipante(Long idGruppo, Abitazione abitazionePartecipante);
    List<Sfida> findByIdGruppo(Long idGruppo);
    int countByIdGruppo(Long idGruppo);

    /**
     * Trova una sfida specifica in base all'ID.
     * @param idSfida ID della sfida.
     * @return La sfida corrispondente.
     */
    @Query("SELECT s FROM Sfida s WHERE s.idSfida = :idSfida")
    Sfida findByIdSfida(@Param("idSfida") Long idSfida);

    /**
     * Trova tutte le sfide associate a un'abitazione.
     * @param idAbitazione ID dell'abitazione.
     * @return Lista delle sfide associate all'abitazione.
     */
    @Query("SELECT s FROM Sfida s WHERE s.abitazionePartecipante.idAbitazione = :idAbitazione")
    List<Sfida> findSfideByAbitazione(@Param("idAbitazione") Long idAbitazione);
}

