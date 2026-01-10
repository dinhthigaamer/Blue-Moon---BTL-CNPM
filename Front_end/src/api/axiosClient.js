import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: { "Content-Type": "application/json" }
});

// Tự động gắn token vào mọi request
axiosClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken");
    if (token && token !== "undefined" && token !== "null") {
      config.headers.Authorization = `Bearer ${token}`;
    } else {
      delete config.headers.Authorization; // tránh gắn Bearer undefined
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default axiosClient;
