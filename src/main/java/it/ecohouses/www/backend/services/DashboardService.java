package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.ConsumoEnergetico;
import it.ecohouses.www.backend.model.ProduzioneEnergia;
import it.ecohouses.www.backend.repositories.ConsumoEnergeticoRepository;
import it.ecohouses.www.backend.repositories.ProduzioneEnergiaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ConsumoEnergeticoRepository consumoEnergeticoRepository;
    private final ProduzioneEnergiaRepository produzioneEnergiaRepository;

    public DashboardService(ConsumoEnergeticoRepository consumoEnergeticoRepository,
                            ProduzioneEnergiaRepository produzioneEnergiaRepository) {
        this.consumoEnergeticoRepository = consumoEnergeticoRepository;
        this.produzioneEnergiaRepository = produzioneEnergiaRepository;
    }

    public List<ConsumoEnergetico> visualizzaConsumi(Long abitazioneId, LocalDateTime inizio, LocalDateTime fine) {
        // Recupero i consumi energetici per un abitazione dato un intervallo di tempo
        return consumoEnergeticoRepository.findConsumiByAbitazione(abitazioneId).stream()
                .filter(consumo -> consumo.getData().isAfter(inizio) && consumo.getData().isBefore(fine))
                .collect(Collectors.toList());
    }

    public List<ProduzioneEnergia> visualizzaProduzione(Long abitazioneId, LocalDateTime inizio, LocalDateTime fine) {
        // Recupero la produzione energetica per un abitazione dato un intervallo di tempo
        return produzioneEnergiaRepository.findProduzioneByAbitazione(abitazioneId).stream()
                .filter(produzione -> produzione.getData().isAfter(inizio) && produzione.getData().isBefore(fine))
                .collect(Collectors.toList());
    }
}
