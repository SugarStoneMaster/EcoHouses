package it.ecohouses.www.backend;

import it.ecohouses.www.backend.controllers.GamificationController;
import it.ecohouses.www.backend.model.Abitazione;
import it.ecohouses.www.backend.model.Classifica;
import it.ecohouses.www.backend.model.ClassificaAbitazione;
import it.ecohouses.www.backend.model.Sfida;
import it.ecohouses.www.backend.services.GamificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GamificationControllerTest {

    @Mock
    private GamificationService gamificationService;
    private GamificationController gamificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gamificationController = new GamificationController(gamificationService);
    }

    @Test
    void testGetClassificaLocaleValid() {
        String nickname = "utente1";
        Classifica mockClassifica = new Classifica(true, LocalDate.now(), LocalDate.now(), "ComuneTest");
        List<ClassificaAbitazione> mockTop100 = Arrays.asList(
                new ClassificaAbitazione(mockClassifica, new Abitazione("Casa1", null, 100, "A", 4, "Comune1"), 1, 500)
        );
        when(gamificationService.getClassificaLocale(nickname)).thenReturn(mockClassifica);
        when(gamificationService.getTop100Abitazioni(mockClassifica.getIdClassifica())).thenReturn(mockTop100);
        when(gamificationService.getPosizioneAndPunteggio(nickname, mockClassifica.getIdClassifica())).thenReturn(new Object[]{1, 500});

        ResponseEntity<?> response = gamificationController.getClassificaLocale(nickname);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertTrue(responseBody.containsKey("top100"));
        assertTrue(responseBody.containsKey("statistiche dell'utente"));
    }

    @Test
    void testGetClassificaLocaleNoTop100() {
        String nickname = "utente2";
        Classifica mockClassifica = new Classifica(true, LocalDate.now(), LocalDate.now(), "ComuneTest");
        List<ClassificaAbitazione> mockTop100 = Arrays.asList(
                new ClassificaAbitazione(mockClassifica, new Abitazione("Casa2", null, 80, "B", 2, "Comune2"), 101, 300)
        );
        when(gamificationService.getClassificaLocale(nickname)).thenReturn(mockClassifica);
        when(gamificationService.getTop100Abitazioni(mockClassifica.getIdClassifica())).thenReturn(mockTop100);
        when(gamificationService.getPosizioneAndPunteggio(nickname, mockClassifica.getIdClassifica())).thenReturn(new Object[]{101, 300});

        ResponseEntity<?> response = gamificationController.getClassificaLocale(nickname);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertTrue(responseBody.containsKey("top100"));
        assertTrue(responseBody.containsKey("statistiche dell'utente"));
    }

    @Test
    void testGetClassificaLocaleNoData() {
        String nickname = "utente3";

        // Simuliamo un errore nel recupero della classifica locale
        when(gamificationService.getClassificaLocale(nickname)).thenThrow(new RuntimeException("Errore nel recupero della classifica locale"));

        ResponseEntity<?> response = gamificationController.getClassificaLocale(nickname);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("La classifica non è al momento disponibile. Riprova più tardi.", response.getBody());
    }


    @Test
    void testGetClassificaLocaleNoOtherUsers() {
        String nickname = "utente4";
        Classifica mockClassifica = new Classifica(true, LocalDate.now(), LocalDate.now(), "ComuneTest");
        when(gamificationService.getClassificaLocale(nickname)).thenReturn(mockClassifica);
        when(gamificationService.getTop100Abitazioni(mockClassifica.getIdClassifica())).thenReturn(new ArrayList<>());
        when(gamificationService.getPosizioneAndPunteggio(nickname, mockClassifica.getIdClassifica())).thenReturn(null);

        ResponseEntity<?> response = gamificationController.getClassificaLocale(nickname);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Non ci sono altri utenti nella classifica."));
    }

    @Test
    void testCreaSfidaDiGruppoValidDT1DL1() {
        Map<String, Object> richiesta = Map.of(
                "durata", "SETTIMANALE",
                "difficolta", "FACILE",
                "nickname", "utente1",
                "partecipanti", 3
        );
        Sfida mockSfida = new Sfida();
        mockSfida.setIdSfida(1L);
        mockSfida.setDurata("SETTIMANALE");
        mockSfida.setDifficolta("FACILE");
        when(gamificationService.creaSfidaDiGruppo("FACILE", "SETTIMANALE", "utente1", 3)).thenReturn(mockSfida);

        ResponseEntity<Sfida> response = gamificationController.creaSfidaDiGruppo(richiesta);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Sfida sfida = response.getBody();
        assertEquals("SETTIMANALE", sfida.getDurata());
        assertEquals("FACILE", sfida.getDifficolta());
    }

    @Test
    void testCreaSfidaDiGruppoValidDT1DL2() {
        Map<String, Object> richiesta = Map.of(
                "durata", "SETTIMANALE",
                "difficolta", "MEDIA",
                "nickname", "utente2",
                "partecipanti", 3
        );
        Sfida mockSfida = new Sfida();
        mockSfida.setIdSfida(2L);
        mockSfida.setDurata("SETTIMANALE");
        mockSfida.setDifficolta("MEDIA");
        when(gamificationService.creaSfidaDiGruppo("MEDIA", "SETTIMANALE", "utente2", 3)).thenReturn(mockSfida);

        ResponseEntity<Sfida> response = gamificationController.creaSfidaDiGruppo(richiesta);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Sfida sfida = response.getBody();
        assertEquals("SETTIMANALE", sfida.getDurata());
        assertEquals("MEDIA", sfida.getDifficolta());
    }

    @Test
    void testCreaSfidaDiGruppoValidDT1DL3() {
        Map<String, Object> richiesta = Map.of(
                "durata", "SETTIMANALE",
                "difficolta", "DIFFICILE",
                "nickname", "utente3",
                "partecipanti", 3
        );
        Sfida mockSfida = new Sfida();
        mockSfida.setIdSfida(3L);
        mockSfida.setDurata("SETTIMANALE");
        mockSfida.setDifficolta("DIFFICILE");
        when(gamificationService.creaSfidaDiGruppo("DIFFICILE", "SETTIMANALE", "utente3", 3)).thenReturn(mockSfida);

        ResponseEntity<Sfida> response = gamificationController.creaSfidaDiGruppo(richiesta);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Sfida sfida = response.getBody();
        assertEquals("SETTIMANALE", sfida.getDurata());
        assertEquals("DIFFICILE", sfida.getDifficolta());
    }

    @Test
    void testCreaSfidaDiGruppoValidDT2DL1() {
        Map<String, Object> richiesta = Map.of(
                "durata", "MENSILE",
                "difficolta", "FACILE",
                "nickname", "utente4",
                "partecipanti", 3
        );
        Sfida mockSfida = new Sfida();
        mockSfida.setIdSfida(4L);
        mockSfida.setDurata("MENSILE");
        mockSfida.setDifficolta("FACILE");
        when(gamificationService.creaSfidaDiGruppo("FACILE", "MENSILE", "utente4", 3)).thenReturn(mockSfida);

        ResponseEntity<Sfida> response = gamificationController.creaSfidaDiGruppo(richiesta);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Sfida sfida = response.getBody();
        assertEquals("MENSILE", sfida.getDurata());
        assertEquals("FACILE", sfida.getDifficolta());
    }

    @Test
    void testCreaSfidaDiGruppoValidDT2DL2() {
        Map<String, Object> richiesta = Map.of(
                "durata", "MENSILE",
                "difficolta", "MEDIA",
                "nickname", "utente5",
                "partecipanti", 3
        );
        Sfida mockSfida = new Sfida();
        mockSfida.setIdSfida(5L);
        mockSfida.setDurata("MENSILE");
        mockSfida.setDifficolta("MEDIA");
        when(gamificationService.creaSfidaDiGruppo("MEDIA", "MENSILE", "utente5", 3)).thenReturn(mockSfida);

        ResponseEntity<Sfida> response = gamificationController.creaSfidaDiGruppo(richiesta);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Sfida sfida = response.getBody();
        assertEquals("MENSILE", sfida.getDurata());
        assertEquals("MEDIA", sfida.getDifficolta());
    }

    @Test
    void testCreaSfidaDiGruppoValidDT2DL3() {
        Map<String, Object> richiesta = Map.of(
                "durata", "MENSILE",
                "difficolta", "DIFFICILE",
                "nickname", "utente6",
                "partecipanti", 3
        );
        Sfida mockSfida = new Sfida();
        mockSfida.setIdSfida(6L);
        mockSfida.setDurata("MENSILE");
        mockSfida.setDifficolta("DIFFICILE");
        when(gamificationService.creaSfidaDiGruppo("DIFFICILE", "MENSILE", "utente6", 3)).thenReturn(mockSfida);

        ResponseEntity<Sfida> response = gamificationController.creaSfidaDiGruppo(richiesta);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        Sfida sfida = response.getBody();
        assertEquals("MENSILE", sfida.getDurata());
        assertEquals("DIFFICILE", sfida.getDifficolta());
    }

    @Test
    void testCreaSfidaDiGruppoErroreLivelloDifficoltàNonSelezionato() {
        Map<String, Object> richiesta = Map.of(
                "durata", "SETTIMANALE",
                "difficolta", "ERRORE",
                "nickname", "utente7",
                "partecipanti", 3
        );

        ResponseEntity<Sfida> response = gamificationController.creaSfidaDiGruppo(richiesta);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testCreaSfidaDiGruppoErroreTipoDurataNonSelezionato() {
        Map<String, Object> richiesta = Map.of(
                "durata", "ERRORE",
                "difficolta", "FACILE",
                "nickname", "utente8",
                "partecipanti", 3
        );

        ResponseEntity<Sfida> response = gamificationController.creaSfidaDiGruppo(richiesta);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testCreaSfidaDiGruppoErroreNumeroPartecipntiNonValido() {
        Map<String, Object> richiesta = Map.of(
                "durata", "SETTIMANALE",
                "difficolta", "FACILE",
                "nickname", "utente9",
                "partecipanti", 1
        );

        ResponseEntity<Sfida> response = gamificationController.creaSfidaDiGruppo(richiesta);

        assertEquals(400, response.getStatusCodeValue());
    }

}

