package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.*;
import it.ecohouses.www.backend.repositories.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Log4j2
@Service
@RequiredArgsConstructor
public class PopulatorService {
    private final UtenteRepository utenteRepository;
    private final AbitazioneRepository abitazioneRepository;
    private final ConsumoEnergeticoRepository consumoenergeticoRepository;
    private final ProduzioneEnergiaRepository produzioneenergiaRepository;
    private final ClassificaRepository classificaRepository;
    private final ClassificaAbitazioneRepository classificaAbitazioneRepository;
    private final DispositivoIoTRepository dispositivoIoTRepository;
    private final PasswordEncoder passwordEncoder;
    private int i;


    @Transactional
    public void populate() {
        log.info("Populating database...");
        /*

        LocalDateTime startOfYear = LocalDateTime.now().withDayOfYear(1).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfYear = startOfYear.plusYears(1);

        //Prima abitazione
        Utente gestore = new Utente("Marco", "gestore@example.com", passwordEncoder.encode("DPBHTX46A26B064I"), "a.jpg", true);
        Abitazione abitazione = new Abitazione("casina", "c.jpg", 22, "classe1", 4, "Fisciano");
        abitazioneRepository.save(abitazione);
        gestore.setAbitazione(abitazione);
        utenteRepository.save(gestore);

        for (i = 0; i < 30; i++) {
            double randomProduzione = ThreadLocalRandom.current().nextDouble(5.0, 50.0); // Produzione casuale tra 5 e 50 kWh
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ProduzioneEnergia produzione = new ProduzioneEnergia(randomProduzione, randomDate);
            produzione.setAbitazione(abitazione);
            produzioneenergiaRepository.save(produzione);
        }

        for (i = 0; i < 100; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(5.0, 50.0); // Consumo casuale tra 5 e 50 kWh
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico randomConsumoEnergetico = new ConsumoEnergetico(randomConsumo, randomDate);
            randomConsumoEnergetico.setAbitazione(abitazione);
            consumoenergeticoRepository.save(randomConsumoEnergetico);
        }

        DispositivoIoT dispositivo1 = new DispositivoIoT(10001L, "Sensore Temperatura", "Sensore", true, false, abitazione);
        DispositivoIoT dispositivo2 = new DispositivoIoT(10002L, "Sensore Umidità", "Sensore", true, false, abitazione);
        DispositivoIoT dispositivo3 = new DispositivoIoT(10003L, "Telecamera", "Sicurezza", true, false, abitazione);
        DispositivoIoT dispositivo4 = new DispositivoIoT(10004L, "Luce Intelligente", "Illuminazione", true, false, abitazione);
        DispositivoIoT dispositivo5 = new DispositivoIoT(10005L, "Termostato", "Controllo Clima", true, false, abitazione);

        dispositivoIoTRepository.save(dispositivo1);
        dispositivoIoTRepository.save(dispositivo2);
        dispositivoIoTRepository.save(dispositivo3);
        dispositivoIoTRepository.save(dispositivo4);
        dispositivoIoTRepository.save(dispositivo5);


        //Seconda abitazione
        Utente gestoreA1 = new Utente("Gianmarco", "utente2@example.com", passwordEncoder.encode("DPATX46G38B064I"), "c.jpg", true);
        Utente utente1 = new Utente("Giulia", "utente1@example.com", passwordEncoder.encode("FPBHTX46A26B064I"), "c.jpg", false);
        Abitazione abitazione1 = new Abitazione("casetta", "d.jpg", 22, "classe1", 4, "Fisciano");
        abitazioneRepository.save(abitazione1);
        gestoreA1.setAbitazione(abitazione1);
        utente1.setAbitazione(abitazione1);
        utenteRepository.save(gestoreA1);
        utenteRepository.save(utente1);

        for (i = 0; i < 100; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(5.0, 50.0); // Consumo casuale tra 5 e 50 kWh
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico randomConsumoEnergetico = new ConsumoEnergetico(randomConsumo, randomDate);
            randomConsumoEnergetico.setAbitazione(abitazione1);
            consumoenergeticoRepository.save(randomConsumoEnergetico);
        }

        DispositivoIoT dispositivo6 = new DispositivoIoT(10006L, "Rilevatore Fumo", "Sicurezza", true, false, abitazione1);
        DispositivoIoT dispositivo7 = new DispositivoIoT(10007L, "Smart Plug", "Energia", true, false, abitazione1);
        DispositivoIoT dispositivo8 = new DispositivoIoT(10008L, "Controller IR", "Controllo", true, false, abitazione1);
        dispositivoIoTRepository.save(dispositivo6);
        dispositivoIoTRepository.save(dispositivo7);
        dispositivoIoTRepository.save(dispositivo8);

        // Abitazione 3
        Utente gestore3 = new Utente("Alessandra", "utente3@example.com", passwordEncoder.encode("DPBHTX67A12B064I"), "e.jpg", true);
        Abitazione abitazione3 = new Abitazione("villa", "e.jpg", 120, "classeA", 5, "Salerno");
        abitazioneRepository.save(abitazione3);
        gestore3.setAbitazione(abitazione3);
        utenteRepository.save(gestore3);

        for (i = 0; i < 50; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(5.0, 100.0);
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico consumo = new ConsumoEnergetico(randomConsumo, randomDate);
            consumo.setAbitazione(abitazione3);
            consumoenergeticoRepository.save(consumo);
        }

        DispositivoIoT dispositivo9 = new DispositivoIoT(10009L, "Rilevatore di Gas", "Sicurezza", true, false, abitazione3);
        DispositivoIoT dispositivo10 = new DispositivoIoT(10010L, "Sistema di Irrigazione", "Giardinaggio", true, false, abitazione3);
        dispositivoIoTRepository.save(dispositivo9);
        dispositivoIoTRepository.save(dispositivo10);

        // Abitazione 4
        Utente gestore4 = new Utente("Lucia", "utente4@example.com", passwordEncoder.encode("DPAHTX80B22B064I"), "f.jpg", true);
        Utente utente4a = new Utente("Mario", "utente5@example.com", passwordEncoder.encode("DPCHTX92C33B064I"), "g.jpg", false);
        Abitazione abitazione4 = new Abitazione("attico", "f.jpg", 90, "classeB", 3, "Napoli");
        abitazioneRepository.save(abitazione4);
        gestore4.setAbitazione(abitazione4);
        utente4a.setAbitazione(abitazione4);
        utenteRepository.save(gestore4);
        utenteRepository.save(utente4a);

        for (i = 0; i < 50; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(5.0, 100.0);
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico consumo = new ConsumoEnergetico(randomConsumo, randomDate);
            consumo.setAbitazione(abitazione4);
            consumoenergeticoRepository.save(consumo);
        }

        for (i = 0; i < 30; i++) {
            double randomProduzione = ThreadLocalRandom.current().nextDouble(10.0, 60.0);
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ProduzioneEnergia produzione = new ProduzioneEnergia(randomProduzione, randomDate);
            produzione.setAbitazione(abitazione4);
            produzioneenergiaRepository.save(produzione);
        }

        DispositivoIoT dispositivo11 = new DispositivoIoT(10011L, "Pannello Solare", "Energia", true, true, abitazione4);
        DispositivoIoT dispositivo12 = new DispositivoIoT(10012L, "Smart Fridge", "Elettrodomestici", true, false, abitazione4);
        dispositivoIoTRepository.save(dispositivo11);
        dispositivoIoTRepository.save(dispositivo12);

        // Abitazione 5
        Utente gestore5 = new Utente("Roberto", "utente6@example.com", passwordEncoder.encode("DPRHTX90A45B064I"), "h.jpg", true);
        Utente utente5a = new Utente("Anna", "utente7@example.com", passwordEncoder.encode("DPGHTX81D33B064I"), "i.jpg", false);
        Abitazione abitazione5 = new Abitazione("villetta", "h.jpg", 80, "classeB", 4, "Avellino");
        abitazioneRepository.save(abitazione5);
        gestore5.setAbitazione(abitazione5);
        utente5a.setAbitazione(abitazione5);
        utenteRepository.save(gestore5);
        utenteRepository.save(utente5a);

        // Generazione dei consumi obbligatori per Abitazione 5
        for (i = 0; i < 60; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(15.0, 100.0); // Consumo casuale tra 15 e 100 kWh
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico consumo = new ConsumoEnergetico(randomConsumo, randomDate);
            consumo.setAbitazione(abitazione5);
            consumoenergeticoRepository.save(consumo);
        }

        // Dispositivi IoT per Abitazione 5
        DispositivoIoT dispositivo13 = new DispositivoIoT(10013L, "Smart Thermostat", "Controllo Clima", true, false, abitazione5);
        DispositivoIoT dispositivo14 = new DispositivoIoT(10014L, "Rilevatore CO2", "Sicurezza", true, false, abitazione5);
        dispositivoIoTRepository.save(dispositivo13);
        dispositivoIoTRepository.save(dispositivo14);

        // Abitazione 6
        Utente gestore6 = new Utente("Francesco", "utente8@example.com", passwordEncoder.encode("DPFHTX77C15B064I"), "j.jpg", true);
        Abitazione abitazione6 = new Abitazione("loft", "j.jpg", 60, "classeC", 2, "Caserta");
        abitazioneRepository.save(abitazione6);
        gestore6.setAbitazione(abitazione6);
        utenteRepository.save(gestore6);

        // Generazione dei consumi obbligatori per Abitazione 6
        for (i = 0; i < 40; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(10.0, 70.0); // Consumo casuale tra 10 e 70 kWh
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico consumo = new ConsumoEnergetico(randomConsumo, randomDate);
            consumo.setAbitazione(abitazione6);
            consumoenergeticoRepository.save(consumo);
        }

        // Dispositivi IoT per Abitazione 6
        DispositivoIoT dispositivo15 = new DispositivoIoT(10015L, "Lampada Smart", "Illuminazione", true, false, abitazione6);
        DispositivoIoT dispositivo16 = new DispositivoIoT(10016L, "Videocamera Smart", "Sicurezza", true, false, abitazione6);
        dispositivoIoTRepository.save(dispositivo15);
        dispositivoIoTRepository.save(dispositivo16);

        // Abitazione 9 - Fisciano
        Utente gestoreFisciano = new Utente("Luigi", "utente9@example.com", passwordEncoder.encode("DPGHTX50F27B064I"), "k.jpg", true);
        Abitazione abitazione9 = new Abitazione("Abitazione 9", "k.jpg", 70, "classeA", 3, "Fisciano");
        abitazioneRepository.save(abitazione9);
        gestoreFisciano.setAbitazione(abitazione9);
        utenteRepository.save(gestoreFisciano);

// Aggiunta utenti per Abitazione 9
        List<Utente> utentiFisciano = Arrays.asList(
                new Utente("Mario", "utente20@example.com", passwordEncoder.encode("MRLTX50G27B064I"), "x.jpg", false),
                new Utente("Anna", "utente21@example.com", passwordEncoder.encode("ANNHTX63L15B064I"), "y.jpg", false)
        );
        utentiFisciano.forEach(utente -> {
            utente.setAbitazione(abitazione9);
            utenteRepository.save(utente);
        });

        for (int i = 0; i < 50; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(10.0, 60.0);
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico consumo = new ConsumoEnergetico(randomConsumo, randomDate);
            consumo.setAbitazione(abitazione9);
            consumoenergeticoRepository.save(consumo);
        }

        DispositivoIoT dispositivoFisciano1 = new DispositivoIoT(10017L, "Smart Speaker", "Intrattenimento", true, false, abitazione9);
        DispositivoIoT dispositivoFisciano2 = new DispositivoIoT(10018L, "Sensore Movimento", "Sicurezza", true, false, abitazione9);
        dispositivoIoTRepository.save(dispositivoFisciano1);
        dispositivoIoTRepository.save(dispositivoFisciano2);

// Abitazione 10 - Salerno
        Utente gestoreSalerno = new Utente("Claudia", "utente10@example.com", passwordEncoder.encode("DPCHTX63L15B064I"), "l.jpg", true);
        Abitazione abitazione10 = new Abitazione("Abitazione 10", "l.jpg", 150, "classeA+", 6, "Salerno");
        abitazioneRepository.save(abitazione10);
        gestoreSalerno.setAbitazione(abitazione10);
        utenteRepository.save(gestoreSalerno);

// Aggiunta utenti per Abitazione 10
        List<Utente> utentiSalerno = Arrays.asList(
                new Utente("Luca", "utente22@example.com", passwordEncoder.encode("LCTTX53L25B064I"), "z.jpg", false)
        );
        utentiSalerno.forEach(utente -> {
            utente.setAbitazione(abitazione10);
            utenteRepository.save(utente);
        });

        for (int i = 0; i < 70; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(20.0, 100.0);
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico consumo = new ConsumoEnergetico(randomConsumo, randomDate);
            consumo.setAbitazione(abitazione10);
            consumoenergeticoRepository.save(consumo);
        }

        DispositivoIoT dispositivoSalerno1 = new DispositivoIoT(10019L, "Videocitofono Smart", "Sicurezza", true, false, abitazione10);
        DispositivoIoT dispositivoSalerno2 = new DispositivoIoT(10020L, "Smart TV", "Intrattenimento", true, false, abitazione10);
        dispositivoIoTRepository.save(dispositivoSalerno1);
        dispositivoIoTRepository.save(dispositivoSalerno2);

// Abitazione 11 - Napoli
        Utente gestoreNapoli = new Utente("Alessia", "utente11@example.com", passwordEncoder.encode("DPNHTX92A23B064I"), "m.jpg", true);
        Abitazione abitazione11 = new Abitazione("Abitazione 11", "m.jpg", 90, "classeB", 4, "Napoli");
        abitazioneRepository.save(abitazione11);
        gestoreNapoli.setAbitazione(abitazione11);
        utenteRepository.save(gestoreNapoli);

// Nessun utente aggiuntivo per questa abitazione

        for (int i = 0; i < 60; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(15.0, 80.0);
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico consumo = new ConsumoEnergetico(randomConsumo, randomDate);
            consumo.setAbitazione(abitazione11);
            consumoenergeticoRepository.save(consumo);
        }

        DispositivoIoT dispositivoNapoli1 = new DispositivoIoT(10021L, "Frigorifero Smart", "Elettrodomestici", true, false, abitazione11);
        DispositivoIoT dispositivoNapoli2 = new DispositivoIoT(10022L, "Termostato Wi-Fi", "Climatizzazione", true, false, abitazione11);
        dispositivoIoTRepository.save(dispositivoNapoli1);
        dispositivoIoTRepository.save(dispositivoNapoli2);

// Abitazione 12 - Avellino
        Utente gestoreAvellino = new Utente("Federico", "utente12@example.com", passwordEncoder.encode("DPFHTX75E15B064I"), "n.jpg", true);
        Abitazione abitazione12 = new Abitazione("Abitazione 12", "n.jpg", 120, "classeA", 5, "Avellino");
        abitazioneRepository.save(abitazione12);
        gestoreAvellino.setAbitazione(abitazione12);
        utenteRepository.save(gestoreAvellino);

// Nessun utente aggiuntivo per questa abitazione

        for (int i = 0; i < 40; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(10.0, 50.0);
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico consumo = new ConsumoEnergetico(randomConsumo, randomDate);
            consumo.setAbitazione(abitazione12);
            consumoenergeticoRepository.save(consumo);
        }

        DispositivoIoT dispositivoAvellino1 = new DispositivoIoT(10023L, "Rilevatore Gas", "Sicurezza", true, false, abitazione12);
        DispositivoIoT dispositivoAvellino2 = new DispositivoIoT(10024L, "Sensore Luce", "Illuminazione", true, false, abitazione12);
        dispositivoIoTRepository.save(dispositivoAvellino1);
        dispositivoIoTRepository.save(dispositivoAvellino2);

// Abitazione 13 - Caserta
        Utente gestoreCaserta = new Utente("Giorgia", "utente13@example.com", passwordEncoder.encode("DPGHTX85D13B064I"), "o.jpg", true);
        Abitazione abitazione13 = new Abitazione("Abitazione 13", "o.jpg", 110, "classeC", 4, "Caserta");
        abitazioneRepository.save(abitazione13);
        gestoreCaserta.setAbitazione(abitazione13);
        utenteRepository.save(gestoreCaserta);

// Nessun utente aggiuntivo per questa abitazione

        for (int i = 0; i < 55; i++) {
            double randomConsumo = ThreadLocalRandom.current().nextDouble(12.0, 70.0);
            LocalDateTime randomDate = randomDateBetween(startOfYear, endOfYear);

            ConsumoEnergetico consumo = new ConsumoEnergetico(randomConsumo, randomDate);
            consumo.setAbitazione(abitazione13);
            consumoenergeticoRepository.save(consumo);
        }

        DispositivoIoT dispositivoCaserta1 = new DispositivoIoT(10025L, "Presa Intelligente", "Energia", true, false, abitazione13);
        DispositivoIoT dispositivoCaserta2 = new DispositivoIoT(10026L, "Videocamera Notturna", "Sicurezza", true, false, abitazione13);
        dispositivoIoTRepository.save(dispositivoCaserta1);
        dispositivoIoTRepository.save(dispositivoCaserta2);


        //Classifiche
        Classifica classificaLocaleFisciano = new Classifica(true, LocalDate.now(), null, "Fisciano");
        classificaRepository.save(classificaLocaleFisciano);

        ClassificaAbitazione classificaAbitazioniFisiciano = new ClassificaAbitazione(classificaLocaleFisciano, abitazione1, 1, 100);
        classificaAbitazioneRepository.save(classificaAbitazioniFisiciano);

        Classifica classificaGlobale = new Classifica(false, LocalDate.now(), null, null);
        classificaRepository.save(classificaGlobale);

        ClassificaAbitazione classificaAbitazioniGlobale = new ClassificaAbitazione(classificaGlobale, abitazione, 1, 100);
        classificaAbitazioneRepository.save(classificaAbitazioniGlobale);


         */


        log.info("Database populated with utenti e gestori.");
    }

    private LocalDateTime randomDateBetween(LocalDateTime start, LocalDateTime end) {
        long startEpoch = start.toEpochSecond(java.time.ZoneOffset.UTC);
        long endEpoch = end.toEpochSecond(java.time.ZoneOffset.UTC);
        long randomEpoch = ThreadLocalRandom.current().nextLong(startEpoch, endEpoch);
        return LocalDateTime.ofEpochSecond(randomEpoch, 0, java.time.ZoneOffset.UTC);
    }

}


