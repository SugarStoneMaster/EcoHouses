import EncryptedStorage from "react-native-encrypted-storage";
import apiEndpoint from "../config/apiEndpointConfig";


class UserApi {

    static async retrieveUserSession() {
        try {
            const session = await EncryptedStorage.getItem("user_session");
            let userSession;
            if (session !== undefined && session !== null) {
                let user = JSON.parse(session);
                if(user.isSignedIn)
                    if (this.checkTokenValidity())
                        userSession = user;
                    else
                        userSession = {isSignedIn: false};
            }
            else {
                userSession = {isSignedIn: false};
            }
            return userSession;
        } catch (error) {
            console.log(error);
        }
    }

    static async storeUserSession(session: any) {
        try {
            await EncryptedStorage.setItem(
                "user_session",
                JSON.stringify(session)
            );

        } catch (error) {
            console.log(error);
        }
    }

    static async removeUserSession() {
        try {
            await EncryptedStorage.removeItem("user_session");
        } catch (error) {
            console.log(error);
        }
    }

    static async login(email:string, password:string) {
        let res = await fetch('http://' + apiEndpoint + ':8080/api/auth/authenticate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: email,
                    password: password,
                }),
            }
        )
        let json = await res.json();
        //let session = Object.assign({"isSignedIn": true},json);
        let session = {"isSignedIn": true, ...json};
        console.log("login ", session);
        await this.storeUserSession(session)
        return session;
    }

    static async signup(user:any) {
        if(!UserApi.areAllValid(user))
            throw new Error("User is not valid");
        let res = await fetch('http://' + apiEndpoint + ':8080/api/auth/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user),
            }
        )
        if(!res.ok){
            console.log(res);
            throw new Error("Response not ok");
        }
        let json = await res.json();
        return true;
    }

    static async resetPassword(email:string){
        let res = await fetch('http://' + apiEndpoint + ':8080/api/userManage/resetPassword?email=' + email, {
                method: 'GET'
            }
        )
        return res;
    }

    //TO-DO
    static checkTokenValidity() {
        return true;
    }


    // Registration Validation
    // Returns false if there isn't an error, true if there is an error
    static validate = (text:string, type:string) => {
        let isError = false;
        if(type === "email"){
            const emailRegex = new RegExp("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
            isError = !emailRegex.test(text);
            console.log("email", isError);
            return isError;
        }
        else if(type === "password"){
            const passwordRegex = new RegExp("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$");
            isError = !passwordRegex.test(text);
            console.log("password", isError);
            return isError;
        }
        else if(type === "name"){
            const nameRegex = new RegExp("^([^0-9]*).{1,}$");
            isError = !nameRegex.test(text);
            console.log("name", isError);
            return isError;
        }
        else if(type === "surname"){
            const surnameRegex = new RegExp("^([^0-9]*).{1,}$");
            isError = !surnameRegex.test(text);
            console.log("surname", isError);
            return isError;
        }
        else if(type === "number"){
            const numberRegex = new RegExp("^\\+39\\d{9,10}$");
            isError = !numberRegex.test(text);
            console.log("number", isError);
            return isError;
        }
        else if(type === "sex"){
            const sexRegex = new RegExp("^(M|F){1}$");
            isError = !sexRegex.test(text);
            console.log("sex", isError);
            console.log("sex ", text);
            return isError;
        }
        else if(type === "fiscalCode"){
            const fiscalCodeRegex = new RegExp("^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$");
            isError = !fiscalCodeRegex.test(text);
            console.log("fiscalCode", isError);
            return isError;
        }
        else return false;
    }

    static areAllValid = (user:any) => {
        return !UserApi.validate(user.email, "email") && !UserApi.validate(user.password, "password") && !UserApi.validate(user.nome, "name") && !UserApi.validate(user.cognome, "surname") && !UserApi.validate(user.sesso, "sex") && !UserApi.validate(user.codiceFiscale, "fiscalCode") && !UserApi.validate(user.telefono, "number")
    }

}

export default UserApi;