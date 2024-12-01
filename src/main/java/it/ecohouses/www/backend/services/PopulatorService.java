package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.AbitazioneRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;


@Log4j2
@Service
@RequiredArgsConstructor
public class PopulatorService {
    private final UtenteRepository utenteRepository;
    private final AbitazioneRepository abitazioneRepository;

    @Transactional
    public void populate() throws ParseException {
        log.info("Populating database...");


        // Aggiungi un gestore

        Utente gestore = new Utente("Marco", "gestore@example.com", "DPBHTX46A26B064I", "a.jpg", true);
        // Aggiungi abitazione
        /*Abitazione abitazione = new Abitazione("Nome", "c.jpg", 22, "classe1", 4, "Fisciano", gestore);
        abitazioneRepository.save(abitazione);
        gestore.setAbitazione(abitazione);*/
        utenteRepository.save(gestore);

        Utente Utente = new Utente("Luca", "utente@example.com", "EPBHTX46A26B064I", "b.jpg", false);
        utenteRepository.save(Utente);


        Utente utente1 = new Utente("Giulia", "utente1@example.com", "FPBHTX46A26B064I", "c.jpg", false);

        utenteRepository.save(utente1);



        log.info("Database populated with utenti e gestori.");
    }

    }


