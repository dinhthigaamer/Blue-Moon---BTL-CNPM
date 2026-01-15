import axiosClient from "./axiosClient"

let householdAPI = {};

householdAPI.createHouse = async function (household) {
    return axiosClient.post("/households", household);
};

householdAPI.getHousehold = async function (params = null) {
    return axiosClient.get("/households", { params });
};

householdAPI.getDetailById = async function (id) {
    return axiosClient.get(`/households/${id}`);
};

householdAPI.update = async function (id, household) {
    return axiosClient.put(`/households/${id}`, household);
};

householdAPI.deleteHouse = async function (id) {
    return axiosClient.delete(`/households/${id}`);
};

householdAPI.getDetailByRoomNumber = async function (roomNumber) {
    return axiosClient.get(`/households/by-room/${roomNumber}`);
};

export default householdAPI;