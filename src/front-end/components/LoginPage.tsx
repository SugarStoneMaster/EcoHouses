import React, { useState, useContext } from 'react';
import { View, Text, TextInput, TouchableOpacity, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { styles } from '../styling/LoginCSS';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import UserApi from '../api/UserApi';
import UserContext from '../contexts/UserContext';

const LoginPage = () => {
    const [emailOrUsername, setEmailOrUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigation = useNavigation();
    const { setUserSession } = useContext(UserContext);

    // Funzione per gestire il login
    const login = async () => {
        if (!emailOrUsername || !password) {
            Alert.alert('Errore', 'Inserisci email/username e password');
            return;
        }

        try {
            const res = await UserApi.login(emailOrUsername, password); // Chiamata all'API per il login
            if (res.isSignedIn) {
                setUserSession?.(); // Imposta la sessione con i dati dell'utente
                Alert.alert('Successo', 'Accesso effettuato con successo!');
                navigation.navigate('IotPage');
            } else {
                Alert.alert('Errore', res.message || 'Credenziali non valide. Riprova.');
            }
        } catch (err) {
            console.error('Errore durante il login:', err);
            Alert.alert('Errore', 'Impossibile completare il login. Riprova.');
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Benvenuto su EcoHouses!</Text>

            {/* Input per Email o Username */}
            <View style={styles.input}>
                <FontAwesome name="pencil" size={20} color="grey" style={styles.inputIcon} />
                <TextInput
                    style={styles.inputText}
                    placeholder="Nome utente/Email"
                    placeholderTextColor="grey"
                    value={emailOrUsername}
                    onChangeText={(value) => setEmailOrUsername(value)}
                />
            </View>

            {/* Input per Password */}
            <View style={styles.input}>
                <FontAwesome name="lock" size={20} color="grey" style={styles.inputIcon} />
                <TextInput
                    style={styles.inputText}
                    placeholder="Password"
                    placeholderTextColor="grey"
                    secureTextEntry
                    value={password}
                    onChangeText={(value) => setPassword(value)}
                />
            </View>

            {/* Pulsante Login */}
            <TouchableOpacity style={styles.loginButton} onPress={login}>
                <Text style={styles.loginButtonText}>Login</Text>
            </TouchableOpacity>

            {/* Registrazione */}
            <View style={styles.registerContainer}>
                <Text style={styles.registerText}>Non sei registrato?</Text>
                <TouchableOpacity style={styles.registerButton} onPress={() => navigation.navigate('Signup')}>
                    <Text style={styles.registerButtonText}>Registrati</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
};

export default LoginPage;
