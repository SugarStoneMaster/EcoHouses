// src/styling/PieChartConfig.ts

export interface PieChartConfig {
    backgroundColor: string;
    color: (opacity?: number) => string; // Funzione che accetta l'opacità
    labelColor: (opacity?: number) => string; // Funzione che accetta l'opacità
    decimalPlaces: number;
    style: {
        borderRadius: number;
    };
}

// Configurazione aggiornata con le funzioni per colorare dinamicamente
export const pieChartConfig: PieChartConfig = {
    backgroundColor: '#ffffff',
    color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`, // Colore della sezione
    labelColor: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`, // Colore dinamico delle etichette
    decimalPlaces: 2,   // Decimali per i valori
    style: {
        borderRadius: 16, // Border radius per la torta
    },
};
