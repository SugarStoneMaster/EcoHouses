package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.services.UtenteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/registrazioneUtente")
    public ResponseEntity<Utente> registrazioneUtente(@Valid @RequestBody Utente utente) {
        try {
            Utente nuovoUtente = utenteService.registrazioneUtente(utente);
            return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
