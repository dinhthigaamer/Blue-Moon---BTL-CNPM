import axios from "axios";

const axiosClient = axios.create({
    baseURL: "http://localhost:8080/api", // URL backend
    headers: {
        "Content-Type": "application/json"
    }
});

// 👉 Tự động gắn token vào mọi request
axiosClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("accessToken");
        // hoặc sessionStorage

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => Promise.reject(error)
);

export default axiosClient;
