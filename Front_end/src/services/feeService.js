import axios from "axios";

const API_BASE = "http://localhost:8080/api";

// Tạo instance axios có sẵn token
const api = axios.create({
  baseURL: API_BASE,
});

// Thêm interceptor để tự động gắn token cho mọi request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// FeePayment APIs
export const getFeePayments = async () => {
  const res = await api.get("/fee-payments/");
  return res.data;
};

export const createFeePayment = async (data) => {
  const res = await api.post("/fee-payments", data);
  return res.data;
};

export const updateFeePayment = async (id, data) => {
  const res = await api.put(`/fee-payments/${id}`, data);
  return res.data;
};

export const deleteFeePayment = async (id) => {
  const res = await api.delete(`/fee-payments/${id}`);
  return res.data;
};

// Fee APIs
export const getFees = async () => {
  const res = await api.get("/fees");
  return res.data;
};

// Thống kê APIs
export const getMonthlyRevenue = async ({ year, month }) => {
  const res = await api.get("/statistics/monthly-revenue", { params: { year, month } });
  return res.data;
};

export const getFeeSummary = async ({ feeId, year, month }) => {
  const res = await api.get("/statistics/fee-summary", { params: { feeId, year, month } });
  return res.data;
};

export const getVoluntarySummary = async ({ year, month }) => {
  const res = await api.get("/statistics/voluntary-summary", { params: { year, month } });
  return res.data;
};

export const getResidentsCount = async () => {
  const res = await api.get("/statistics/residents");
  return res.data;
};
