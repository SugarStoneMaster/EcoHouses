import React from 'react';
import { View, Text } from 'react-native';
import { LineChart } from 'react-native-chart-kit';
import { chartConfig } from '../styling/DashboardStyle';

// Definiamo un tipo esplicito per i dati del grafico a linee
interface LineChartData {
    x: string; // Data o etichetta dell'asse X
    y: number; // Valore dell'asse Y
}

interface LineChartCardProps {
    data: LineChartData[]; // Array di oggetti con i dati per il grafico a linee
    title: string;
}

const LineChartCard: React.FC<LineChartCardProps> = ({ data, title }) => {
    // Separiamo i dati in due array per X e Y
    const xValues = data.map((item) => item.x);
    const yValues = data.map((item) => item.y);

    return (
        <View style={{ marginBottom: 20 }}>
            <Text>{title}</Text>
            <LineChart
                data={{
                    labels: xValues,
                    datasets: [
                        {
                            data: yValues,
                        },
                    ],
                }}
                width={400} // Imposta la larghezza del grafico
                height={220} // Imposta l'altezza del grafico
                chartConfig={chartConfig}
            />
        </View>
    );
};

export default LineChartCard;
