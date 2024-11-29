package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.*;
import it.ecohouses.www.backend.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<ConsumoEnergetico> visualizzaConsumi(Utente utente, LocalDateTime inizio, LocalDateTime fine) {
        Optional<Abitazione> abitazione = abitazioneRepository.findByUtente(utente);
        return consumoEnergeticoRepository.findByAbitazione_IdAbitazioneAndDataBetween(abitazione.get().getIdAbitazione(), inizio, fine);
    }

    public List<ProduzioneEnergia> visualizzaProduzione(Utente utente, LocalDateTime inizio, LocalDateTime fine) {
        Optional<Abitazione> abitazione = abitazioneRepository.findByUtente(utente);
        return produzioneEnergiaRepository.findByAbitazione_IdAbitazioneAndDataBetween(abitazione.get().getIdAbitazione(), inizio, fine);
    }
}
