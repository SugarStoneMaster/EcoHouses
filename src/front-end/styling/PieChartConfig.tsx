// src/styling/PieChartConfig.ts

export interface PieChartConfig {
    backgroundColor: string;
    color: (opacity?: number) => string;
    labelColor: string;
    decimalPlaces: number;
    style: {
        borderRadius: number;
    };
}

export const pieChartConfig: PieChartConfig = {
    backgroundColor: '#ffffff',
    color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`, // Colore della sezione
    labelColor: '#000', // Colore delle etichette
    decimalPlaces: 2,   // Decimali per i valori
    style: {
        borderRadius: 16, // Border radius per la torta
    },
};
