// src/api/DashboardAPI.ts

import AsyncStorage from '@react-native-async-storage/async-storage';
import apiEndpoint from '../config/apiEndpointConfig';

export default class DashboardAPI {
    /**
     * Recupera l'ID dell'abitazione da AsyncStorage.
     */
    static async getAbitazioneId(): Promise<number | null> {
        try {
            const id = await AsyncStorage.getItem('abitazioneId');
            return id ? parseInt(id) : null; // Se non c'è, restituisce null
        } catch (error) {
            console.error("Errore durante il recupero dell'abitazioneId:", error);
            return null; // Se c'è un errore, restituisce null
        }
    }

    /**
     * Restituisce l'intervallo di date in base al periodo selezionato (giornaliero, mensile, annuale).
     */
    static getDateRange(selectedTime: string) {
        const now = new Date(); // Data corrente
        let inizio, fine;

        switch (selectedTime) {
            case 'day': // Periodo giornaliero
                fine = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                inizio = new Date(fine);
                inizio.setDate(inizio.getDate() - 6); // Fine: giorno successivo
                break;
            case 'month': // Periodo mensile
                fine = new Date(now.getFullYear(), now.getMonth(), 1);
                inizio = new Date(fine);
                inizio.setMonth(inizio.getMonth() - 30); // Fine: mese successivo
                break;
            case 'year': // Periodo annuale
                inizio = new Date(now.getFullYear(), 0, 1);
                fine = new Date(now.getFullYear() + 1, 0, 1); // Fine: anno successivo
                break;
            default:
                inizio = new Date();
                fine = new Date();
        }

        return { inizio: inizio.toISOString(), fine: fine.toISOString() };
    }

    /**
     * Recupera i dati di consumo energetico per un dato periodo e ID abitazione.
     */
    static async fetchConsumi(abitazioneId: number, selectedTime: string) {
        const { inizio, fine } = this.getDateRange(selectedTime);

        try {
            const response = await fetch('http://' + apiEndpoint + ':8080/api/dashboard/consumi', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json', // Specifica il formato JSON
                },
                body: JSON.stringify({ abitazioneId, inizio, fine }),
            });

            if (!response.ok) {
                throw new Error('Errore nella risposta del server');
            }

            const data = await response.json(); // Restituisce i dati come oggetto JavaScript
            return data;
        } catch (error) {
            console.error('Errore durante il recupero dei consumi:', error);
            throw error; // Propaga l'errore
        }
    }

    /**
     * Recupera i dati di produzione energetica per un dato periodo e ID abitazione.
     */
    static async fetchProduzione(abitazioneId: number, selectedTime: string) {
        const { inizio, fine } = this.getDateRange(selectedTime);

        try {
            const response = await fetch('http://' + apiEndpoint + ':8080/api/dashboard/produzione', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json', // Specifica il formato JSON
                },
                body: JSON.stringify({ abitazioneId, inizio, fine }),
            });

            if (!response.ok) {
                throw new Error('Errore nella risposta del server');
            }

            const data = await response.json(); // Restituisce i dati come oggetto JavaScript
            return data;
        } catch (error) {
            console.error('Errore durante il recupero della produzione:', error);
            throw error; // Propaga l'errore
        }
    }
}
