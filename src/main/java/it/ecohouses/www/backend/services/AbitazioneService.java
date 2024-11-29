package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.AbitazioneRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AbitazioneService {

    @Autowired
    private AbitazioneRepository abitazioneRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public Abitazione registraAbitazione(Abitazione abitazione, String nicknameGestore) {
        // Controlla se l'utente è un gestore valido
        if (!utenteRepository.existsByNicknameAndGestoreTrue(nicknameGestore)) {
            throw new IllegalArgumentException("L'utente specificato non è un gestore valido.");
        }

        // Controlla se il nome della casa esiste già
        if (abitazioneRepository.existsByNomeCasa(abitazione.getNomeCasa())) {
            throw new IllegalArgumentException("Il nome della casa è già in uso");
        }

        // Recupera l'utente gestore
        Utente gestore = utenteRepository.findByNickname(nicknameGestore)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato."));

        // Aggiungi il gestore alla lista degli utenti associati
        abitazione.getUtentiAssociati().add(gestore);

        // Salva e ritorna l'abitazione
        return abitazioneRepository.save(abitazione);
    }


    public Optional<Abitazione> getAbitazioneByUtente(Utente utente) {
        return abitazioneRepository.findByUtente(utente);
    }

}
