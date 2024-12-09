import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import LoginPage from './components/LoginPage'; // Schermata di Login
import SignupPage from './components/SignupPage'; // Schermata di Registrazione
import Dashboard from './components/Dashboard'; // Dashboard
import IotPage from './components/IotPage'; // Schermata di Dispositivi IoT

const Stack = createStackNavigator();

function App(): JSX.Element {
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Login">
                {/* Schermata di Login */}
                <Stack.Screen
                    name="Login"
                    component={LoginPage}
                    options={{ headerShown: false }} // Disabilita l'header per la schermata di login
                />

                {/* Schermata di Signup */}
                <Stack.Screen
                    name="Signup"
                    component={SignupPage}
                    options={{ headerShown: true, title: 'Registrati' }} // Mostra l'header con il titolo "Registrati"
                />

                {/* Schermata di IoT */}
                <Stack.Screen
                    name="IotPage"
                    component={IotPage}
                    options={{ headerShown: true, title: 'Dispositivi IoT' }} // Mostra l'header con il titolo "Dispositivi IoT"
                />

                {/* Schermata della Dashboard */}
                <Stack.Screen
                    name="Dashboard"
                    component={Dashboard}
                    options={{
                        headerShown: true,
                        title: 'Dashboard', // Titolo mostrato nell'header
                        headerStyle: { backgroundColor: '#007bff' }, // Colore di sfondo dell'header
                        headerTintColor: '#fff', // Colore del testo dell'header
                        headerTitleStyle: { fontWeight: 'bold' }, // Stile del titolo
                    }}
                />
            </Stack.Navigator>
        </NavigationContainer>
    );
}

export default App;
