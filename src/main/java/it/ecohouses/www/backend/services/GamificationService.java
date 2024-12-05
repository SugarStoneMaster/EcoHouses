package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.ClassificaAbitazioneRepository;
import it.ecohouses.www.backend.repositories.ClassificaRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GamificationService {

    private final ClassificaRepository classificaRepository;
    private final ClassificaAbitazioneRepository classificaAbitazioneRepository;
    private final UtenteRepository utenteRepository;

    // Costruttore per l'injection dei repository
    public GamificationService(ClassificaRepository classificaRepository,
                               ClassificaAbitazioneRepository classificaAbitazioneRepository,
                               UtenteRepository utenteRepository) {
        this.classificaRepository = classificaRepository;
        this.classificaAbitazioneRepository = classificaAbitazioneRepository;
        this.utenteRepository = utenteRepository;
    }

    /*
     * Recupera la classifica locale più recente per un dato utente.
     */
    public long getClassificaLocale(String nickname) {
        // Recupera il comune dell'utente
        String comune = utenteRepository.findComuneByNickname(nickname);
        System.out.println("Comune: " + comune);

        // Recupera la classifica locale più recente per il comune
        return classificaRepository.findLatestLocalIdByComune(comune)
                .orElseThrow(() -> new RuntimeException("Classifica locale non trovata per il comune: " + comune));
    }

    public long getClassificaGlobale() {
        // Recupera la classifica globale più recente
        return classificaRepository.findLatestGlobalId()
                .orElseThrow(() -> new RuntimeException("Classifica globale non trovata"));
    }

    /**
     * Ottiene le prime 10 abitazioni di una classifica.
     * @return Una lista delle prime 10 abitazioni nella classifica.
     */
    public List<Object[]> getTop10Abitazioni(Long idClassifica) {
        // Usa il repository per recuperare le prime 10 abitazioni della classifica
        return classificaAbitazioneRepository.findTop10ByClassifica(idClassifica);
    }


    /**
     * Ottiene la posizione e il punteggio dell'abitazione dell'utente in una classifica
     * @param nickname Il nickname dell'utente.
     * @param idClassifica L'ID della classifica da cui vogliamo ottenere la posizione.
     * @return La posizione e il punteggio dell'abitazione dell'utente.
     */
    public Object[] getPosizioneAndPunteggio(String nickname, Long idClassifica) {
        // Recupera l'abitazione dell'utente in base al nickname
        Optional<Utente> utenteOpt = utenteRepository.findByNickname(nickname);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            Long idAbitazione = utente.getAbitazione().getIdAbitazione();

            // Recupera la posizione e il punteggio dell'abitazione dell'utente nella classifica
            return classificaAbitazioneRepository.findPosizioneAndPunteggioByClassificaAndAbitazione(idClassifica, idAbitazione)
                    .orElse(null);
        }
        return null;
    }


}

