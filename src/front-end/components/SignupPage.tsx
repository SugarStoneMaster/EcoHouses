import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, CheckBox } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';

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
        <View style={styles.container}>
        <Text style={styles.title}>Benvenuto su EcoHouses!</Text>
    <View style={styles.inputContainer}>
    <Icon name="user" size={20} color="black" style={styles.icon} />
    <TextInput
    style={styles.input}
    placeholder="Nome utente"
    placeholderTextColor="black"
    value={username}
    onChangeText={setUsername}
    />
    </View>
    <View style={styles.inputContainer}>
    <Icon name="envelope" size={20} color="black" style={styles.icon} />
    <TextInput
    style={styles.input}
    placeholder="Email"
    placeholderTextColor="black"
    keyboardType="email-address"
    value={email}
    onChangeText={setEmail}
    />
    </View>
    <View style={styles.inputContainer}>
    <Icon name="lock" size={20} color="black" style={styles.icon} />
    <TextInput
    style={styles.input}
    placeholder="Password"
    placeholderTextColor="black"
    secureTextEntry
    value={password}
    onChangeText={setPassword}
    />
    </View>
    <View style={styles.inputContainer}>
    <Icon name="lock" size={20} color="black" style={styles.icon} />
    <TextInput
    style={styles.input}
    placeholder="Conferma password"
    placeholderTextColor="black"
    secureTextEntry
    value={confirmPassword}
    onChangeText={setConfirmPassword}
    />
    </View>
    <View style={styles.checkboxContainer}>
    <CheckBox value={isManager} onValueChange={setIsManager} />
    <Text style={styles.checkboxLabel}>Sono il gestore della mia abitazione</Text>
    </View>
    <TouchableOpacity style={styles.button} onPress={handleSignup}>
    <Text style={styles.buttonText}>Registrati</Text>
        </TouchableOpacity>
        </View>
);
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 20,
        backgroundColor: '#ffffff',
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 30,
    },
    inputContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#d3e7d6',
        borderRadius: 25,
        marginBottom: 15,
        paddingHorizontal: 10,
        height: 50,
        width: '100%',
    },
    icon: {
        marginRight: 10,
    },
    input: {
        flex: 1,
        fontSize: 16,
        color: 'black',
    },
    checkboxContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 20,
    },
    checkboxLabel: {
        marginLeft: 8,
        fontSize: 14,
        color: 'black',
    },
    button: {
        backgroundColor: '#cde6fb',
        borderRadius: 25,
        height: 50,
        width: '100%',
        justifyContent: 'center',
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5,
    },
    buttonText: {
        color: 'black',
        fontSize: 16,
        fontWeight: 'bold',
    },
});

export default SignupPage;
