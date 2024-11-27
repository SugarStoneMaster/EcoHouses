import React from 'react';
import { View, Image, Text, StyleSheet, TouchableOpacity } from 'react-native';

// Definisci i tipi per le props che la componente ChartCard riceve
interface ChartCardProps {
    title: string;
    imageSource: string;  // Tipo per il percorso dell'immagine
    onPress: () => void;  // Funzione da eseguire quando viene cliccato il link
}

const ChartCard: React.FC<ChartCardProps> = ({ title, imageSource, onPress }) => {
    return (
        <View style={styles.cardContainer}>
            <Text style={styles.cardTitle}>{title}</Text>
            <Image source={require(`../images/${imageSource}`)} style={styles.chartImage} />
            <TouchableOpacity onPress={onPress}>
                <Text style={styles.chartLink}>Visualizza grafico</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    cardContainer: {
        padding: 20,
        borderRadius: 10,
        backgroundColor: '#fff',
        margin: 10,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.8,
        shadowRadius: 2,
    },
    cardTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 10,
    },
    chartImage: {
        width: '100%',
        height: 200,
        marginBottom: 10,
    },
    chartLink: {
        color: '#007BFF',
        textDecorationLine: 'underline',
        textAlign: 'center',
    },
});

export default ChartCard;
