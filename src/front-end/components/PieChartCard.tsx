// src/components/PieChartCard.tsx

import React from 'react';
import { View, Text } from 'react-native';
import { PieChart } from 'react-native-chart-kit';
import { pieChartConfig } from '../styling/PieChartConfig';

interface PieChartData {
    name: string;
    value: number;
    color: string;
    legendFontColor: string;
    legendFontSize: number;
}

interface PieChartCardProps {
    data: PieChartData[];
    title: string;
}

const PieChartCard: React.FC<PieChartCardProps> = ({ data, title }) => {
    return (
        <View style={{ marginBottom: 20 }}>
            <Text>{title}</Text>
            <PieChart
                data={data}
                width={400} // Larghezza del grafico
                height={220} // Altezza del grafico
                chartConfig={pieChartConfig}
                accessor="value"
                backgroundColor="transparent"
                paddingLeft="15"
            />
        </View>
    );
};

export default PieChartCard;
