package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.*;
import it.ecohouses.www.backend.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ConsumoEnergetico> visualizzaConsumi(Abitazione abitazione, LocalDateTime inizio, LocalDateTime fine) {
        List<ConsumoEnergetico> lista_consumi = new ArrayList<>();
        lista_consumi = abitazioneRepository.findByAbitazioneConsumi(abitazione.getIdAbitazione()).stream()
                .filter(consumo -> consumo.getData().isAfter(inizio) && consumo.getData().isBefore(fine))
                .toList();

        return lista_consumi;
    }

    public List<ProduzioneEnergia> visualizzaProduzione(Abitazione abitazione, LocalDateTime inizio, LocalDateTime fine) {
        List<ProduzioneEnergia> lista_produzione = new ArrayList<>();
        lista_produzione = abitazioneRepository.findByAbitazioneProduzione(abitazione.getIdAbitazione()).stream()
                .filter(consumo -> consumo.getData().isAfter(inizio) && consumo.getData().isBefore(fine))
                .toList();

        return lista_produzione;


    }
}
