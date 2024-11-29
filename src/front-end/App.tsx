import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import SignupPage from './components/SignupPage'; // Importa la SignupPage

const Stack = createStackNavigator();

function App(): JSX.Element {
  return (
      <NavigationContainer>
        <Stack.Navigator>
          {/* Rimuovi il resto delle pagine e mantieni solo la SignupPage */}
          <Stack.Screen
              name="Signup"
              component={SignupPage}
              options={{ headerShown: false }} // Disabilita l'header per questa schermata
          />
        </Stack.Navigator>
      </NavigationContainer>
  );
}

export default App;
