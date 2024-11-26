package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.repositories.AbitazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbitazioneService {

    @Autowired
    private AbitazioneRepository abitazioneRepository;

    public Abitazione registraAbitazione(Abitazione abitazione) {
        // Controlla se il nome della casa esiste già
        if (abitazioneRepository.existsByNomeCasa(abitazione.getNomeCasa())) {
            throw new IllegalArgumentException("Il nome della casa è già in uso");
        }

        // Salva e ritorna l'abitazione
        return abitazioneRepository.save(abitazione);
    }
}
