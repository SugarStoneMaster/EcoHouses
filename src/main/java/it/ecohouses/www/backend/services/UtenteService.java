package it.ecohouses.www.backend.services;

import it.ecohouses.www.backend.model.Utente;
import it.ecohouses.www.backend.repositories.AbitazioneRepository;
import it.ecohouses.www.backend.repositories.UtenteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final AbitazioneRepository abitazioneRepository;
    private static final Logger logger = LoggerFactory.getLogger(UtenteService.class);


    @Autowired
    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder, AbitazioneRepository abitazioneRepository) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.abitazioneRepository = abitazioneRepository;
    }
        public Utente registrazioneUtente(@Valid Utente utente) {
            // Validate nickname
            if (utenteRepository.existsByNickname(utente.getNickname())) {
                throw new IllegalArgumentException("Nickname già utilizzato.");
            }

            if (!Pattern.matches("^[a-zA-Z0-9_]{3,15}$", utente.getNickname())) {
                throw new IllegalArgumentException("Il nickname deve essere tra 3 e 15 caratteri e può contenere solo lettere, numeri e '_'.");
            }

            // Validate email
            if (utenteRepository.existsByEmail(utente.getEmail())) {
                throw new IllegalArgumentException("Email già utilizzata.");
            }

            if (!Pattern.matches("^[^@\\s]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", utente.getEmail())) {
                throw new IllegalArgumentException("Email non valida.");
            }

            // Validate password
            if (!utente.getPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%&*!]).{12,}$")) {
                throw new IllegalArgumentException("La password deve contenere almeno una lettera maiuscola, una lettera minuscola, un numero e un carattere speciale (@, #, $, %, &, *, !).");
            }

            // Encrypt password
            utente.setPassword(passwordEncoder.encode(utente.getPassword()));

            try {
                return utenteRepository.save(utente);
            } catch (Exception e) {
                logger.error("Error saving user: {}", e.getMessage());
                throw new IllegalArgumentException("Errore durante il salvataggio dell'utente.");
            }
        }

        @Transactional
        public Utente registrazioneGestore(@Valid Utente gestore) {
            if (utenteRepository.existsByNickname(gestore.getNickname())) {
                throw new IllegalArgumentException("Nickname già utilizzato.");
            }

            if (!Pattern.matches("^[a-zA-Z0-9_]{3,15}$", gestore.getNickname())) {
                throw new IllegalArgumentException("Il nickname deve essere tra 3 e 15 caratteri e può contenere solo lettere, numeri e '_'.");
            }

            if (utenteRepository.existsByEmail(gestore.getEmail())) {
                throw new IllegalArgumentException("Email già utilizzata.");
            }

            if (!Pattern.matches("^[^@\\s]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", gestore.getEmail())) {
                throw new IllegalArgumentException("Email non valida.");
            }

            if (!gestore.getPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%&*!]).{12,}$")) {
                throw new IllegalArgumentException("La password deve contenere almeno una lettera maiuscola, una lettera minuscola, un numero e un carattere speciale.");
            }

            gestore.setPassword(passwordEncoder.encode(gestore.getPassword()));

            if (gestore.getAbitazione() == null) {
                throw new IllegalArgumentException("Il gestore deve avere una casa associata.");
            }

            try {
                return utenteRepository.save(gestore);
            } catch (Exception e) {
                logger.error("Error saving gestore and abitazione: {}", e.getMessage());
                if (gestore.getAbitazione() != null) {
                    // Rollback logic, delete associated habitation
                    abitazioneRepository.delete(gestore.getAbitazione());
                }
                throw new IllegalArgumentException("Errore durante il salvataggio del gestore e dell'abitazione.");
            }
        }


    public Utente autenticazioneUtente(String identificatore, String password) {
        Optional<Utente> utenteOpt;

        // Determina se l'identificatore è un'email o un nickname
        if (identificatore.contains("@")) {
            utenteOpt = utenteRepository.findByEmail(identificatore);
        } else {
            utenteOpt = utenteRepository.findByNickname(identificatore);
        }

        // Verifica la presenza dell'utente
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();

            // Verifica la password
            if (passwordEncoder.matches(password, utente.getPassword())) {
                return utente;
            } else {
                throw new IllegalArgumentException("Password errata.");
            }
        } else {
            throw new IllegalArgumentException("Utente non trovato.");
        }
    }


}
