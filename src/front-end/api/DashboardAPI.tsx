// src/api/DashboardAPI.ts

export default class DashboardAPI {
    static getDateRange(selectedTime: string) {
        const now = new Date(); // Data corrente
        let inizio, fine;

        switch (selectedTime) {
            case 'day': // Periodo giornaliero
                inizio = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                fine = new Date(inizio);
                fine.setDate(fine.getDate() + 1); // Fine: giorno successivo
                break;
            case 'month': // Periodo mensile
                inizio = new Date(now.getFullYear(), now.getMonth(), 1);
                fine = new Date(inizio);
                fine.setMonth(fine.getMonth() + 1); // Fine: mese successivo
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
     * Recupera i dati di consumo energetico per il periodo selezionato.
     */
    static async fetchConsumi(abitazioneId: number, selectedTime: string) {
        const { inizio, fine } = this.getDateRange(selectedTime);

        const response = await fetch('http://localhost:8080/api/dashboard/consumi', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // Specifica il formato JSON
            },
            body: JSON.stringify({ abitazioneId, inizio, fine }),
        });

        return response.json(); // Restituisce i dati come oggetto JavaScript
    }

    /**
     * Recupera i dati di produzione energetica per il periodo selezionato.
     */
    static async fetchProduzione(abitazioneId: number, selectedTime: string) {
        const { inizio, fine } = this.getDateRange(selectedTime);

        const response = await fetch('http://localhost:8080/api/dashboard/produzione', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // Specifica il formato JSON
            },
            body: JSON.stringify({ abitazioneId, inizio, fine }),
        });

        return response.json(); // Restituisce i dati come oggetto JavaScript
    }
}
