package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.DispositivoIoT;
import it.ecohouses.www.backend.services.IoTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/IoT")
public class IoTController {

    private final IoTService iotService;

    public IoTController(IoTService iotService) {
        this.iotService = iotService;
    }

    @PostMapping(value = "/registraDispositivoIoT")
    public ResponseEntity<?> registraDispositivoIoT(@RequestBody  DispositivoIoT dispositivoIoT) {
        try {
            DispositivoIoT nuovoDispositivo = iotService.registraDispositivoIoT(dispositivoIoT);
            return new ResponseEntity<>(nuovoDispositivo, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Errore durante la registrazione del dispositivo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
