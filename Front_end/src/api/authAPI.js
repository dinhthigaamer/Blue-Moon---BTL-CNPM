import axios from "axios";
import axiosClient from "./axiosClient";

let authAPI = {};
const CONNECTION_ERROR_RESPONSE = { "message": "Cannot send request" };

authAPI.register = async function (user) {
    return axiosClient.post("/auth/register", user);
};

authAPI.login = async function (user) {
    console.log(user);
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
};

authAPI.getListAccount = async function () {
    return axiosClient.get("/admin/users/pending");
};

authAPI.approveAccount = async function (id) {
    return axiosClient.patch(`/admin/users/${id}/approve`);
};

authAPI.rejectAccount = async function (id) {
    return axiosClient.delete(`/admin/users/${id}/reject`);
};

export default authAPI;
