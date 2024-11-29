// Definizione del tipo di ritorno per il metodo `login`
interface LoginResponse {
    isSignedIn: boolean;
    user: {
        id: number;
        name: string;
        email: string;
    };
}

const UserApi = {
    /**
     * Simulazione della funzione di login.
     *
     * @param {string} usernameOrEmail - L'email o il nome utente.
     * @param {string} password - La password dell'utente.
     * @returns {Promise<LoginResponse>} - Oggetto contenente informazioni sulla sessione utente.
     */
    login: async (usernameOrEmail: string, password: string): Promise<LoginResponse> => {
        // Simula una richiesta API al server
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                if (usernameOrEmail === 'test' && password === 'password') {
                    resolve({
                        isSignedIn: true,
                        user: {
                            id: 1,
                            name: 'Test User',
                            email: 'test@example.com',
                        },
                    });
                } else {
                    reject(new Error('Credenziali non valide'));
                }
            }, 1000); // Simula un ritardo di 1 secondo
        });
    },
};

export default UserApi;
