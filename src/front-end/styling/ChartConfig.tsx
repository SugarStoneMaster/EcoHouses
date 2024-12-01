// src/styling/ChartConfig.ts

import { StyleSheet } from 'react-native';

export const chartConfig = {
    backgroundGradientFrom: '#ffffff',
    backgroundGradientTo: '#ffffff',
    color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`, // Colore della linea
    labelColor: () => '#000', // Colore delle etichette
    strokeWidth: 2, // Larghezza della linea
};
