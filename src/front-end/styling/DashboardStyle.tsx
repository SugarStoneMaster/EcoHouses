//DasboardStyle.tsx
import { StyleSheet } from 'react-native';

export const chartConfig = {
    backgroundGradientFrom: '#ffffff',
    backgroundGradientTo: '#ffffff',
    color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`,
    labelColor: () => '#000',
    strokeWidth: 2,
};

export const pieChartConfig = {
    backgroundColor: '#ffffff',
    color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`,
    labelColor: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,
    decimalPlaces: 2,
    style: {
        borderRadius: 16,
    },
};

export const styles = StyleSheet.create({
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
