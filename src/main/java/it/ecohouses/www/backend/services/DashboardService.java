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

    public List<ConsumoEnergetico> visualizzaConsumi(Abitazione abitazione, LocalDateTime inizio, LocalDateTime fine) {
        List<ConsumoEnergetico> listaConsumi;
        listaConsumi = consumoEnergeticoRepository.findConsumiByAbitazione(abitazione.getIdAbitazione()).stream()
                .filter(consumo -> consumo.getData().isAfter(inizio) && consumo.getData().isBefore(fine))
                .toList();

        return listaConsumi;
    }

    public List<ProduzioneEnergia> visualizzaProduzione(Abitazione abitazione, LocalDateTime inizio, LocalDateTime fine) {
        List<ProduzioneEnergia> listaProduzione;
        listaProduzione = produzioneEnergiaRepository.findProduzioneByAbitazione(abitazione.getIdAbitazione()).stream()
                .filter(consumo -> consumo.getData().isAfter(inizio) && consumo.getData().isBefore(fine))
                .toList();

        return listaProduzione;


    }
}
