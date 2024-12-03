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


}



