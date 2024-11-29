package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.Utente;
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
    public ResponseEntity<Abitazione> registraAbitazione(
            @RequestParam("nickname") String nickname,
            @Valid @RequestBody Abitazione abitazione) {
        try {
            // Passa nickname e abitazione al servizio
            Abitazione nuovaAbitazione = abitazioneService.registraAbitazione(abitazione, nickname);
            return new ResponseEntity<>(nuovaAbitazione, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-by-utente")
    public ResponseEntity<Abitazione> findByUtente(@RequestBody Utente utente) {
        return abitazioneService.getAbitazioneByUtente(utente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}



