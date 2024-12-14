package it.ecohouses.www.backend;

import it.ecohouses.www.backend.controllers.UtenteController;
import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import it.ecohouses.www.backend.services.AbitazioneService;
import it.ecohouses.www.backend.services.UtenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtenteControllerTest {

    @Mock
    private UtenteService utenteService;
    @Mock
    private AbitazioneService abitazioneService;
    private UtenteController utenteController;

    private Utente validUtente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utenteController = new UtenteController(utenteService, abitazioneService);
        validUtente = new Utente("validNickname", "validemail@example.com", "ValidPassword123!", null, true);
        validUtente.setAbitazione(new Abitazione("Casa", null, 50.0f, "A1", 4, "Roma"));
    }

    @Test
    void testRegistrazioneEmailNonValida() {
        validUtente.setEmail("invalidemail.com");

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Dati invalidi.", response.getBody());
    }

    @Test
    void testRegistrazionePasswordNonValida() {
        validUtente.setPassword("short");

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Dati invalidi.", response.getBody());
    }

    @Test
    void testRegistrazioneConfermaPasswordNonValida() {
        validUtente.setPassword("ValidPassword123!");

        // Simuliamo che al momento non sia possibile effettuare il test
        System.out.println("Test fallito: al momento non è possibile effettuare il test per la conferma password non valida.");
        fail("Test fallito: al momento non è possibile effettuare il test per la conferma password non valida.");
    }

    @Test
    void testRegistrazioneNicknameGiaInUso() {
        validUtente.setNickname("existingNickname");

        // Simuliamo che il nickname sia già esistente nel database nel servizio
        when(utenteService.registrazioneGestore(validUtente)).thenThrow(new IllegalArgumentException("Nickname già utilizzato."));

        // Eseguiamo la registrazione
        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        // Verifica che la risposta sia un errore con status 400
        assertEquals(400, response.getStatusCodeValue());

        // Verifica che il messaggio di errore sia corretto
        assertEquals("Dati invalidi.", response.getBody());
    }



    @Test
    void testRegistrazioneRuoloMembroNonValido() {
        validUtente.setGestore(false);

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Ruolo non valido per il gestore.", response.getBody());
    }


    @Test
    void testRegistrazioneNomeCasaNonValido() {
        validUtente.getAbitazione().setNomeCasa("A very long house name that exceeds twenty characters");

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Nome casa non valido.", response.getBody());
    }

    @Test
    void testRegistrazioneMetraturaNonValida() {
        validUtente.getAbitazione().setMetratura(5);

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Metratura non valida.", response.getBody());
    }


    @Test
    void testRegistrazioneClasseEnergeticaNonValida() {
        validUtente.getAbitazione().setClasseEnergetica("Z");

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Classe energetica non valida.", response.getBody());
    }

    @Test
    void testRegistrazioneNumeroPersoneNonValido() {
        validUtente.getAbitazione().setNumeroPersone(0);

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Numero di persone non valido.", response.getBody());
    }


    @Test
    void testRegistrazioneComuneNonValido() {
        validUtente.getAbitazione().setComune("");

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Comune non valido.", response.getBody());
    }


    @Test
    void testRegistrazioneImmagineNonValida() {
        validUtente.getAbitazione().setImmagine("invalidimage.bmp");

        ResponseEntity<?> response = utenteController.registrazioneUtente(validUtente);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Immagine non valida.", response.getBody());
    }


}
