import AsyncStorage from '@react-native-async-storage/async-storage';
import apiEndpoint from '../config/apiEndpointConfig';

//const apiEndpoint = '192.168.1.47:8080/api/utenti/'; // Cambia questo con l'indirizzo del tuo back-end

class UserApi {
    // Funzione per effettuare il login
    static async login(emailOrUsername: string, password: string) {
        try {
            // Fai una richiesta POST al back-end per autenticare l'utente
            let res = await fetch('http://' + apiEndpoint + ':8080/api/utenti/autenticazioneUtente', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    identificatore: emailOrUsername,
                    password: password,
                }),
            });

            // Gestisci la risposta del server
            if (!res.ok) {
                throw new Error('Errore durante la comunicazione con il server');
            }

            let json = await res.json(); // Ottieni la risposta come oggetto JSON

            // Unisci i dati dell'utente con l'informazione sulla sessione
            let session = { isSignedIn: true, ...json };
            console.log('login ', session); // Log dei dati della sessione per il debug

            // Salva la sessione utente (vedi il metodo storeUserSession qui sotto)
            await this.storeUserSession(session);

            return session; // Ritorna l'oggetto di sessione
        } catch (error: unknown) {
            // Verifica se l'errore è un'istanza di Error
            if (error instanceof Error) {
                console.error('Errore nel login:', error.message);
                return {
                    isSignedIn: false,
                    message: error.message || 'Errore sconosciuto',
                };
            }
            // Se non è un'istanza di Error, gestisci il caso in cui l'errore sia sconosciuto
            console.error('Errore sconosciuto durante il login:', error);
            return {
                isSignedIn: false,
                message: 'Errore sconosciuto durante il login',
            };
        }
    }

    // Funzione per salvare la sessione dell'utente (puoi usare AsyncStorage o qualsiasi altro metodo)
    static async storeUserSession(session: any) {
        try {
            const sessionString = JSON.stringify(session);
            await AsyncStorage.setItem('userSession', sessionString);
            console.log('Sessione utente salvata con successo:', session);
        } catch (error) {
            console.error('Errore nel salvataggio della sessione:', error);
        }
    }
}

export default UserApi;
