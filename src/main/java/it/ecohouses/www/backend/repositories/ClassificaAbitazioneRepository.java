package it.ecohouses.www.backend.repositories;

import it.ecohouses.www.backend.model.ClassificaAbitazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassificaAbitazioneRepository extends JpaRepository<ClassificaAbitazione, Long> {

    // Query per ottenere i primi 10 utenti in una classifica
    @Query("SELECT ca FROM ClassificaAbitazione ca " +
            "WHERE ca.classifica.idClassifica = :idClassifica " +
            "ORDER BY ca.posizione ASC")
    List<ClassificaAbitazione> findTop100ByClassifica(@Param("idClassifica") Long idClassifica);

    //query per ottenere posizione e punetggio di un'abitazione in una classifica
    @Query("SELECT ca.posizione, ca.punteggio " +
            "FROM ClassificaAbitazione ca " +
            "WHERE ca.classifica.idClassifica = :idClassifica " +  // Confronta l'ID della Classifica
            "AND ca.abitazione.idAbitazione = :idAbitazione")     // Confronta l'ID dell'Abitazione
    Optional<Object[]> findPosizioneAndPunteggioByClassificaAndAbitazione(@Param("idClassifica") Long idClassifica,
                                                                          @Param("idAbitazione") Long idAbitazione);

    //query per controllare se una classifica di abitazioni esiste
    boolean existsByClassificaIdClassifica(Long idClassifica);

}

