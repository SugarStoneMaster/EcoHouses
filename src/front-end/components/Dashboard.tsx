// src/components/Dashboard.tsx

import React, { useState, useEffect } from 'react';
import { View, Text } from 'react-native';
import { fetchChartData } from '../api/ChartsAPI';
import TimeSelector from './TimeSelector';
import LineChartCard from './LineChartCard';
import PieChartCard from './PieChartCard';

const Dashboard: React.FC = () => {
    const [selectedTime, setSelectedTime] = useState<string>('day');
    const [lineChartData, setLineChartData] = useState<any>([]);
    const [pieChartData, setPieChartData] = useState<any>([]);

    useEffect(() => {
        const { lineChartData, pieChartData } = fetchChartData(selectedTime);
        setLineChartData(lineChartData);
        setPieChartData(pieChartData);
    }, [selectedTime]);

    return (
        <View style={{ flex: 1, padding: 16, backgroundColor: '#f0f0f0' }}>
            <TimeSelector selectedTime={selectedTime} setSelectedTime={setSelectedTime} />
            <LineChartCard data={lineChartData} title="Consumi vs Media" />
            <PieChartCard data={pieChartData} title="Energia Consuma vs Prodotta" />
        </View>
    );
};

export default Dashboard;
