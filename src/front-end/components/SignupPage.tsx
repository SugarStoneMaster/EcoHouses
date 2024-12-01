import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity } from 'react-native';
import CheckBox from "@react-native-community/checkbox";
import Icon from 'react-native-vector-icons/FontAwesome';
import SignupPageStyles from '../styling/SignupPageStyles'; // Import degli stili corretti

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
            {/* Elementi decorativi */}
            <View style={{ position: 'absolute', top: -50, left: -50, width: 200, height: 200, backgroundColor: '#CDE8D9', borderRadius: 100, zIndex: -1 }} />
            <View style={{ position: 'absolute', top: 100, right: -70, width: 150, height: 150, backgroundColor: '#A8D5BA', borderRadius: 75, zIndex: -1 }} />

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
                    onValueChange={setIsManager}
                    boxType="circle"
                />
                <Text style={SignupPageStyles.checkboxLabel}>Sono il gestore della mia abitazione</Text>
            </View>

            {/* Bottone Registrati */}
            <TouchableOpacity style={SignupPageStyles.button} onPress={handleSignup}>
                <Text style={SignupPageStyles.buttonText}>Registrati</Text>
            </TouchableOpacity>
        </View>
    );
};

export default SignupPage;
