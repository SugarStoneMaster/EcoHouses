import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 16,
        backgroundColor: '#F5F5F5', // Sfondo chiaro
    },
    title: {
        fontSize: 28,
        fontWeight: 'bold',
        marginBottom: 16,
        textAlign: 'center',
        color: '#333333', // Titolo scuro per contrasto
    },
    deviceContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        backgroundColor: '#E8F5E9', // Colore verde chiaro per il dispositivo
        padding: 12,
        borderRadius: 8,
        marginBottom: 8,
        elevation: 3, // Leggera ombra per dare profondità
    },
    deviceName: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#333333', // Testo scuro per il nome del dispositivo
    },
    deviceType: {
        fontSize: 14,
        color: '#555555', // Colore più chiaro per il tipo di dispositivo
    },
    deviceStatus: {
        fontSize: 14,
        color: '#888888', // Colore grigio per il stato del dispositivo
    },
    deleteButton: {
        backgroundColor: '#FFCDD2', // Colore rosso chiaro per il bottone di rimozione
        padding: 8,
        borderRadius: 50,
    },
    deleteButtonText: {
        color: '#D32F2F', // Rosso scuro per il testo del bottone
        fontWeight: 'bold',
    },
    addButton: {
        marginTop: 20,
        backgroundColor: '#64B5F6', // Blu per il bottone di aggiunta
        paddingVertical: 12,
        borderRadius: 25,
        alignItems: 'center',
        width: '100%', // Bottone largo
    },
    addButtonText: {
        color: '#FFFFFF',
        fontWeight: 'bold',
        fontSize: 16,
    },
    modal: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(0,0,0,0.5)', // Modal con sfondo semitrasparente
    },
    modalContent: {
        backgroundColor: '#FFFFFF',
        padding: 20,
        borderRadius: 8,
        width: '80%',
        alignItems: 'center',
    },
});

export default styles;
