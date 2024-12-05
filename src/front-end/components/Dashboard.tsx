// src/components/Dashboard.tsx
import React, { useState, useEffect } from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import { LineChart, PieChart } from 'react-native-chart-kit';
import { styles, chartConfig, pieChartConfig } from '../Styling/DashboardStyle';
import DashboardAPI from '../api/DashboardAPI';

const Dashboard: React.FC = () => {
    const [selectedTime, setSelectedTime] = useState<string>('day'); // Stato per il periodo selezionato
    const [lineChartData, setLineChartData] = useState<any>([]); // Stato per il grafico a linee
    const [pieChartData, setPieChartData] = useState<any>([]); // Stato per il grafico a torta
    const abitazioneId = 1; // ID fisso per l'abitazione, potrebbe essere dinamico

    /**
     * Funzione per caricare i dati dai metodi di `DashboardAPI`.
     */
    const fetchData = async () => {
        try {
            // Recupera i dati di consumo energetico
            const consumiData = await DashboardAPI.fetchConsumi(abitazioneId, selectedTime);

            // Recupera i dati di produzione energetica
            const produzioneData = await DashboardAPI.fetchProduzione(abitazioneId, selectedTime);

            // Adatta i dati per il grafico a linee
            setLineChartData(
                consumiData.map((item: any) => ({
                    x: new Date(item.data).toLocaleDateString(), // Etichetta asse X
                    y: item.consumo, // Valore asse Y
                }))
            );

            // Adatta i dati per il grafico a torta
            setPieChartData(
                produzioneData.map((item: any) => ({
                    name: item.tipo, // Nome della sezione
                    value: item.produzione, // Valore della sezione
                    color: randomColor(), // Colore generato casualmente
                    legendFontColor: '#7F7F7F', // Colore del font della leggenda
                    legendFontSize: 15, // Dimensione del font della leggenda
                }))
            );
        } catch (error) {
            console.error('Errore durante il caricamento dei dati:', error);
        }
    };

    /**
     * Genera un colore casuale per il grafico a torta.
     */
    const randomColor = () => {
        const letters = '0123456789ABCDEF';
        let color = '#';
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    };

    // Carica i dati ogni volta che cambia il periodo selezionato
    useEffect(() => {
        fetchData();
    }, [selectedTime]);

    /**
     * Componente per selezionare il periodo temporale.
     */
    const TimeSelector: React.FC = () => (
        <View style={styles.timeSelectorContainer}>
            {['day', 'month', 'year'].map((time) => (
                <TouchableOpacity
                    key={time}
                    style={[
                        styles.timeButton,
                        selectedTime === time && styles.timeButtonSelected, // Stile selezionato
                    ]}
                    onPress={() => setSelectedTime(time)} // Aggiorna lo stato
                >
                    <Text style={styles.timeButtonText}>
                        {time === 'day' ? 'Giorno' : time === 'month' ? 'Mese' : 'Anno'}
                    </Text>
                </TouchableOpacity>
            ))}
        </View>
    );

    // Layout principale della dashboard
    return (
        <View style={styles.dashboardContainer}>
            <TimeSelector />
            <View style={styles.card}>
                <Text style={styles.cardTitle}>Consumi vs Media</Text>
                <LineChart
                    data={{
                        labels: lineChartData.map((item: any) => item.x), // Etichette asse X
                        datasets: [{ data: lineChartData.map((item: any) => item.y) }], // Valori asse Y
                    }}
                    width={400}
                    height={220}
                    chartConfig={chartConfig}
                />
            </View>
            <View style={styles.card}>
                <Text style={styles.cardTitle}>Energia Consuma vs Prodotta</Text>
                <PieChart
                    data={pieChartData}
                    width={400}
                    height={220}
                    chartConfig={pieChartConfig}
                    accessor="value"
                    backgroundColor="transparent"
                    paddingLeft="15"
                />
            </View>
        </View>
    );
};

export default Dashboard;
