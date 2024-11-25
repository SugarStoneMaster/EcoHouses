import React, { createContext, useContext, useEffect, useState } from 'react';
import type {PropsWithChildren} from 'react';
import {
  Button,
  Image,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  TouchableOpacity,
  useColorScheme,
  View,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import { createStackNavigator } from '@react-navigation/stack';
import { NavigationContainer } from '@react-navigation/native';
import LoginPage from './components/LoginPage';
import SignupPage from './components/SignupPage';
import ForgotPage from './components/ForgotPage';
import HomePage from './components/HomePage';
import { navStyles } from './styling/navigation';
import UserContext from './contexts/UserContext';
import UserApi from './api/UserApi';
import ChatPage from './components/ChatPage';
import ChatContext from './contexts/ChatContext';
import ProfilePage from './components/ProfilePage';
import SettingsScreen from './components/SettingsScreen';
import SpecialistaPage from './components/SpecialistaPage';
import AddSpecialistaPage from './components/AddSpecialistaPage';
import ChatInfoPage from './components/ChatInfoPage';
import DoubleChatListPage from './components/DoubleChatListPage';
import ReportList from './components/ReportList';
import PrivacyPolicy from './components/PrivacyPolicy';
import TermsService from './components/TermsService';

const Stack = createStackNavigator();

function App(): JSX.Element {
  const [userSession, setUserSession] = useState({isSignedIn: false});
  const [chatContext, setChatContext] = useState(null);
  const userContext = useContext(UserContext);

  async function loadUserSession() {
    try{
      let user = await UserApi.retrieveUserSession();
      console.log("l'user è ", user);
      
      if(user.isSignedIn){
        let newToken = await UserApi.checkTokenValidity({userSession: user, setUserSession: setUserSession});
        if(newToken !== null){
            user = newToken;
            UserApi.storeUserSession(user);
        }
      }
      setUserSession(user);

    } catch(error:any) {
      if(error.message === UserApi.TOKEN_NOT_VALID){
        console.log("User dal local storage con token scaduto");
        setUserSession({isSignedIn: false});
        UserApi.removeUserSession();
      }
      console.log("Errore: ", error);
      
    }
  }

  useEffect(() => {    
    loadUserSession();
    console.log(userContext);
    
  }, [])

  const CustomBackButton = () => (
    <TouchableOpacity onPress={() => { /* your back action */ }}>
      <Text style={{ fontSize: 18, color: 'black' }}>{'<'}</Text>
    </TouchableOpacity>
  );

  


  return (
    <NavigationContainer>
      <UserContext.Provider value={{userSession, setUserSession}}>
          <ChatContext.Provider value={{chatContext, setChatContext}}>
            <Stack.Navigator
              screenOptions={{
                headerStyle: navStyles.headerStyle,
                headerBackImage: () => (
                  <Image
                    style={{ width: 30, height: 30 }}
                    source={require('./images/back_button.png')}
                  />
                ),
                headerBackTitleVisible: false
              }}
            >
              {userSession?.isSignedIn ?
                <>
                  <Stack.Screen options={{headerShown: false}} name="HomePage" component={HomePage}/>
                  <Stack.Screen options={{headerShown: true, headerTitle: "Impostazioni"}} name="Settings" component={SettingsScreen} />
                  <Stack.Screen options={{headerShown: true, headerTitle: "Modifica profilo"}}  name="ProfilePage" component={ProfilePage} />
                  <Stack.Screen options={{headerShown: true, headerTitle: "Informativa sulla privacy"}}  name="PrivacyPolicy" component={PrivacyPolicy} />
                  <Stack.Screen options={{headerShown: true, headerTitle: "Termini di servizio"}}  name="TermsService" component={TermsService} />
                  <Stack.Screen options={{headerShown: true, headerTitle: "I Miei Referti"}} name="ReportList" component={ReportList} />
                  <Stack.Screen options={{headerShown: true}} name="ChatPage" component={ChatPage} />
                  <Stack.Screen options={{headerShown: true}} name="ChatInfoPage" component={ChatInfoPage} />
                  <Stack.Screen options={{headerShown: true}} name="SpecialistaPage" component={SpecialistaPage} />
                  {userSession?.user.ruolo !== "paziente" ? 
                    <Stack.Screen options={{headerShown: true, headerTitle:"Chat"}} name="DoubleChatListPage" component={DoubleChatListPage} />
                    :
                    <></>
                  }
                  {userSession?.user.ruolo === "admin" ? 
                    <Stack.Screen options={{headerShown: true}} name="AddSpecialista" component={AddSpecialistaPage} />
                    :
                    <></>
                  }
                </>
                : 
                <>
                  <Stack.Screen options={{headerShown: false}} name="Login" component={LoginPage}/>
                  <Stack.Screen options={{headerShown: false}} name="Signup" component={SignupPage}/>
                  <Stack.Screen options={{headerShown: false}} name="ForgotPassword" component={ForgotPage}/>
                </>
              }
            </Stack.Navigator>
          </ChatContext.Provider>
      </UserContext.Provider>
    </NavigationContainer>
  );
}

export default App;
