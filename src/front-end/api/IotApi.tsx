import AsyncStorage from '@react-native-async-storage/async-storage';
import apiEndpoint from '../config/apiEndpointConfig';

export type Device = {
    numeroSerie: number;
    nomeDispositivo: string;
    tipoDispositivo: string;
    statoConnessione: boolean;
    statoAccensione: boolean;
};

export const fetchDevices = async (): Promise<Device[]> => {
    // Log di debug per l'inizio del metodo
    console.log('[DEBUG] fetchDevices: Inizio del metodo.');

    try {
        // Recupera la sessione utente salvata
        console.log('[DEBUG] fetchDevices: Recupero della sessione utente da AsyncStorage.');
        const sessionString = await AsyncStorage.getItem('userSession');

        if (!sessionString) {
            console.error("[DEBUG] fetchDevices: Sessione non trovata. L'utente deve effettuare il login.");
            throw new Error('Sessione non trovata. Effettua il login.');
        }

        console.log('[DEBUG] fetchDevices: Sessione trovata:', sessionString);
        const session = JSON.parse(sessionString);

        const nickname = session.nickname; // Supponiamo che 'nickname' sia il campo richiesto
        console.log('[DEBUG] fetchDevices: Nickname recuperato dalla sessione:', nickname);

        if (!nickname) {
            console.error('[DEBUG] fetchDevices: Nickname non disponibile nella sessione.');
            throw new Error('Nickname non disponibile nella sessione.');
        }

        // Effettua la chiamata API passando il nickname nel corpo della richiesta
        console.log(
            '[DEBUG] fetchDevices: Inizio chiamata API a',
            `http://${apiEndpoint}:8080/api/IoT/recupera-dispositivi`
        );

        const response = await fetch('http://' + apiEndpoint + ':8080/api/IoT/recupera-dispositivi', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ nickname }), // Invia il nickname nel corpo della richiesta
        });

        console.log('[DEBUG] fetchDevices: Risposta HTTP ricevuta. Stato:', response.status);

        if (!response.ok) {
            console.error('[DEBUG] fetchDevices: Errore HTTP! Stato:', response.status);
            throw new Error(`Errore HTTP! Stato: ${response.status}`);
        }

        const data: Device[] = await response.json();
        console.log('[DEBUG] fetchDevices: Dispositivi recuperati con successo:', data);
        return data;
    } catch (error) {
        console.error('[DEBUG] fetchDevices: Errore durante il recupero dei dispositivi:', error);
        throw error;
    } finally {
        console.log('[DEBUG] fetchDevices: Fine del metodo.');
    }
};
