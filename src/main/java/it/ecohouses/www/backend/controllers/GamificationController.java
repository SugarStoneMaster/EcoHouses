package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.services.GamificationService;
import it.ecohouses.www.backend.model.Sfida;
import it.ecohouses.www.backend.model.Classifica;
import it.ecohouses.www.backend.model.ClassificaAbitazione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gamification")
public class GamificationController {

    // Costruttore per l'iniezione delle dipendenze
    @Autowired
    public GamificationController(GamificationService gamificationService) {
        this.gamificationService = gamificationService;
    }

    private final GamificationService gamificationService;

    // Ottiene la classifica locale con le prime 100 abitazioni e la posizione/punteggio dell'utente
    @GetMapping("/classificaLocale")
    public ResponseEntity<?> getClassificaLocale(@RequestParam String nickname) {
        try {
            // Recupera l'ID della classifica locale
            Classifica classificaLocale = gamificationService.getClassificaLocale(nickname);

            // Recupera le prime 100 abitazioni della classifica locale
            List<ClassificaAbitazione> top100Abitazioni = gamificationService.getTop100Abitazioni(classificaLocale.getIdClassifica());
            if (top100Abitazioni.isEmpty()) {
                return ResponseEntity.ok("Non ci sono altri utenti nella classifica.");
            }

            // Recupera posizione e punteggio dell'utente nella classifica locale
            Object[] posizioneEPunteggio = gamificationService.getPosizioneAndPunteggio(nickname, classificaLocale.getIdClassifica());

            // Prepara la risposta
            Map<String, Object> response = new HashMap<>();
            response.put("top100", top100Abitazioni);
            response.put("statistiche dell'utente", posizioneEPunteggio);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("La classifica non è al momento disponibile. Riprova più tardi.");
        }
    }

    // Ottiene la classifica globale con le prime 10 abitazioni e la posizione/punteggio dell'utente
    @GetMapping("/classificaGlobale")
    public ResponseEntity<?> getClassificaGlobale(@RequestParam String nickname) {
        try {
            // Recupera l'ID della classifica globale
            Classifica classificaGlobale = gamificationService.getClassificaGlobale();

            // Recupera le prime 100 abitazioni della classifica globale
            List<ClassificaAbitazione> top100Abitazioni = gamificationService.getTop100Abitazioni(classificaGlobale.getIdClassifica());

            // Recupera posizione e punteggio dell'utente nella classifica globale
            Object[] posizioneEPunteggio = gamificationService.getPosizioneAndPunteggio(nickname, classificaGlobale.getIdClassifica());

            // Prepara la risposta
            Map<String, Object> response = new HashMap<>();
            response.put("top100", top100Abitazioni);
            response.put("statistiche dell'utente", posizioneEPunteggio);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/sfidePredefinite")
    public ResponseEntity<?> getSfidePredefinite(@RequestParam String nickname) {
        try {
            // Genera le sfide predefinite usando il servizio
            List<Sfida> sfidePredefinite = gamificationService.generaSfidePredefinite(nickname);

            // Ritorna la lista di sfide come risposta
            return ResponseEntity.ok(sfidePredefinite);
        } catch (RuntimeException e) {
            // Gestione degli errori
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }

    @PostMapping(value = "/partecipaSfidaIndividuale")
    public ResponseEntity<Sfida> salvaSfida(@RequestBody Map<String, Object> richiesta) {
        try {
            // Estrazione dei parametri dalla mappa
            String difficolta = (String) richiesta.get("difficolta");
            String durata = (String) richiesta.get("durata");
            int punteggio = (int) richiesta.get("punteggio");
            float obiettivo = ((Number) richiesta.get("obiettivo")).floatValue();
            String nickname = (String) richiesta.get("nickname");

            // Chiamata al service
            Sfida sfidaSalvata = gamificationService.salvaSfidaIndividuale(difficolta, durata, punteggio, obiettivo, nickname);
            return new ResponseEntity<>(sfidaSalvata, HttpStatus.CREATED);
        }   catch (IllegalArgumentException e) {
            // Gestione degli errori
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/creaSfidaDiGruppo")
    public ResponseEntity<Sfida> creaSfidaDiGruppo(@RequestBody Map<String, Object> richiesta) {
        try {
            // Estrazione dei parametri dalla mappa
            String difficolta = (String) richiesta.get("difficolta");
            String durata = (String) richiesta.get("durata");
            String nickname = (String) richiesta.get("nickname");
            int partecipanti = (int) richiesta.get("partecipanti");

            //verifica che difficoltà e durata siano valide
            if (!Arrays.asList("FACILE", "MEDIA", "DIFFICILE").contains(difficolta.toUpperCase())) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (!Arrays.asList("SETTIMANALE", "MENSILE").contains(durata.toUpperCase())) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (partecipanti < 2) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            // Chiamata al service con la lista validata
            Sfida sfidaDiGruppo = gamificationService.creaSfidaDiGruppo(
                    difficolta, durata, nickname, partecipanti);
            return new ResponseEntity<>(sfidaDiGruppo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Gestione degli errori
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/partecipaSfidaDiGruppo")
    public ResponseEntity<Sfida> partecipaSfidaDiGruppo(@RequestBody Map<String, Object> richiesta) {
        try {
            // Estrazione dei parametri dalla mappa
            long idGruppo = ((Number) richiesta.get("idGruppo")).longValue();
            String nicknameCreatore = (String) richiesta.get("nicknameCreatore");
            String nicknamePartecipante = (String) richiesta.get("nicknamePartecipante");

            // Chiamata al service
            Sfida sfidaDiGruppo = gamificationService.partecipaASfidaGruppo(idGruppo, nicknameCreatore, nicknamePartecipante);
            return new ResponseEntity<>(sfidaDiGruppo, HttpStatus.CREATED);
        }   catch (IllegalArgumentException e) {
            // Gestione degli errori
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}

