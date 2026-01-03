import axiosClient from "./axiosClient";

let authAPI = {};
const CONNECTION_ERROR_RESPONSE = { "message": "Cannot send request" };

authAPI.register = async function (user) {
    return axiosClient.post("/auth/register", user);
};

authAPI.login = async function (user) {
    return axiosClient.post("/auth/login", user);
};

authAPI.getMe = async function () {
    return axiosClient.get("/auth/me");
};

authAPI.updateMe = async function (user) {
    return axiosClient.put("/auth/me", user);
};

export default authAPI;
