import axiosClient from "./axiosClient"

let residentAPI = {};

residentAPI.createRes = async function (resident) {
    return axiosClient.post("/residents", resident);
};

residentAPI.getResident = async function (params = null) {
    return axiosClient.get("/residents", { params });
};

residentAPI.getDetailById = async function (id) {
    return axiosClient.get(`/residents/${id}`);
};

residentAPI.update = async function (resident) {
    return axiosClient.get(`/residents/${resident.id}`, resident);
};

export default residentAPI;