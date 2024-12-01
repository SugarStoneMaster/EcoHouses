import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#FFFFFF",
        alignItems: "center",
        padding: 20,
    },
    title: {
        fontSize: 24,
        fontWeight: "bold",
        color: "#000000",
        marginTop: 40,
        marginBottom: 20,
        textAlign: "center",
    },
    input: {
        width: "90%",
        backgroundColor: "#CFE3CC",
        borderRadius: 15,
        padding: 10,
        marginBottom: 15,
        flexDirection: "row",
        alignItems: "center",
    },
    inputIcon: {
        marginRight: 10,
    },
    inputText: {
        fontSize: 16,
        color: "#000000",
        flex: 1,
    },
    rememberForgotContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
        width: "90%",
        marginBottom: 20,
    },
    rememberText: {
        fontSize: 14,
        color: "#000000",
    },
    forgotText: {
        fontSize: 14,
        color: "#6C757D",
        textDecorationLine: "underline",
    },
    loginButton: {
        width: "90%",
        backgroundColor: "#B3D5F2",
        borderRadius: 15,
        paddingVertical: 12,
        alignItems: "center",
        shadowColor: "#000",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 5,
        marginBottom: 25,
    },
    loginButtonText: {
        fontSize: 18,
        fontWeight: "bold",
        color: "#000000",
    },
    socialLoginContainer: {
        width: "90%",
        marginBottom: 20,
        alignItems: "center",
    },
    socialLoginText: {
        fontSize: 16,
        marginBottom: 15,
        color: "#000000",
    },
    socialIcons: {
        flexDirection: "row",
        justifyContent: "space-between",
        width: "60%",
    },
    socialIcon: {
        width: 50,
        height: 50,
        borderRadius: 25,
        backgroundColor: "#CFE3CC",
        justifyContent: "center",
        alignItems: "center",
    },
    registerContainer: {
        alignItems: "center",
        marginTop: 30,
    },
    registerText: {
        fontSize: 16,
        color: "#000000",
        marginBottom: 10,
    },
    registerButton: {
        width: "95%", // Aumenta la larghezza del pulsante "Registrati"
        backgroundColor: "#B3D5F2",
        borderRadius: 15,
        paddingVertical: 12,
        alignItems: "center",
        shadowColor: "#000",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 5,
        marginBottom: 10,
    },
    registerButtonText: {
        fontSize: 18,
        fontWeight: "bold",
        color: "#000000",
    },
    signInLink: {
        fontSize: 14,
        color: "#A3C8A3",
        textDecorationLine: "underline",
    },
});
