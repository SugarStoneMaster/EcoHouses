package it.ecohouses.www.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.ecohouses.www.backend.controllers.DashboardController;
import it.ecohouses.www.backend.model.ConsumoEnergetico;
import it.ecohouses.www.backend.services.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class DashboardControllerTest {

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    private LocalDateTime getStartDate() {
        return LocalDateTime.of(2024, 12, 1, 0, 0, 0, 0);
    }

    private LocalDateTime getEndDate() {
        return LocalDateTime.of(2024, 12, 31, 23, 59, 59, 999999);
    }

    private Map<String, Object> createRequestBody(Long abitazioneId, String nickname, LocalDateTime inizio, LocalDateTime fine) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("abitazioneId", abitazioneId);
        requestBody.put("nickname", nickname);
        requestBody.put("inizio", inizio);
        requestBody.put("fine", fine);
        return requestBody;
    }

    //TC_D1_1: Dati completi per il consumo giornaliero e la media locale disponibile
    @Test
    public void testGetConsumiAndMediaConsumi_CompleteData() {
        Long abitazioneId = 1L;
        String nickname = "utente1";
        LocalDateTime inizio = getStartDate();
        LocalDateTime fine = getEndDate();

        // Crea la mappa per i parametri
        Map<String, Object> requestBody = createRequestBody(abitazioneId, nickname, inizio, fine);

        // Mock dei dati di consumo e della media
        List<ConsumoEnergetico> consumiMock = Arrays.asList(new ConsumoEnergetico(100.0, LocalDateTime.now()));
        when(dashboardService.visualizzaConsumi(eq(abitazioneId), eq(inizio), eq(fine))).thenReturn(consumiMock);
        when(dashboardService.getMediaConsumi(eq(nickname), eq(inizio), eq(fine))).thenReturn(80.0);

        // Test per getConsumi
        List<ConsumoEnergetico> consumiResult = dashboardController.getConsumi(requestBody);
        assertNotNull(consumiResult);
        assertEquals(1, consumiResult.size());
        assertEquals(100.0, consumiResult.get(0).getValoreConsumo());

        // Test per getMediaConsumi
        Double mediaConsumiResult = dashboardController.getMediaConsumi(requestBody);
        assertNotNull(mediaConsumiResult);
        assertEquals(80.0, mediaConsumiResult);
    }

    // TC_D1_2: Visualizzazione completa dei dati settimanali dell’utente e della media locale
    @Test
    public void testGetConsumiAndMediaConsumi_WeeklyData_Full() {
        Long abitazioneId = 1L;
        String nickname = "utente1";
        LocalDateTime inizio = getStartDate();
        LocalDateTime fine = getEndDate();

        // Crea la mappa per i parametri
        Map<String, Object> requestBody = createRequestBody(abitazioneId, nickname, inizio, fine);

        // Mock dei dati di consumo e della media
        List<ConsumoEnergetico> consumiMock = Arrays.asList(new ConsumoEnergetico(100.0, LocalDateTime.now()));
        when(dashboardService.visualizzaConsumi(eq(abitazioneId), eq(inizio), eq(fine))).thenReturn(consumiMock);
        when(dashboardService.getMediaConsumi(eq(nickname), eq(inizio), eq(fine))).thenReturn(80.0);

        // Test per getConsumi
        List<ConsumoEnergetico> consumiResult = dashboardController.getConsumi(requestBody);
        assertNotNull(consumiResult);
        assertEquals(1, consumiResult.size());
        assertEquals(100.0, consumiResult.get(0).getValoreConsumo());

        // Test per getMediaConsumi
        Double mediaConsumiResult = dashboardController.getMediaConsumi(requestBody);
        assertNotNull(mediaConsumiResult);
        assertEquals(80.0, mediaConsumiResult);
    }

    // TC_D1_3: Visualizzazione completa dei dati mensili dell’utente e della media locale
    @Test
    public void testGetConsumiAndMediaConsumi_MonthlyData_Full() {
        Long abitazioneId = 1L;
        String nickname = "utente1";
        LocalDateTime inizio = getStartDate();
        LocalDateTime fine = getEndDate();

        // Crea la mappa per i parametri
        Map<String, Object> requestBody = createRequestBody(abitazioneId, nickname, inizio, fine);

        // Mock dei dati di consumo e della media
        List<ConsumoEnergetico> consumiMock = Arrays.asList(new ConsumoEnergetico(100.0, LocalDateTime.now()));
        when(dashboardService.visualizzaConsumi(eq(abitazioneId), eq(inizio), eq(fine))).thenReturn(consumiMock);
        when(dashboardService.getMediaConsumi(eq(nickname), eq(inizio), eq(fine))).thenReturn(80.0);

        // Test per getConsumi
        List<ConsumoEnergetico> consumiResult = dashboardController.getConsumi(requestBody);
        assertNotNull(consumiResult);
        assertEquals(1, consumiResult.size());
        assertEquals(100.0, consumiResult.get(0).getValoreConsumo());

        // Test per getMediaConsumi
        Double mediaConsumiResult = dashboardController.getMediaConsumi(requestBody);
        assertNotNull(mediaConsumiResult);
        assertEquals(80.0, mediaConsumiResult);
    }

    // TC_D1_4: Dati parziali per il consumo giornaliero e la media locale disponibile
    @Test
    public void testGetConsumiAndMediaConsumi_PartialData() {
        Long abitazioneId = 1L;
        String nickname = "utente1";
        LocalDateTime inizio = getStartDate();
        LocalDateTime fine = getEndDate();

        // Crea la mappa per i parametri
        Map<String, Object> requestBody = createRequestBody(abitazioneId, nickname, inizio, fine);

        // Mock dei dati di consumo e della media
        List<ConsumoEnergetico> consumiMockParziali = Arrays.asList(new ConsumoEnergetico(90.0, LocalDateTime.now()));
        when(dashboardService.visualizzaConsumi(eq(abitazioneId), eq(inizio), eq(fine))).thenReturn(consumiMockParziali);
        when(dashboardService.getMediaConsumi(eq(nickname), eq(inizio), eq(fine))).thenReturn(70.0);

        // Test per getConsumi
        List<ConsumoEnergetico> consumiResult = dashboardController.getConsumi(requestBody);
        assertNotNull(consumiResult);
        assertEquals(1, consumiResult.size());
        assertEquals(90.0, consumiResult.get(0).getValoreConsumo());

        // Test per getMediaConsumi
        Double mediaConsumiResult = dashboardController.getMediaConsumi(requestBody);
        assertNotNull(mediaConsumiResult);
        assertEquals(70.0, mediaConsumiResult);
    }

    // TC_D1_5: Visualizzazione parziale dei dati settimanali dell’utente con nota sull’incompletezza
    @Test
    public void testGetConsumiAndMediaConsumi_WeeklyData_Partial() {
        Long abitazioneId = 1L;
        String nickname = "utente1";
        LocalDateTime inizio = getStartDate();
        LocalDateTime fine = getEndDate();

        // Crea la mappa per i parametri
        Map<String, Object> requestBody = createRequestBody(abitazioneId, nickname, inizio, fine);

        // Mock dei dati di consumo parziali
        List<ConsumoEnergetico> consumiMockParziali = Arrays.asList(new ConsumoEnergetico(90.0, LocalDateTime.now()));
        when(dashboardService.visualizzaConsumi(eq(abitazioneId), eq(inizio), eq(fine))).thenReturn(consumiMockParziali);
        when(dashboardService.getMediaConsumi(eq(nickname), eq(inizio), eq(fine))).thenReturn(70.0);

        // Test per getConsumi (con nota sull'incompletezza)
        List<ConsumoEnergetico> consumiResult = dashboardController.getConsumi(requestBody);
        assertNotNull(consumiResult);
        assertEquals(1, consumiResult.size());
        assertEquals(90.0, consumiResult.get(0).getValoreConsumo());

        // Test per getMediaConsumi (con nota sull'incompletezza)
        Double mediaConsumiResult = dashboardController.getMediaConsumi(requestBody);
        assertNotNull(mediaConsumiResult);
        assertEquals(70.0, mediaConsumiResult);
    }

    // TC_D1_6: Visualizzazione parziale dei dati mensili dell’utente con nota sull’incompletezza
    @Test
    public void testGetConsumiAndMediaConsumi_MonthlyData_Partial() {
        Long abitazioneId = 1L;
        String nickname = "utente1";
        LocalDateTime inizio = getStartDate();
        LocalDateTime fine = getEndDate();

        // Crea la mappa per i parametri
        Map<String, Object> requestBody = createRequestBody(abitazioneId, nickname, inizio, fine);

        // Mock dei dati di consumo parziali
        List<ConsumoEnergetico> consumiMockParziali = Arrays.asList(new ConsumoEnergetico(90.0, LocalDateTime.now()));
        when(dashboardService.visualizzaConsumi(eq(abitazioneId), eq(inizio), eq(fine))).thenReturn(consumiMockParziali);
        when(dashboardService.getMediaConsumi(eq(nickname), eq(inizio), eq(fine))).thenReturn(70.0);

        // Test per getConsumi (con nota sull'incompletezza)
        List<ConsumoEnergetico> consumiResult = dashboardController.getConsumi(requestBody);
        assertNotNull(consumiResult);
        assertEquals(1, consumiResult.size());
        assertEquals(90.0, consumiResult.get(0).getValoreConsumo());

        // Test per getMediaConsumi (con nota sull'incompletezza)
        Double mediaConsumiResult = dashboardController.getMediaConsumi(requestBody);
        assertNotNull(mediaConsumiResult);
        assertEquals(70.0, mediaConsumiResult);
    }

    // TC_D1_7: Nessun dato di consumo disponibile
    @Test
    public void testGetConsumi_NoData() {
        Long abitazioneId = 1L;
        String nickname = "utente1";
        LocalDateTime inizio = getStartDate();
        LocalDateTime fine = getEndDate();

        // Crea la mappa per i parametri
        Map<String, Object> requestBody = createRequestBody(abitazioneId, nickname, inizio, fine);

        // Mock dei dati di consumo
        when(dashboardService.visualizzaConsumi(eq(abitazioneId), eq(inizio), eq(fine))).thenReturn(null);

        // Test per getConsumi
        List<ConsumoEnergetico> consumiResult = dashboardController.getConsumi(requestBody);
        assertNull(consumiResult);
    }

    // TC_D1_8: Nessun dato della media locale disponibile
    @Test
    public void testGetMediaConsumi_NoMediaLocale() {
        Long abitazioneId = 1L;
        String nickname = "utente1";
        LocalDateTime inizio = getStartDate();
        LocalDateTime fine = getEndDate();

        // Crea la mappa per i parametri
        Map<String, Object> requestBody = createRequestBody(abitazioneId, nickname, inizio, fine);

        // Mock della media
        when(dashboardService.getMediaConsumi(eq(nickname), eq(inizio), eq(fine))).thenReturn(null);

        // Test per getMediaConsumi
        Double mediaConsumiResult = dashboardController.getMediaConsumi(requestBody);
        assertNull(mediaConsumiResult);
    }

    // TC_D1_9: Dati parziali e nessun dato della media locale disponibile
    @Test
    public void testGetConsumiAndMediaConsumi_PartialData_NoMediaLocale() {
        Long abitazioneId = 1L;
        String nickname = "utente1";
        LocalDateTime inizio = getStartDate();
        LocalDateTime fine = getEndDate();

        // Crea la mappa per i parametri
        Map<String, Object> requestBody = createRequestBody(abitazioneId, nickname, inizio, fine);

        // Mock dei dati di consumo
        List<ConsumoEnergetico> consumiMockParziali = Arrays.asList(new ConsumoEnergetico(90.0, LocalDateTime.now()));

        when(dashboardService.visualizzaConsumi(eq(abitazioneId), eq(inizio), eq(fine))).thenReturn(consumiMockParziali);
        when(dashboardService.getMediaConsumi(eq(nickname), eq(inizio), eq(fine))).thenReturn(null);

        // Test per getConsumi
        List<ConsumoEnergetico> consumiResult = dashboardController.getConsumi(requestBody);
        assertNotNull(consumiResult);
        assertEquals(1, consumiResult.size());
        assertEquals(90.0, consumiResult.get(0).getValoreConsumo());

        // Test per getMediaConsumi
        Double mediaConsumiResult = dashboardController.getMediaConsumi(requestBody);
        assertNull(mediaConsumiResult);
    }
}
