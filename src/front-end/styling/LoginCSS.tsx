import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#FFFFFF',
        alignItems: 'center',
        padding: 20,
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#000000',
        marginTop: 40,
        marginBottom: 20,
        textAlign: 'center',
    },
    input: {
        width: '90%',
        backgroundColor: '#CFE3CC',
        borderRadius: 15,
        padding: 10,
        marginBottom: 15,
        flexDirection: 'row',
        alignItems: 'center',
    },
    inputIcon: {
        marginRight: 10,
    },
    inputText: {
        fontSize: 16,
        color: '#000000',
        flex: 1,
    },
    loginButton: {
        width: '90%',
        backgroundColor: '#B3D5F2',
        borderRadius: 15,
        paddingVertical: 12,
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 5,
        marginBottom: 25,
    },
    loginButtonText: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#000000',
    },
    registerContainer: {
        alignItems: 'center',
        marginTop: 30,
    },
    registerText: {
        fontSize: 16,
        color: '#000000',
        marginBottom: 10,
    },
    registerButton: {
        width: '95%',
        backgroundColor: '#B3D5F2',
        borderRadius: 15,
        paddingVertical: 12,
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 5,
        marginBottom: 10,
    },
    registerButtonText: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#000000',
    },
});
