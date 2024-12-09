import React, { useState, useEffect } from 'react';
import {
    View,
    Text,
    FlatList,
    TouchableOpacity,
    Alert,
    Modal,
    StyleSheet,
    PermissionsAndroid,
    Platform,
    ActivityIndicator,
} from 'react-native';
import styles from '../styling/IotStyle';
import { fetchDevices, Device } from '../api/IotApi'; // Importa la funzione e il tipo

const IotPage = (): JSX.Element => {
    const [devices, setDevices] = useState<Device[]>([]);
    const [isScannerVisible, setScannerVisible] = useState(false);
    const [loading, setLoading] = useState(false);

    const requestCameraPermission = async () => {
        if (Platform.OS === 'android') {
            try {
                const granted = await PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.CAMERA, {
                    title: 'Permesso per la fotocamera',
                    message: "L'app ha bisogno di accedere alla fotocamera per scansionare i codici QR.",
                    buttonNeutral: 'Chiedimi dopo',
                    buttonNegative: 'Annulla',
                    buttonPositive: 'OK',
                });
                return granted === PermissionsAndroid.RESULTS.GRANTED;
            } catch (err) {
                console.warn(err);
                return false;
            }
        }
        return true;
    };

    const openScanner = async () => {
        const hasPermission = await requestCameraPermission();
        if (hasPermission) {
            setScannerVisible(true);
        } else {
            Alert.alert('Permesso negato', 'Non puoi accedere alla fotocamera senza permesso.');
        }
    };

    const loadDevices = async () => {
        setLoading(true);
        try {
            const devicesList = await fetchDevices(); // Non passiamo nickname direttamente, lo otteniamo dalla sessione
            setDevices(devicesList);
        } catch (error) {
            Alert.alert('Errore', 'Impossibile recuperare i dispositivi. Riprova più tardi.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadDevices();
    }, []);

    const removeDevice = (deviceId: number) => {
        setDevices((prevDevices) => prevDevices.filter((device) => device.numeroSerie !== deviceId));
    };

    const confirmRemoveDevice = (deviceId: number) => {
        Alert.alert(
            'Rimuovere il dispositivo?',
            'Sei sicuro di voler rimuovere questo dispositivo?',
            [
                { text: 'No', style: 'cancel' },
                { text: 'Sì', onPress: () => removeDevice(deviceId), style: 'destructive' },
            ],
            { cancelable: true }
        );
    };

    const renderDevice = ({ item }: { item: Device }) => (
        <View style={styles.deviceContainer}>
            <Text style={styles.deviceName}>{item.nomeDispositivo}</Text>
            <TouchableOpacity style={styles.deleteButton} onPress={() => confirmRemoveDevice(item.numeroSerie)}>
                <Text style={styles.deleteButtonText}>Rimuovi</Text>
            </TouchableOpacity>
        </View>
    );

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Lista dispositivi</Text>
            {loading ? (
                <ActivityIndicator size="large" color="#0000ff" />
            ) : (
                <FlatList
                    data={devices}
                    keyExtractor={(item) => item.numeroSerie.toString()} // Usa numeroSerie come chiave unica
                    renderItem={renderDevice}
                />
            )}
            <TouchableOpacity style={styles.addButton} onPress={openScanner}>
                <Text style={styles.addButtonText}>+ Aggiungi dispositivo</Text>
            </TouchableOpacity>

            <Modal
                visible={isScannerVisible}
                animationType="slide"
                onRequestClose={() => setScannerVisible(false)}
            ></Modal>
        </View>
    );
};

export default IotPage;
