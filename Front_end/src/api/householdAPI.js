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

householdAPI.update = async function (user) {
    return axiosClient.put(`/households/${user.id}`, user);
};

export default householdAPI;