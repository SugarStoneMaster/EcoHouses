package it.ecohouses.www.backend;

import it.ecohouses.www.backend.controllers.IoTController;
import it.ecohouses.www.backend.model.DispositivoIoT;
import it.ecohouses.www.backend.model.SmartMeter;
import it.ecohouses.www.backend.services.IoTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class IoTControllerTest {

    @Mock
    private IoTService iotService;

    @InjectMocks
    private IoTController iotController;

    private DispositivoIoT dispositivo;
    private SmartMeter smartmeter;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dispositivo = new DispositivoIoT();
        dispositivo.setNumeroSerie(123456L);
        dispositivo.setNomeDispositivo("Test Device");
        dispositivo.setTipoDispositivo("SmartMeter");

        smartmeter = new SmartMeter(); // Aggiunto per evitare NullPointerException
        smartmeter.setEmail("Palombagamer@gmail.com");
        smartmeter.setPassword("IsBestEsame");
    }
     //TC_IOT_1
    @Test
    void testRegistrazioneDispositivo_ValidQRCode() {
        when(iotService.aggiungiDispositivoIoT(dispositivo)).thenReturn(dispositivo);

        ResponseEntity<?> response = iotController.aggiungiDispositivoIoT(dispositivo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dispositivo, response.getBody());
    }

    //TC_IOT_2
    @Test
    void testRegistrazioneDispositivo_QRGiàRegistrato() {
        when(iotService.aggiungiDispositivoIoT(dispositivo)).thenThrow(new IllegalArgumentException("Dispositivo già registrato."));

        ResponseEntity<?> response = iotController.aggiungiDispositivoIoT(dispositivo);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Errore durante la registrazione del dispositivo.", response.getBody());
    }

    //TC_SM_5
    @Test
    void testRegistrazioneDispositivo_EmailErrata(){

        when(iotService.aggiungiDispositivoIoT(smartmeter)).thenThrow(new IllegalArgumentException("Email errata"));

        ResponseEntity<?> response = iotController.aggiungiDispositivoIoT(smartmeter);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email errata", response.getBody());
    }

    //TC_SM_6
    @Test
    void testRegistrazioneDispositivo_PasswordErrata(){

        when(iotService.aggiungiDispositivoIoT(smartmeter)).thenThrow(new IllegalArgumentException("Password errata"));

        ResponseEntity<?> response = iotController.aggiungiDispositivoIoT(smartmeter);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Password errata", response.getBody());
    }
}
