import { StyleSheet } from 'react-native';

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 16,
        backgroundColor: '#E9F5F0', // Sfondo verde chiaro
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center',
        marginBottom: 20,
        color: '#000', // Colore del testo nero
    },
    inputContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 16,
        backgroundColor: '#CDE8D9', // Colore di sfondo degli input
        borderRadius: 25, // Arrotondamento
        paddingHorizontal: 10,
        height: 50,
    },
    icon: {
        marginRight: 8,
        color: '#000', // Icone nere
    },
    input: {
        flex: 1,
        fontSize: 16,
        color: '#000', // Testo nero
    },
    checkboxContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginVertical: 16,
    },
    checkboxLabel: {
        fontSize: 16,
        marginLeft: 8,
        color: '#000', // Testo nero
    },
    button: {
        backgroundColor: '#5DADE2', // Celeste
        padding: 15,
        borderRadius: 25, // Arrotondamento
        alignItems: 'center',
        shadowColor: '#000', // Ombra
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.3,
        shadowRadius: 4,
        elevation: 5, // Ombra su Android
    },
    buttonText: {
        color: '#fff', // Testo bianco
        fontSize: 18,
        fontWeight: 'bold',
    },
});

export default styles;
