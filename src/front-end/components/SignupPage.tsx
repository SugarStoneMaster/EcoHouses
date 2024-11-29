import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity } from 'react-native';
import CheckBox from '@react-native-community/checkbox';
import Icon from 'react-native-vector-icons/FontAwesome';
import styles from './SignupPage';
import SignupPageStyles from "../styling/SignupPageStyles"; // Import degli stili separati

const SignupPage = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [isManager, setIsManager] = useState(false);

    const handleSignup = () => {
        // Logica di registrazione
        console.log({
            username,
            email,
            password,
            confirmPassword,
            isManager,
        });
    };

    return (
        <View style={SignupPageStyles.container}>
            <Text style={SignupPageStyles.title}>Benvenuto su EcoHouses!</Text>
            <View style={SignupPageStyles.inputContainer}>
                <Icon name="user" size={20} color="black" style={SignupPageStyles.icon} />
                <TextInput
                    style={SignupPageStyles.input}
                    placeholder="Nome utente"
                    placeholderTextColor="black"
                    value={username}
                    onChangeText={setUsername}
                />
            </View>
            <View style={SignupPageStyles.inputContainer}>
                <Icon name="envelope" size={20} color="black" style={SignupPageStyles.icon} />
                <TextInput
                    style={SignupPageStyles.input}
                    placeholder="Email"
                    placeholderTextColor="black"
                    keyboardType="email-address"
                    value={email}
                    onChangeText={setEmail}
                />
            </View>
            <View style={SignupPageStyles.inputContainer}>
                <Icon name="lock" size={20} color="black" style={SignupPageStyles.icon} />
                <TextInput
                    style={SignupPageStyles.input}
                    placeholder="Password"
                    placeholderTextColor="black"
                    secureTextEntry
                    value={password}
                    onChangeText={setPassword}
                />
            </View>
            <View style={SignupPageStyles.inputContainer}>
                <Icon name="lock" size={20} color="black" style={SignupPageStyles.icon} />
                <TextInput
                    style={SignupPageStyles.input}
                    placeholder="Conferma password"
                    placeholderTextColor="black"
                    secureTextEntry
                    value={confirmPassword}
                    onChangeText={setConfirmPassword}
                />
            </View>
            <View style={SignupPageStyles.checkboxContainer}>
                <CheckBox value={isManager} onValueChange={setIsManager} />
                <Text style={SignupPageStyles.checkboxLabel}>Sono il gestore della mia abitazione</Text>
            </View>
            <TouchableOpacity style={SignupPageStyles.button} onPress={handleSignup}>
                <Text style={SignupPageStyles.buttonText}>Registrati</Text>
            </TouchableOpacity>
        </View>
    );
};

export default SignupPage;
