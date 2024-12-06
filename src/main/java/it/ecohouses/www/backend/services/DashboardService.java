package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.ConsumoEnergetico;
import it.ecohouses.www.backend.model.ProduzioneEnergia;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.ConsumoEnergeticoRepository;
import it.ecohouses.www.backend.repositories.ProduzioneEnergiaRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ConsumoEnergeticoRepository consumoEnergeticoRepository;
    private final ProduzioneEnergiaRepository produzioneEnergiaRepository;
    private final UtenteRepository utenteRepository;

    public DashboardService(ConsumoEnergeticoRepository consumoEnergeticoRepository,
                            ProduzioneEnergiaRepository produzioneEnergiaRepository,
                            UtenteRepository utenteRepository) {
        this.consumoEnergeticoRepository = consumoEnergeticoRepository;
        this.produzioneEnergiaRepository = produzioneEnergiaRepository;
        this.utenteRepository = utenteRepository;
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

    public Double getMediaConsumi(String nicknameUtente, LocalDateTime dataInizio, LocalDateTime dataFine) {
        Utente utente = utenteRepository.findByNickname(nicknameUtente)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        // Ottieni l'abitazione associata
        Abitazione abitazione = utente.getAbitazione();
        if (abitazione == null) {
            throw new IllegalArgumentException("Abitazione non associata all'utente");
        }

        Double superficieMin = abitazione.getMetratura() * 0.9;
        Double superficieMax = abitazione.getMetratura() * 1.1;
        Integer numeroPersone = abitazione.getNumeroPersone();
        String classeEnergetica = abitazione.getClasseEnergetica();
        String comune = abitazione.getComune(); // Prendiamo il comune dall'oggetto

        return consumoEnergeticoRepository.findMediaConsumiByComuneCaratteristicheEPeriodo(
                comune, superficieMin, superficieMax, numeroPersone, classeEnergetica, dataInizio, dataFine);
    }
}
