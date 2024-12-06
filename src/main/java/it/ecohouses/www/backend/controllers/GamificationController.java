package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.services.GamificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Ottiene la classifica locale con le prime 10 abitazioni e la posizione/punteggio dell'utente
    @GetMapping("/classificaLocale")
    public ResponseEntity<?> getClassificaLocale(@RequestParam String nickname) {
        try {
            // Recupera l'ID della classifica locale
            long idClassificaLocale = gamificationService.getClassificaLocale(nickname);

            // Recupera le prime 10 abitazioni della classifica locale
            List<Object[]> top10Abitazioni = gamificationService.getTop10Abitazioni(idClassificaLocale);

            // Recupera posizione e punteggio dell'utente nella classifica locale
            Object[] posizioneEPunteggio = gamificationService.getPosizioneAndPunteggio(nickname, idClassificaLocale);

            // Prepara la risposta
            Map<String, Object> response = new HashMap<>();
            response.put("top10", top10Abitazioni);
            response.put("statistiche dell'utente", posizioneEPunteggio);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Ottiene la classifica globale con le prime 10 abitazioni e la posizione/punteggio dell'utente
    @GetMapping("/classificaGlobale")
    public ResponseEntity<?> getClassificaGlobale(@RequestParam String nickname) {
        try {
            // Recupera l'ID della classifica globale
            long idClassificaGlobale = gamificationService.getClassificaGlobale();

            // Recupera le prime 10 abitazioni della classifica globale
            List<Object[]> top10Abitazioni = gamificationService.getTop10Abitazioni(idClassificaGlobale);

            // Recupera posizione e punteggio dell'utente nella classifica globale
            Object[] posizioneEPunteggio = gamificationService.getPosizioneAndPunteggio(nickname, idClassificaGlobale);

            // Prepara la risposta
            Map<String, Object> response = new HashMap<>();
            response.put("top10", top10Abitazioni);
            response.put("statistiche dell'utente", posizioneEPunteggio);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

