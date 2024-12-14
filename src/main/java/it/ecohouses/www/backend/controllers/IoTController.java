package it.ecohouses.www.backend.controllers;

import it.ecohouses.www.backend.model.DispositivoIoT;
import it.ecohouses.www.backend.model.SmartMeter;
import it.ecohouses.www.backend.services.IoTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/IoT")
public class IoTController {

    private final IoTService iotService;

    public IoTController(IoTService iotService) {
        this.iotService = iotService;
    }

    @PostMapping(value = "/aggiungiDispositivoIoT")
    public ResponseEntity<?> aggiungiDispositivoIoT(@RequestBody  DispositivoIoT dispositivoIoT) {
        try {
            DispositivoIoT nuovoDispositivo = iotService.aggiungiDispositivoIoT(dispositivoIoT);
            return new ResponseEntity<>(nuovoDispositivo, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Errore durante la registrazione del dispositivo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/recupera-dispositivi")
    public ResponseEntity<?> recuperaDispositivoIoT(@RequestBody  Map<String, Object> requestBody){

        String nickname = requestBody.get("nickname").toString();
        System.out.println("Nickname richiesto: " + nickname);
        List<DispositivoIoT> dispositivi = iotService.retriveDispositivi(nickname);
        System.out.println(dispositivi);
        return new ResponseEntity<>(dispositivi, HttpStatus.OK);
    }

    @PostMapping(value = "/loginSmartMeter")
    public ResponseEntity<?> login(@RequestBody SmartMeter smartMeter) {
        try {
            String email = smartMeter.getEmail();
            String password = smartMeter.getPassword();

            DispositivoIoT sMeter = new DispositivoIoT();
            sMeter.setNumeroSerie(smartMeter.getNumeroSerie());
            sMeter.setNomeDispositivo(smartMeter.getNomeDispositivo());
            sMeter.setAbitazione(smartMeter.getAbitazione());
            sMeter.setTipoDispositivo(smartMeter.getTipoDispositivo());

            // Validazione finta
            if (email.equals(email) && password.equals(password)) {
                DispositivoIoT nuovoDispositivo = iotService.aggiungiDispositivoIoT(sMeter);
                return new ResponseEntity<>(nuovoDispositivo, HttpStatus.CREATED);

            } else {
                // Credenziali errate
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "errore", "Credenziali non valide."
                ));
            }
        } catch (Exception e) {
            // Gestione errore generico
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "errore", "Errore durante il login."
            ));
        }
    }
}
