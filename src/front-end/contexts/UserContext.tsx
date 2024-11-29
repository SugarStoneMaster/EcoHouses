import { createContext } from 'react';

// Definizione del contesto per gestire la sessione utente
const UserContext = createContext({
    user: null,
    setUserSession: () => {} // Placeholder per evitare errori
});

export default UserContext;
