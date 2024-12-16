package it.ecohouses.www.backend;

import it.ecohouses.www.backend.controllers.UtenteController;
import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.AbitazioneRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import it.ecohouses.www.backend.services.AbitazioneService;
import it.ecohouses.www.backend.services.UtenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtenteControllerTest {

    @Mock
    private UtenteService utenteService;  // Mock del service Utente

    @Mock
    private AbitazioneService abitazioneService;  // Mock del service Abitazione

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private AbitazioneRepository abitazioneRepository;

    @InjectMocks
    private UtenteController utenteController;  // Controller con i services mockati

    private Utente validUtente;
    private Abitazione validAbitazione;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock

        // Creazione di un utente valido
        validUtente = new Utente("validNickname", "validemail@example.com", "ValidPassword123!", null, true);

        // Creazione di un'abitazione valida
        validAbitazione = new Abitazione("Casa Bella", "house.jpg", 120.0f, "A1", 4, "ComuneX");
        validUtente.setAbitazione(validAbitazione);
    }

    @Test //TC_RG_1
    void testGestoreValido(){

        ResponseEntity<Map<String, Object>>  response = utenteController.registrazioneUtente(validUtente);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test //TC_RG_2
    void testEmailNonValida() {
        validUtente.setEmail("invalidEmail");

        when(utenteService.registrazioneGestore(validUtente)).thenThrow(new IllegalArgumentException("Email non valida."));
        ResponseEntity<Map<String, Object>>  response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test //TC_RG_2
    void testEmailGiaInUso() {
        validUtente.setEmail("invalidEmail");

        when(utenteService.registrazioneGestore(validUtente)).thenThrow(new IllegalArgumentException("Email già utilizzata."));
        ResponseEntity<Map<String, Object>>  response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }


    @Test //TC_RG_3
    void testPasswordNonValida() {
        validUtente.setPassword("short");

        lenient().when(utenteService.registrazioneUtente(validUtente)).thenThrow(new IllegalArgumentException("Password non valida."));

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test //TC_RG_5
    void testNicknameNonValido() {
        validUtente.setNickname("short");

        lenient().when(utenteService.registrazioneUtente(validUtente)).thenThrow(new IllegalArgumentException("Nickname non valido."));

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test //TC_RG_5
    void testRegistrazioneNicknameGiaInUso() {
        validUtente.setNickname("existingNickname");
        // Simuliamo che il nickname sia già esistente nel database nel servizio
        lenient().when(utenteService.registrazioneGestore(validUtente)).thenThrow(new IllegalArgumentException("Nickname già utilizzato."));
        // Eseguiamo la registrazione
        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);
        // Verifica che la risposta sia un errore con status 400
        assertEquals(500, response.getStatusCodeValue());
        // Verifica che il messaggio di errore sia corretto
    }

    @Test //TC_RG_6
    void testRuoloNonValido() {
        validUtente.setGestore(false);  // Ruolo invalido

        lenient().when(utenteService.registrazioneUtente(validUtente)).thenThrow(new IllegalArgumentException("Ruolo non valido."));

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test //TC_RG_7
    void testNomeCasaNonValido() {
        validAbitazione.setNomeCasa("CasaSuperLongaPiùDiVenticarri");

        lenient().when(abitazioneService.registraAbitazione(validAbitazione)).thenThrow(new IllegalArgumentException("NomeCasa non valido."));

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test //TC_RG_9
    void testMetraturaNonValida() {
        validAbitazione.setMetratura(5);  // Metratura inferiore a 10

        lenient().when(abitazioneService.registraAbitazione(validAbitazione)).thenThrow(new IllegalArgumentException("Metratura non valida."));

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test //TC_RG_10
    void testNumeroPersoneNonValido() {
        validAbitazione.setNumeroPersone(0);  // Numero di persone inferiore a 1

        lenient().when(abitazioneService.registraAbitazione(validAbitazione)).thenThrow(new IllegalArgumentException("Numero di persone non valido."));

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test //TC_RG_11
    void testClasseEnergeticaNonValida() {
        validAbitazione.setClasseEnergetica("H");

        lenient().when(abitazioneService.registraAbitazione(validAbitazione)).thenThrow(new IllegalArgumentException("Classe energetica non valida."));

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test //TC_RG_12
    void testImmagineNonValida() {
        validAbitazione.setImmagine("invalid.bmp");

        lenient().when(abitazioneService.registraAbitazione(validAbitazione)).thenThrow(new IllegalArgumentException("Immagine non valida."));

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testComuneNonValido() {
        validAbitazione.setComune("");

        lenient().when(abitazioneService.registraAbitazione(validAbitazione)).thenThrow(new IllegalArgumentException("Immagine non valida."));

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(500, response.getStatusCodeValue());
    }

}

