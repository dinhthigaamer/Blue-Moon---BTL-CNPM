import axiosClient from "./axiosClient"

let authAPI = {}

authAPI.register = async function (user) {
    return axiosClient.post("/auth/register", user)
};

authAPI.login = async function (user) {
    return axiosClient.post("/auth/login", user)
};

authAPI.getMe = async function () {
    return axiosClient.get("/auth/me")
};

export default authAPI;
