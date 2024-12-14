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

    @Test
    void testRegistrazioneDispositivo_ValidQRCode() {
        // Simula un Gestore autenticato e un codice QR valido
        when(iotService.registraDispositivoIoT(dispositivo)).thenReturn(dispositivo);

        ResponseEntity<?> response = iotController.registraDispositivoIoT(dispositivo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dispositivo, response.getBody());
    }

    @Test
    void testRegistrazioneDispositivo_QRGiàRegistrato() {
        // Simula un codice QR già registrato
        when(iotService.registraDispositivoIoT(dispositivo)).thenThrow(new IllegalArgumentException("Dispositivo già registrato."));

        ResponseEntity<?> response = iotController.registraDispositivoIoT(dispositivo);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Errore durante la registrazione del dispositivo.", response.getBody());
    }

    @Test
    void testRegistrazioneDispositivo_QRNonLeggibile() {
        // Simula un codice QR non leggibile
        when(iotService.registraDispositivoIoT(dispositivo)).thenThrow(new IllegalArgumentException("Codice QR non leggibile."));

        ResponseEntity<?> response = iotController.registraDispositivoIoT(dispositivo);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Codice QR non leggibile.", response.getBody());
    }

    @Test
    void testRegistrazioneDispositivo_FormatoErrato() {
        // Simula un codice QR con formato errato
        when(iotService.registraDispositivoIoT(dispositivo)).thenThrow(new IllegalArgumentException("Formato codice QR errato."));

        ResponseEntity<?> response = iotController.registraDispositivoIoT(dispositivo);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Formato codice QR errato.", response.getBody());
    }

    @Test
    void testRegistrazioneDispositivo_EmailErrata(){
        // Simula un errore di email
        when(iotService.registraDispositivoIoT(smartmeter)).thenThrow(new IllegalArgumentException("Email errata"));

        ResponseEntity<?> response = iotController.registraDispositivoIoT(smartmeter);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email errata", response.getBody());
    }

    @Test
    void testRegistrazioneDispositivo_PasswordErrata(){
        // Simula un errore di password
        when(iotService.registraDispositivoIoT(smartmeter)).thenThrow(new IllegalArgumentException("Password errata"));

        ResponseEntity<?> response = iotController.registraDispositivoIoT(smartmeter);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Password errata", response.getBody());
    }
}
