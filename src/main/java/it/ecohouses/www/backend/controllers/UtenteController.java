package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.*;
import it.ecohouses.www.backend.services.AbitazioneService;
import it.ecohouses.www.backend.services.UtenteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final UtenteService utenteService;
    private final AbitazioneService abitazioneService;

    public UtenteController(UtenteService utenteService, AbitazioneService abitazioneService) {
        this.utenteService = utenteService;
        this.abitazioneService = abitazioneService;
    }

   /* @PostMapping(value = "/registrazioneUtente", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registrazioneUtente(
            @RequestPart("utente") @Valid Utente utente,
            @RequestPart(value = "abitazione", required = false) Abitazione abitazione) {

        //System.out.println("Utente: " + utente);
        //System.out.println("Abitazione: " + abitazione);

        try {
            if (utente.isGestore()) {
                if (abitazione != null) {
                    Utente nuovoUtente = utenteService.registrazioneUtente(utente);
                    Abitazione nuovaAbitazione = abitazioneService.registraAbitazione(abitazione, utente.getNickname());
                    return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);
                } else {
                   throw new IllegalArgumentException("Il gestore deve inserire un'abitazione.");
                }

            }

            Utente nuovoUtente = utenteService.registrazioneUtente(utente);
            return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Dati invalidi.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante la registrazione.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PostMapping(value = "/registrazioneUtente")
    public ResponseEntity<?> registrazioneUtente(@RequestBody @Valid Utente utente) {
        
        try {
            if (utente.isGestore()) {
                if (utente.getAbitazione() != null ) {
                    // Salva l'abitazione
                    Abitazione abitazione = utente.getAbitazione();
                    Abitazione nuovaAbitazione = abitazioneService.registraAbitazione(abitazione, utente.getNickname());

                    // Associa l'abitazione salvata all'utente
                    utente.setAbitazione(nuovaAbitazione);

                    // Salva l'utente come gestore
                    Utente nuovoUtente = utenteService.registrazioneGestore(utente);

                    return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);
                } else {
                    throw new IllegalArgumentException("Il gestore deve inserire un'abitazione.");
                }

            }

            Utente nuovoUtente = utenteService.registrazioneUtente(utente);
            return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Dati invalidi.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Errore durante la registrazione.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/autenticazioneUtente")
    public ResponseEntity<Map<String, Object>> autenticazioneUtente(@RequestBody Utente utente) {
        try {
            Utente utenteAutenticato = utenteService.autenticazioneUtente(utente.getEmail(), utente.getPassword());
            Map<String, Object> response = new HashMap<>();
            response.put("nickname", utenteAutenticato.getNickname());
            response.put("email", utenteAutenticato.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}