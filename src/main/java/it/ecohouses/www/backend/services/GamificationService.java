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
    public Classifica getClassificaLocale(String nickname) {
        // Recupera il comune dell'utente
        String comune = utenteRepository.findComuneByNickname(nickname);

        // Verifica se esiste una classifica locale per il comune
        // Se non esiste, crea una nuova classifica locale
        if (!classificaRepository.existsByComune(comune)) {
            Classifica classifica = new Classifica();
            classifica.setTipoClassifica(true); // Locale
            classifica.setDataCreazione(LocalDate.now());
            classifica.setDataAggiornamento(null);
            classifica.setComune(comune);

            // Salva la nuova classifica locale
            classificaRepository.save(classifica);

            // Restituisce la nuova classifica locale
            return classifica;
        }

        else{// Recupera la classifica locale più recente per il comune
            return classificaRepository.findLatestLocalByComune(comune)
                    .orElseThrow(() -> new RuntimeException("Errore nel recupero della classifica locale."));
        }

    }

    public Classifica getClassificaGlobale() {
        // Recupera la classifica globale più recente
        return classificaRepository.findLatestGlobal()
                .orElseThrow(() -> new RuntimeException("Errore nel recupero della classifica globale."));
    }

    /**
     * Ottiene le prime 100 abitazioni di una classifica.
     * @return Una lista delle prime 100 abitazioni nella classifica.
     */
    public List<ClassificaAbitazione> getTop100Abitazioni(Long idClassifica) {

        return classificaAbitazioneRepository.findTop100ByClassifica(idClassifica);

    }

    //verifica se l'utente è l'unico in classifica locale, restituisce true in caso affermativo
    public boolean isUtenteUnicoInClassificaLocale(String nickname, List<ClassificaAbitazione> top100Abitazioni) {
        // Recupera l'abitazione dell'utente in base al nickname
        Optional<Utente> utenteOpt = utenteRepository.findByNickname(nickname);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            Long idAbitazione = utente.getAbitazione().getIdAbitazione();

            // Verifica se la lista contiene un solo elemento ed è l'abitazione dell'utente
            return top100Abitazioni.size() == 1 && top100Abitazioni.get(0).getAbitazione().getIdAbitazione().equals(idAbitazione);
        }
        return false;
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

        LocalDateTime dataCreazione= LocalDateTime.now();

        // Crea e salva la sfida
        Sfida sfida = new Sfida();
        sfida.setTipoSfida(false); // Individuale
        sfida.setDifficolta(difficolta);
        sfida.setDurata(durata);
        sfida.setPunteggio(punteggio);
        sfida.setObiettivo(obiettivo);
        sfida.setDataSfida(dataCreazione);
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
            default -> throw new IllegalArgumentException("Difficoltà non valida");
        };

        int moltiplicatore = switch (durata.toUpperCase()) {
            case "SETTIMANALE" -> 2;
            case "MENSILE" -> 3;
            default -> throw new IllegalArgumentException("Durata non valida");
        };

        return punteggioBase * moltiplicatore;
    }

    public Sfida creaSfidaDiGruppo(String difficolta, String durata, String nicknameCreatore, int numeroPartecipanti) {

        try {

            //recupera l'abitazione dell'utente creatore
            Utente utenteCreatore = utenteRepository.findByNickname(nicknameCreatore)
                    .orElseThrow(() -> new RuntimeException("Utente non trovato: " + nicknameCreatore));
            Abitazione abitazioneCreatore = utenteCreatore.getAbitazione();

            //calcola il punteggio in base a difficoltà e durata
            int punteggio = calcolaPunteggioGruppo(difficolta, durata);

            //calcola l'obiettivo per ogni abitazione
            float consumoCreatore = abitazioneCreatore.getConsumoTotale();
            float obiettivo = calcolaObiettivo(difficolta, durata, consumoCreatore);

            LocalDateTime dataCreazione = LocalDateTime.now();

            //crea l'id del gruppo
            Long idGruppo = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

            //crea la sfida
            Sfida sfida = new Sfida();
            sfida.setTipoSfida(true); // Di gruppo
            sfida.setDifficolta(difficolta);
            sfida.setDurata(durata);
            sfida.setPunteggio(punteggio);
            sfida.setObiettivo(obiettivo);
            sfida.setDataSfida(dataCreazione);
            sfida.setCompletamento(false);
            sfida.setIdGruppo(idGruppo);
            sfida.setAttivazione(false);
            sfida.setNumeroPartecipanti(numeroPartecipanti);
            sfida.setAbitazionePartecipante(abitazioneCreatore);

            return sfidaRepository.save(sfida);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Errore: " + e.getMessage());
        }

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
        nuovaSfida.setDataSfida(sfidaOriginale.getDataSfida());
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

