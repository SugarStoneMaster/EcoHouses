import React, { useState, useEffect, useContext } from 'react';
import { View, Text, TextInput, StyleSheet, TouchableOpacity, Alert } from 'react-native';
import EncryptedStorage from 'react-native-encrypted-storage';
import { useNavigation } from '@react-navigation/native';
import MMButton from './MMButton';
import Colors from '../styling/colors';
import { styles } from '../styling/loginCSS'; // Stili già forniti in precedenza
import UserApi from '../api/UserApi';
import UserContext from '../contexts/UserContext';
import FontAwesome from 'react-native-vector-icons/FontAwesome'; // Per le icone social

const LoginPage = () => {
    const [emailOrUsername, setEmailOrUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isLogError, setLogError] = useState(false);
    const navigation = useNavigation();
    const userSession = useContext(UserContext);

    const login = async () => {
        try {
            let res = await UserApi.login(emailOrUsername, password);
            if (res && res.isSignedIn && userSession) {
                userSession.setUserSession(res);
            }
        } catch (err) {
            console.log("Errore durante il login: ", err);
            setLogError(true);
        }
    };

    const loginWithGoogle = async () => {
        // Simulazione autenticazione Google
        try {
            Alert.alert("Google Login", "Login tramite Google non ancora implementato.");
        } catch (err) {
            console.log("Errore durante il login con Google: ", err);
        }
    };

    const loginWithFacebook = async () => {
        // Simulazione autenticazione Facebook
        try {
            Alert.alert("Facebook Login", "Login tramite Facebook non ancora implementato.");
        } catch (err) {
            console.log("Errore durante il login con Facebook: ", err);
        }
    };

    const loginWithInstagram = async () => {
        // Simulazione autenticazione Instagram
        try {
            Alert.alert("Instagram Login", "Login tramite Instagram non ancora implementato.");
        } catch (err) {
            console.log("Errore durante il login con Instagram: ", err);
        }
    };

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
                    onChangeText={(value) => setPassword(value)}
                />
            </View>

            {/* Ricordami e Password dimenticata */}
            <View style={styles.rememberForgotContainer}>
                <Text style={styles.rememberText}>Ricordami</Text>
                <TouchableOpacity onPress={() => navigation.navigate("ForgotPassword")}>
                    <Text style={styles.forgotText}>Password dimenticata?</Text>
                </TouchableOpacity>
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
                <TouchableOpacity style={styles.registerButton} onPress={() => navigation.navigate("Signup")}>
                    <Text style={styles.registerButtonText}>Registrati</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={() => navigation.navigate("Signin")}>
                    <Text style={styles.signInLink}>sign in</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
};

export default LoginPage;
