package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.services.AbitazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/abitazioni")
public class AbitazioneController {

    @Autowired
    private AbitazioneService abitazioneService;

    @PostMapping("/registrazioneAbitazione")
    public ResponseEntity<Abitazione> registraAbitazione(@Valid @RequestBody Abitazione abitazione) {
        try {
            Abitazione nuovaAbitazione = abitazioneService.registraAbitazione(abitazione);
            return new ResponseEntity<>(nuovaAbitazione, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}