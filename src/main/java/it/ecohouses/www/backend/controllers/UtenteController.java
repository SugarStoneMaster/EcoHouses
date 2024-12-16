package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.*;
import it.ecohouses.www.backend.services.AbitazioneService;
import it.ecohouses.www.backend.services.UtenteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final UtenteService utenteService;
    private final AbitazioneService abitazioneService;
    private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);

    public UtenteController(UtenteService utenteService, AbitazioneService abitazioneService) {
        this.utenteService = utenteService;
        this.abitazioneService = abitazioneService;
    }

    @PostMapping(value = "/registrazioneUtente")
    public ResponseEntity<Map<String, Object>> registrazioneUtente(@RequestBody @Valid Utente utente) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (utente.isGestore() && utente.getAbitazione() == null) {
                response.put("error", "Il gestore deve inserire un'abitazione.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Gestisce l'associazione abitazione se l'utente è un gestore
            if (utente.isGestore()) {
                Abitazione nuovaAbitazione = abitazioneService.registraAbitazione(utente.getAbitazione());
                utente.setAbitazione(nuovaAbitazione);
            }

            // Registra l'utente (gestore o utente normale)
            Utente nuovoUtente = utente.isGestore()
                    ? utenteService.registrazioneGestore(utente)
                    : utenteService.registrazioneUtente(utente);

            if (nuovoUtente == null) {
                response.put("error", "Errore durante la registrazione dell'utente.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            response.put("user", nuovoUtente);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/autenticazioneUtente")
    public ResponseEntity<Map<String, Object>> autenticazioneUtente(@RequestBody Map<String, String> loginData) {
        Map<String, Object> response = new HashMap<>();
        try {
            String identificatore = loginData.get("identificatore");
            String password = loginData.get("password");

            Utente utenteAutenticato = utenteService.autenticazioneUtente(identificatore, password);
            response.put("nickname", utenteAutenticato.getNickname());
            response.put("email", utenteAutenticato.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            logger.error("Authentication failed: {}", e.getMessage());
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
