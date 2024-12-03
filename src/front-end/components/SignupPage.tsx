import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, TouchableOpacity, Modal, StyleSheet, Pressable } from 'react-native';
import CheckBox from '@react-native-community/checkbox';
import Icon from 'react-native-vector-icons/FontAwesome';
import SignupPageStyles from '../styling/SignupPageStyles';

const SignupPage = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [isManager, setIsManager] = useState(false);
    const [modalVisible, setModalVisible] = useState(false);

    // Dati abitazione
    const [squareMeters, setSquareMeters] = useState('');
    const [city, setCity] = useState('');
    const [people, setPeople] = useState('');
    const [energyClass, setEnergyClass] = useState('');

    // Stato per controllare se il pulsante del popup è abilitato
    const [isPopupButtonEnabled, setIsPopupButtonEnabled] = useState(false);

    // Stato per controllare se il pulsante principale è abilitato
    const [isButtonEnabled, setIsButtonEnabled] = useState(false);

    // Funzione per controllare se tutti i campi del popup sono compilati
    const checkPopupFields = () => {
        if (squareMeters && city && people && energyClass) {
            setIsPopupButtonEnabled(true);
        } else {
            setIsPopupButtonEnabled(false);
        }
    };

    // Funzione per controllare se tutti i campi principali sono compilati
    const checkFields = () => {
        if (
            username &&
            email &&
            password &&
            confirmPassword &&
            password === confirmPassword &&
            (!isManager || isPopupButtonEnabled)
        ) {
            setIsButtonEnabled(true);
        } else {
            setIsButtonEnabled(false);
        }
    };

    // Esegui il controllo dei campi ogni volta che i valori cambiano
    useEffect(() => {
        checkPopupFields();
    }, [squareMeters, city, people, energyClass]);

    useEffect(() => {
        checkFields();
    }, [username, email, password, confirmPassword, isManager, isPopupButtonEnabled]);

    const handleSignup = () => {
        const requestData = {
            username,
            email,
            password,
            confirmPassword,
            isManager,
            houseDetails: isManager ? { squareMeters, city, people, energyClass } : null,
        };

        console.log('Dati inviati:', requestData);

        // Qui puoi fare la richiesta HTTP
        // fetch('/api/register', {
        //   method: 'POST',
        //   headers: { 'Content-Type': 'application/json' },
        //   body: JSON.stringify(requestData),
        // })
        //   .then(response => response.json())
        //   .then(data => console.log('Risposta server:', data))
        //   .catch(error => console.error('Errore:', error));
    };

    return (
        <View style={SignupPageStyles.container}>
            <Text style={SignupPageStyles.title}>Benvenuto in EcoHouses!</Text>

            {/* Input Nome Utente */}
            <View style={SignupPageStyles.inputContainer}>
                <Icon name="user" size={20} style={SignupPageStyles.icon} />
                <TextInput
                    style={SignupPageStyles.input}
                    placeholder="Nome utente"
                    placeholderTextColor="grey"
                    value={username}
                    onChangeText={setUsername}
                />
            </View>

            {/* Input Email */}
            <View style={SignupPageStyles.inputContainer}>
                <Icon name="envelope" size={20} style={SignupPageStyles.icon} />
                <TextInput
                    style={SignupPageStyles.input}
                    placeholder="Email"
                    placeholderTextColor="#888"
                    keyboardType="email-address"
                    value={email}
                    onChangeText={setEmail}
                />
            </View>

            {/* Input Password */}
            <View style={SignupPageStyles.inputContainer}>
                <Icon name="lock" size={20} style={SignupPageStyles.icon} />
                <TextInput
                    style={SignupPageStyles.input}
                    placeholder="Password"
                    placeholderTextColor="grey"
                    secureTextEntry
                    value={password}
                    onChangeText={setPassword}
                />
            </View>

            {/* Input Conferma Password */}
            <View style={SignupPageStyles.inputContainer}>
                <Icon name="lock" size={20} style={SignupPageStyles.icon} />
                <TextInput
                    style={SignupPageStyles.input}
                    placeholder="Conferma password"
                    placeholderTextColor="#888"
                    secureTextEntry
                    value={confirmPassword}
                    onChangeText={setConfirmPassword}
                />
            </View>

            {/* Checkbox Gestore */}
            <View style={SignupPageStyles.checkboxContainer}>
                <CheckBox
                    value={isManager}
                    onValueChange={(value) => {
                        setIsManager(value);
                        setModalVisible(value); // Mostra o nasconde il popup
                    }}
                    boxType="circle"
                />
                <Text style={SignupPageStyles.checkboxLabel}>Sono il gestore della mia abitazione</Text>
            </View>

            {/* Modal per i dettagli dell'abitazione */}
            <Modal
                visible={modalVisible}
                transparent={true}
                animationType="slide"
                onRequestClose={() => setModalVisible(false)} // Chiudi il popup cliccando all'esterno
            >
                <Pressable
                    style={modalStyles.modalContainer}
                    onPress={() => setModalVisible(false)} // Chiude cliccando all'esterno
                >
                    <Pressable style={modalStyles.modalContent}>
                        <Text style={SignupPageStyles.title}>Inserisci dettagli abitazione</Text>

                        {/* Input Metratura */}
                        <View style={SignupPageStyles.inputContainer}>
                            <Icon name="home" size={20} style={SignupPageStyles.icon} />
                            <TextInput
                                style={SignupPageStyles.input}
                                placeholder="Metratura"
                                placeholderTextColor="grey"
                                value={squareMeters}
                                onChangeText={setSquareMeters}
                                keyboardType="numeric"
                            />
                        </View>

                        {/* Input Comune */}
                        <View style={SignupPageStyles.inputContainer}>
                            <Icon name="map-marker" size={20} style={SignupPageStyles.icon} />
                            <TextInput
                                style={SignupPageStyles.input}
                                placeholder="Comune"
                                placeholderTextColor="grey"
                                value={city}
                                onChangeText={setCity}
                            />
                        </View>

                        {/* Input Numero Persone */}
                        <View style={SignupPageStyles.inputContainer}>
                            <Icon name="users" size={20} style={SignupPageStyles.icon} />
                            <TextInput
                                style={SignupPageStyles.input}
                                placeholder="Numero persone"
                                placeholderTextColor="grey"
                                keyboardType="numeric"
                                value={people}
                                onChangeText={setPeople}
                            />
                        </View>

                        {/* Input Classe Energetica */}
                        <View style={SignupPageStyles.inputContainer}>
                            <Icon name="bolt" size={20} style={SignupPageStyles.icon} />
                            <TextInput
                                style={SignupPageStyles.input}
                                placeholder="Classe energetica"
                                placeholderTextColor="grey"
                                value={energyClass}
                                onChangeText={setEnergyClass}
                            />
                        </View>

                        {/* Bottone Registrati */}
                        <TouchableOpacity
                            style={[
                                SignupPageStyles.button,
                                { backgroundColor: isPopupButtonEnabled ? '#007BFF' : '#ccc' }, // Cambia colore in base allo stato
                            ]}
                            onPress={() => {
                                if (isPopupButtonEnabled) {
                                    setModalVisible(false);
                                    handleSignup();
                                }
                            }}
                            disabled={!isPopupButtonEnabled} // Disabilita se non compilato
                        >
                            <Text style={SignupPageStyles.buttonText}>Registrati</Text>
                        </TouchableOpacity>
                    </Pressable>
                </Pressable>
            </Modal>

            {/* Bottone Registrati */}
            <TouchableOpacity
                style={[
                    SignupPageStyles.button,
                    { backgroundColor: isButtonEnabled ? '#007BFF' : '#ccc' }, // Cambia colore in base allo stato
                ]}
                onPress={handleSignup}
                disabled={!isButtonEnabled} // Disabilita se non compilato
            >
                <Text style={SignupPageStyles.buttonText}>Registrati</Text>
            </TouchableOpacity>
        </View>
    );
};

const modalStyles = StyleSheet.create({
    modalContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
    },
    modalContent: {
        width: '90%',
        backgroundColor: '#fff',
        borderRadius: 10,
        padding: 20,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 4,
        elevation: 5,
    },
});

export default SignupPage;
