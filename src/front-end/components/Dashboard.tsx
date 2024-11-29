import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Image } from 'react-native';
import ChartCard from './ChartCard'; // Assicurati di importare la ChartCard correttamente
import ChartsAPI from '../api/ChartsAPI';

const Dashboard: React.FC = () => {
    const [selectedTime, setSelectedTime] = useState<'giorno' | 'mese' | 'anno'>('giorno');

    const handleTimeChange = (time: 'giorno' | 'mese' | 'anno') => {
        setSelectedTime(time);
    };

    const { lineChart, pieChart } = ChartsAPI.fetchLineChartData(selectedTime);

    return (
        <ScrollView style={styles.container}>
            <View style={styles.timeButtonsContainer}>
                <TouchableOpacity style={styles.timeButton} onPress={() => handleTimeChange('giorno')}>
                    <Text style={styles.timeButtonText}>Giorno</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.timeButton} onPress={() => handleTimeChange('mese')}>
                    <Text style={styles.timeButtonText}>Mese</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.timeButton} onPress={() => handleTimeChange('anno')}>
                    <Text style={styles.timeButtonText}>Anno</Text>
                </TouchableOpacity>
            </View>

            <ChartCard
                title="Grafico Linea"
                imageSource={lineChart}
                onPress={() => console.log('Visualizza grafico a linee')}
            />

            <ChartCard
                title="Grafico a Torta"
                imageSource={pieChart}
                onPress={() => console.log('Visualizza grafico a torta')}
            />
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: '#f7f7f7',
    },
    timeButtonsContainer: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        marginBottom: 20,
    },
    timeButton: {
        backgroundColor: '#007BFF',
        padding: 10,
        borderRadius: 5,
    },
    timeButtonText: {
        color: '#fff',
        fontWeight: 'bold',
    },
});

export default Dashboard;
