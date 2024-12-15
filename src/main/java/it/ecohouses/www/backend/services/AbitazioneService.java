package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.repositories.AbitazioneRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AbitazioneService {

    private final AbitazioneRepository abitazioneRepository;
    private static final Logger logger = LoggerFactory.getLogger(AbitazioneService.class);

    public AbitazioneService(AbitazioneRepository abitazioneRepository) {
        this.abitazioneRepository = abitazioneRepository;
    }

    public Abitazione registraAbitazione(@Valid Abitazione abitazione) {
        if (abitazioneRepository.existsByNomeCasa(abitazione.getNomeCasa())) {
            logger.error("Abitazione already exists with name: {}", abitazione.getNomeCasa());
            throw new IllegalArgumentException("Il nome della casa è già in uso.");
        }

        if (abitazione.getMetratura() < 10) {
            throw new IllegalArgumentException("La metratura deve essere almeno di 10 metri quadrati.");
        }

        if (abitazione.getNumeroPersone() < 1) {
            throw new IllegalArgumentException("Deve esserci almeno una persona.");
        }

        String classeEnergetica = abitazione.getClasseEnergetica();
        if (!classeEnergetica.matches("^(A4|A3|A2|A1|B|C|D|E|F|G)$")) {
            throw new IllegalArgumentException("La classe energetica deve essere tra A4, A3, A2, A1, B, C, D, E, F, G.");
        }

        String immagine = abitazione.getImmagine();
        if (immagine == null || !immagine.matches("^(.*\\.(png|jpg))$")) {
            throw new IllegalArgumentException("L'immagine deve essere in formato PNG o JPG.");
        }

        try {
            return abitazioneRepository.save(abitazione);
        } catch (Exception e) {
            logger.error("Error saving abitazione: {}", e.getMessage());
            throw new IllegalArgumentException("Errore durante il salvataggio dell'abitazione.");
        }
    }
}
