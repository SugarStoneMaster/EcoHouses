package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.services.AbitazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/abitazioni")
public class AbitazioneController {

    @Autowired
    private AbitazioneService abitazioneService;


}



