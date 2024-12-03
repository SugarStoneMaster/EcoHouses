package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.ConsumoEnergetico;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.AbitazioneRepository;
import it.ecohouses.www.backend.repositories.ConsumoEnergeticoRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Log4j2
@Service
@RequiredArgsConstructor
public class PopulatorService {
    private final UtenteRepository utenteRepository;
    private final AbitazioneRepository abitazioneRepository;
    private final ConsumoEnergeticoRepository consumoenergeticoRepository;

    @Transactional
    public void populate() {
        log.info("Populating database...");


        Utente gestore = new Utente("Marco", "gestore@example.com", "DPBHTX46A26B064I", "a.jpg", true);
        Abitazione abitazione = new Abitazione("casina", "c.jpg", 22, "classe1", 4, "Fisciano");
        abitazioneRepository.save(abitazione);
        ConsumoEnergetico consumo = new ConsumoEnergetico(20.5,(LocalDateTime.now()));
        consumo.setAbitazione(abitazione);
        consumoenergeticoRepository.save(consumo);
        gestore.setAbitazione(abitazione);
        utenteRepository.save(gestore);


        Utente utente1 = new Utente("Giulia", "utente1@example.com", "FPBHTX46A26B064I", "c.jpg", false);
        Abitazione abitazione1 = new Abitazione("casetta", "d.jpg", 22, "classe1", 4, "Fisciano");
        abitazioneRepository.save(abitazione1);
        utente1.setAbitazione(abitazione1);
        utenteRepository.save(utente1);





        log.info("Database populated with utenti e gestori.");
    }

    }


