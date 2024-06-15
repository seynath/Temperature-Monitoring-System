import axios from "axios";

export const ValidateUser = (userData) => {
    return axios.post("http://127.0.0.1:8080/api/v/auth/validate-user", userData);
};