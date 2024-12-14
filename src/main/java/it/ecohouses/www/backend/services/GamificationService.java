package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.*;
import it.ecohouses.www.backend.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GamificationService {

    private final ClassificaRepository classificaRepository;
    private final ClassificaAbitazioneRepository classificaAbitazioneRepository;
    private final UtenteRepository utenteRepository;
    private final SfidaRepository sfidaRepository;

    // Costruttore per l'injection dei repository
    public GamificationService(ClassificaRepository classificaRepository,
                               ClassificaAbitazioneRepository classificaAbitazioneRepository,
                               UtenteRepository utenteRepository, SfidaRepository sfidaRepository) {
        this.classificaRepository = classificaRepository;
        this.classificaAbitazioneRepository = classificaAbitazioneRepository;
        this.utenteRepository = utenteRepository;
        this.sfidaRepository = sfidaRepository;
    }

    /*
     * Recupera la classifica locale più recente per un dato utente.
     */
    public long getClassificaLocale(String nickname) {
        // Recupera il comune dell'utente
        String comune = utenteRepository.findComuneByNickname(nickname);

        // Verifica se esiste una classifica locale per il comune
        if (!classificaRepository.existsByComune(comune)) {
            Classifica classifica = new Classifica();
            classifica.setTipoClassifica(true); // Locale
            classifica.setDataCreazione(LocalDate.now());
            classifica.setDataAggiornamento(null);
            classifica.setComune(comune);

            // Salva la nuova classifica locale
            classificaRepository.save(classifica);

            // Recupera l'ID della classifica appena creata
            return classifica.getIdClassifica();
        }

        else{// Recupera la classifica locale più recente per il comune
            return classificaRepository.findLatestLocalIdByComune(comune)
                    .orElseThrow(() -> new RuntimeException("Classifica locale non trovata per il comune: " + comune));
        }

    }

    public long getClassificaGlobale() {
        // Recupera la classifica globale più recente
        return classificaRepository.findLatestGlobalId()
                .orElseThrow(() -> new RuntimeException("Classifica globale non trovata"));
    }

    /**
     * Ottiene le prime 10 abitazioni di una classifica.
     * @return Una lista delle prime 10 abitazioni nella classifica.
     */
    public List<Object[]> getTop100Abitazioni(Long idClassifica) {
        // Usa il repository per recuperare le prime 100 abitazioni della classifica
        return classificaAbitazioneRepository.findTop100ByClassifica(idClassifica);
    }


    /**
     * Ottiene la posizione e il punteggio dell'abitazione dell'utente in una classifica
     * @param nickname Il nickname dell'utente.
     * @param idClassifica L'ID della classifica da cui vogliamo ottenere la posizione.
     * @return La posizione e il punteggio dell'abitazione dell'utente.
     */
    public Object[] getPosizioneAndPunteggio(String nickname, Long idClassifica) {
        // Recupera l'abitazione dell'utente in base al nickname
        Optional<Utente> utenteOpt = utenteRepository.findByNickname(nickname);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            Long idAbitazione = utente.getAbitazione().getIdAbitazione();

            // Recupera la posizione e il punteggio dell'abitazione dell'utente nella classifica
            return classificaAbitazioneRepository.findPosizioneAndPunteggioByClassificaAndAbitazione(idClassifica, idAbitazione)
                    .orElse(null);
        }
        return null;
    }

    /**
     * Calcola il punteggio della sfida in base alla difficoltà e alla durata.
     * @return Il punteggio calcolato.
     */
    public int calcolaPunteggio(String difficolta, String durata) {
        int punteggioBase = switch (difficolta.toUpperCase()) {
            case "FACILE" -> 10;
            case "MEDIA" -> 20;
            case "DIFFICILE" -> 30;
            default -> 0; // Valore di default
        };

        int moltiplicatore = switch (durata.toUpperCase()) {
            case "GIORNALIERA" -> 1;
            case "SETTIMANALE" -> 2;
            case "MENSILE" -> 3;
            default -> 1; // Valore di default
        };

        return punteggioBase * moltiplicatore;
    }

    /**
     * Calcola l'obiettivo della sfida basandosi su difficoltà, durata e consumo dell'abitazione.
     * @return L'obiettivo calcolato.
     */
    public int calcolaObiettivo(String difficolta, String durata, float consumoAttuale) {
        double moltiplicatoreDifficolta = switch (difficolta.toUpperCase()) {
            case "FACILE" -> 1; // Mantenere il consumo attuale
            case "MEDIA" -> 0.9; // Riduzione del 10%
            case "DIFFICILE" -> 0.8; // Riduzione del 20%
            default -> 1.0; // Nessuna modifica
        };

        int moltiplicatoreDurata = switch (durata.toUpperCase()) {
            case "GIORNALIERA" -> 1;
            case "SETTIMANALE" -> 7;
            case "MENSILE" -> 30;
            default -> 1; // Default a giornaliero
        };

        return (int) (consumoAttuale * moltiplicatoreDifficolta * moltiplicatoreDurata);
    }

    /**
     * Genera una lista di sfide predefinite con tutte le combinazioni possibili di difficoltà e durata.
     * @return Una lista di sfide predefinite.
     */
    public List<Sfida> generaSfidePredefinite(String nickname) {
        // Recupera l'abitazione dell'utente in base al nickname
        Utente utente = utenteRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + nickname));
        Abitazione abitazione = utente.getAbitazione();

        // Ottieni il consumo attuale dell'abitazione
        float consumo = abitazione.getConsumoTotale();

        // Definizione delle combinazioni di difficoltà e durata
        List<String> difficolta = List.of("FACILE", "MEDIA", "DIFFICILE");
        List<String> durata = List.of("GIORNALIERA", "SETTIMANALE", "MENSILE");

        // Lista per salvare le sfide predefinite
        List<Sfida> sfidePredefinite = new ArrayList<>();

        // Genera tutte le combinazioni possibili di difficoltà e durata
        for (String diff : difficolta) {
            for (String dur : durata) {
                int punteggio = calcolaPunteggio(diff, dur); // Calcola il punteggio
                float obiettivo = calcolaObiettivo(diff, dur, consumo); // Calcola l'obiettivo

                // Crea una nuova sfida predefinita
                Sfida sfida = new Sfida();
                sfida.setTipoSfida(false); // Individuale
                sfida.setDifficolta(diff);
                sfida.setDurata(dur);
                sfida.setPunteggio(punteggio);
                sfida.setObiettivo(obiettivo);
                sfida.setCompletamento(false);

                // Aggiungi la sfida alla lista
                sfidePredefinite.add(sfida);
            }
        }

        return sfidePredefinite;
    }

    /**
     * Salva la sfida individuale scelta dall'utente.
     * @return La sfida salvata.
     */
    public Sfida salvaSfidaIndividuale(String difficolta, String durata, int punteggio, float obiettivo, String nickname) {
        // Recupera l'abitazione dell'utente
        Utente utente = utenteRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + nickname));
        Abitazione abitazione = utente.getAbitazione();

        //calcola la data di scadenza
        LocalDateTime dataCreazione = LocalDateTime.now();
        LocalDateTime dataScadenza = switch (durata.toUpperCase()) {
            case "GIORNALIERA" -> dataCreazione.plusDays(1);
            case "SETTIMANALE" -> dataCreazione.plusWeeks(1);
            case "MENSILE" -> dataCreazione.plusMonths(1);
            default -> throw new IllegalArgumentException("Durata non valida");
        };

        // Crea e salva la sfida
        Sfida sfida = new Sfida();
        sfida.setTipoSfida(false); // Individuale
        sfida.setDifficolta(difficolta);
        sfida.setDurata(durata);
        sfida.setPunteggio(punteggio);
        sfida.setObiettivo(obiettivo);
        sfida.setDataScadenza(dataScadenza);
        sfida.setCompletamento(false);
        sfida.setAbitazionePartecipante(abitazione);

        return sfidaRepository.save(sfida);
    }

    //metodo per calcolare il punteggio nelle sfide di gruppo in base a difficoltà e durata
    public int calcolaPunteggioGruppo(String difficolta, String durata) {
        int punteggioBase = switch (difficolta.toUpperCase()) {
            case "FACILE" -> 20;
            case "MEDIA" -> 40;
            case "DIFFICILE" -> 60;
            default -> 0; // Valore di default
        };

        int moltiplicatore = switch (durata.toUpperCase()) {
            case "SETTIMANALE" -> 2;
            case "MENSILE" -> 3;
            default -> 1; // Valore di default
        };

        return punteggioBase * moltiplicatore;
    }

    public Sfida creaSfidaDiGruppo(String difficolta, String durata, String nicknameCreatore, List<String> nicknamePartecipanti) {

        //recupera l'abitazione dell'utente creatore
        Utente utenteCreatore = utenteRepository.findByNickname(nicknameCreatore)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + nicknameCreatore));
        Abitazione abitazioneCreatore = utenteCreatore.getAbitazione();

        //recupera le abitazioni dei partecipanti
        List<Abitazione> abitazioniPartecipanti = new ArrayList<>();
        for (String nickname : nicknamePartecipanti) {
            Utente utentePartecipante = utenteRepository.findByNickname(nickname)
                    .orElseThrow(() -> new RuntimeException("Utente non trovato: " + nickname));
            Abitazione abitazionePartecipante = utentePartecipante.getAbitazione();
            abitazioniPartecipanti.add(abitazionePartecipante);
        }

        //calcola il punteggio in base a difficoltà e durata
        int punteggio = calcolaPunteggioGruppo(difficolta, durata);

        //calcola l'obiettivo per ogni abitazione
        float consumoCreatore = abitazioneCreatore.getConsumoTotale();
        float obiettivo = calcolaObiettivo(difficolta, durata, consumoCreatore);
        for (Abitazione abitazione : abitazioniPartecipanti) {
            float consumoPartecipante = abitazione.getConsumoTotale();
            obiettivo += calcolaObiettivo(difficolta, durata, consumoPartecipante);
        }

        //calcola il numero dei partecipanti
        int numeroPartecipanti = abitazioniPartecipanti.size() + 1;

        //calcola l'obiettivo della sfida come una media degli obiettivi delle abitazioni partecipanti
        float obiettivoSfidaGruppo = obiettivo/numeroPartecipanti;

        //calcola la data di scadenza
        LocalDateTime dataCreazione = LocalDateTime.now();
        LocalDateTime dataScadenza = switch (durata.toUpperCase()) {
            case "SETTIMANALE" -> dataCreazione.plusWeeks(1);
            case "MENSILE" -> dataCreazione.plusMonths(1);
            default -> throw new IllegalArgumentException("Durata non valida");
        };

        //crea l'id del gruppo
        Long idGruppo = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        //crea la sfida
        Sfida sfida = new Sfida();
        sfida.setTipoSfida(true); // Di gruppo
        sfida.setDifficolta(difficolta);
        sfida.setDurata(durata);
        sfida.setPunteggio(punteggio);
        sfida.setObiettivo(obiettivoSfidaGruppo);
        sfida.setDataScadenza(dataScadenza);
        sfida.setCompletamento(false);
        sfida.setIdGruppo(idGruppo);
        sfida.setAttivazione(false);
        sfida.setNumeroPartecipanti(numeroPartecipanti);
        sfida.setAbitazionePartecipante(abitazioneCreatore);

        return sfidaRepository.save(sfida);

    }

    public Sfida partecipaASfidaGruppo(Long idGruppo, String nicknamePartecipante, String nicknameCreatore) {

        // Recupera l'utente che ha creato la sfida e mandato l'invito
        Utente utente = utenteRepository.findByNickname(nicknameCreatore)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Abitazione abitazioneCreatore = utente.getAbitazione();

        // Recupera l'utente partecipante
        Utente utentePartecipante = utenteRepository.findByNickname(nicknamePartecipante)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Abitazione abitazionePartecipante = utentePartecipante.getAbitazione();

        //recupera la sfida creata
        Sfida sfidaOriginale = sfidaRepository.findByIdGruppoAndAbitazionePartecipante(idGruppo, abitazioneCreatore);
        if (sfidaOriginale == null) {
            throw new RuntimeException("Sfida con idGruppo:" + idGruppo + " non trovata, per il creatore con abitazione: " + abitazioneCreatore);
        }

        // Crea una nuova istanza della sfida per l'utente partecipante
        Sfida nuovaSfida = new Sfida();
        nuovaSfida.setTipoSfida(true); // Sfida di gruppo
        nuovaSfida.setIdGruppo(idGruppo); // Usa lo stesso ID gruppo
        nuovaSfida.setDifficolta(sfidaOriginale.getDifficolta());
        nuovaSfida.setDurata(sfidaOriginale.getDurata());
        nuovaSfida.setPunteggio(sfidaOriginale.getPunteggio());
        nuovaSfida.setObiettivo(sfidaOriginale.getObiettivo());
        nuovaSfida.setDataScadenza(sfidaOriginale.getDataScadenza());
        nuovaSfida.setCompletamento(false);
        nuovaSfida.setAbitazionePartecipante(abitazionePartecipante);

        // Salva la nuova istanza nel database
        sfidaRepository.save(nuovaSfida);

        // Conta i partecipanti associati all'idGruppo
        int partecipanti = sfidaRepository.countByIdGruppo(idGruppo);

        // Attiva la sfida se il numero di partecipanti è stato raggiunto
        if (partecipanti >= sfidaOriginale.getNumeroPartecipanti()) {
            // Recupera tutte le istanze della sfida di gruppo
            List<Sfida> sfideGruppo = sfidaRepository.findByIdGruppo(idGruppo);

            // Imposta "attivazione" su true per tutte le sfide nel gruppo
            for (Sfida sfida : sfideGruppo) {
                sfida.setAttivazione(true);
            }

            // Salva tutte le sfide aggiornate
            sfidaRepository.saveAll(sfideGruppo);
        }

        return nuovaSfida;
    }

}
