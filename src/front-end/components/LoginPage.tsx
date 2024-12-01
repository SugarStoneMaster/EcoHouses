import React, { useState, useContext } from 'react';
import { View, Text, TextInput, TouchableOpacity, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { styles } from '../styling/LoginCSS';
import UserApi from '../api/UserApi';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import UserContext from '../contexts/UserContext';

const LoginPage = () => {
    const [emailOrUsername, setEmailOrUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigation = useNavigation();
    const { setUserSession } = useContext(UserContext); // Assicurati che UserContext sia configurato correttamente

    // Funzione per gestire il login
    const login = async () => {
        if (!emailOrUsername || !password) {
            Alert.alert('Errore', 'Inserisci email/username e password.');
            return;
        }

        try {
            const res = await UserApi.login(emailOrUsername, password); // Chiamata al metodo UserApi.login
            if (res && res.isSignedIn) {
                // @ts-ignore
                setUserSession?.(res); // Aggiorna la sessione utente, se il contesto è configurato
                Alert.alert('Successo', 'Accesso effettuato con successo!');
            } else {
                Alert.alert('Errore', 'Credenziali non valide. Riprova.');
            }
        } catch (err) {
            console.error('Errore durante il login:', err);
            Alert.alert('Errore', 'Impossibile completare il login. Riprova.');
        }
    };

    // Placeholder per altre modalità di login
    const loginWithGoogle = () => Alert.alert('Google Login', 'Login tramite Google non ancora implementato.');
    const loginWithFacebook = () => Alert.alert('Facebook Login', 'Login tramite Facebook non ancora implementato.');
    const loginWithInstagram = () => Alert.alert('Instagram Login', 'Login tramite Instagram non ancora implementato.');

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Benvenuto su EcoHouses!</Text>

            {/* Input per Email o Username */}
            <View style={styles.input}>
                <FontAwesome name="user" size={20} color="grey" style={styles.inputIcon} />
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

            {/* Divider */}
            <Text style={styles.socialLoginText}>Oppure accedi con:</Text>

            {/* Login Social */}
            <View style={styles.socialIcons}>
                <TouchableOpacity style={styles.socialIcon} onPress={loginWithGoogle}>
                    <FontAwesome name="google" size={24} color="black" />
                </TouchableOpacity>
                <TouchableOpacity style={styles.socialIcon} onPress={loginWithFacebook}>
                    <FontAwesome name="facebook" size={24} color="black" />
                </TouchableOpacity>
                <TouchableOpacity style={styles.socialIcon} onPress={loginWithInstagram}>
                    <FontAwesome name="instagram" size={24} color="black" />
                </TouchableOpacity>
            </View>

            {/* Registrazione */}
            <View style={styles.registerContainer}>
                <Text style={styles.registerText}>Non sei registrato?</Text>
                <TouchableOpacity
                    style={styles.registerButton}
                    onPress={() => navigation.navigate('Signup')}
                >
                    <Text style={styles.registerButtonText}>Registrati</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
};

export default LoginPage;
