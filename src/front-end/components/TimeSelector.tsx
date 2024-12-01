// src/components/TimeSelector.tsx

import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';

interface TimeSelectorProps {
    selectedTime: string;
    setSelectedTime: (time: string) => void;
}

const TimeSelector: React.FC<TimeSelectorProps> = ({ selectedTime, setSelectedTime }) => {
    return (
        <View style={{ flexDirection: 'row', justifyContent: 'space-around', marginBottom: 16 }}>
            <TouchableOpacity
                style={[styles.timeButton, selectedTime === 'day' && styles.timeButtonSelected]}
                onPress={() => setSelectedTime('day')}
            >
                <Text style={styles.timeButtonText}>Giorno</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={[styles.timeButton, selectedTime === 'month' && styles.timeButtonSelected]}
                onPress={() => setSelectedTime('month')}
            >
                <Text style={styles.timeButtonText}>Mese</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={[styles.timeButton, selectedTime === 'year' && styles.timeButtonSelected]}
                onPress={() => setSelectedTime('year')}
            >
                <Text style={styles.timeButtonText}>Anno</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = {
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
};

export default TimeSelector;
