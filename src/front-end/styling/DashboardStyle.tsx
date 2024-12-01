import { StyleSheet } from 'react-native';

// Configurazione per i grafici (a linee e a torta)
export const chartConfig = {
    backgroundGradientFrom: '#ffffff',
    backgroundGradientTo: '#ffffff',
    color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`, // Colore della linea o delle barre
    labelColor: () => '#000', // Colore delle etichette
    strokeWidth: 2, // Larghezza della linea
};

// Configurazione del grafico a torta (colore e impostazioni delle sezioni)
export const pieChartConfig = {
    backgroundColor: '#ffffff',
    color: () => '#000', // Colore della sezione
    labelColor: '#000',
};

const styles = StyleSheet.create({
    dashboardContainer: {
        flex: 1,
        padding: 16,
        backgroundColor: '#f0f0f0',
    },
    timeSelectorContainer: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        marginBottom: 16,
    },
    timeButton: {
        padding: 10,
        borderWidth: 1,
        borderColor: '#ccc',
        borderRadius: 5,
        backgroundColor: '#fff',
    },
    timeButtonSelected: {
        backgroundColor: '#007bff',
        borderColor: '#0056b3',
    },
    timeButtonText: {
        color: '#000',
    },
    card: {
        marginBottom: 16,
        padding: 16,
        backgroundColor: '#fff',
        borderRadius: 8,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowRadius: 10,
        elevation: 2,
    },
    cardTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 8,
    },
});

export default styles;
