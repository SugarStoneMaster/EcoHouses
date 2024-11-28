package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.*;
import it.ecohouses.www.backend.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {
    private final AbitazioneRepository abitazioneRepository;
    private final ConsumoEnergeticoRepository consumoEnergeticoRepository;
    private final ProduzioneEnergiaRepository produzioneEnergiaRepository;

    public DashboardService(AbitazioneRepository abitazioneRepository,
                            ConsumoEnergeticoRepository consumoEnergeticoRepository,
                            ProduzioneEnergiaRepository produzioneEnergiaRepository) {
        this.abitazioneRepository = abitazioneRepository;
        this.consumoEnergeticoRepository = consumoEnergeticoRepository;
        this.produzioneEnergiaRepository = produzioneEnergiaRepository;
    }

    public List<ConsumoEnergetico> visualizzaConsumi(Utente Utente, LocalDateTime inizio, LocalDateTime fine) {
        Abitazione abitazione = abitazioneRepository.findByUtente(Utente);
        return consumoEnergeticoRepository.findByAbitazione_IdAbitazioneAndDataBetween(abitazione.getIdAbitazione(), inizio, fine);
    }

    public List<ProduzioneEnergia> visualizzaProduzione(Utente Utente, LocalDateTime inizio, LocalDateTime fine) {
        Abitazione abitazione = abitazioneRepository.findByUtente(Utente);
        return produzioneEnergiaRepository.findByAbitazione_IdAbitazioneAndDataBetween(abitazione.getIdAbitazione(), inizio, fine);
    }
}
