import { StyleSheet } from 'react-native';

const DashboardStyle = StyleSheet.create({
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

export default DashboardStyle;
