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

authAPI.requestOTP = async function (email) {
    return axiosClient.post("/auth/forgot-password/request-otp", email);
};

authAPI.confirmOTP = async function (infor) {
    return axiosClient.post("/auth/forgot-password/confirm", infor);
}

export default authAPI;
