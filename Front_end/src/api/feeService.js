import axiosClient from "./axiosClient";  // client đã cấu hình sẵn

// ---------------- FeePayment APIs ----------------
export const getFeePayments = async () => {
  const res = await axiosClient.get("/fee-payments/");
  return res.data;
};

export const createFeePayment = async (data) => {
  const res = await axiosClient.post("/fee-payments", data);
  return res.data;
};

export const updateFeePayment = async (id, data) => {
  const res = await axiosClient.put(`/fee-payments/${id}`, data);
  return res.data;
};

export const deleteFeePayment = async (id) => {
  const res = await axiosClient.delete(`/fee-payments/${id}`);
  return res.data;
};

// Get FeePayment by ID
export const getFeePaymentById = async (id) => {
  const res = await axiosClient.get(`/fee-payments/search/${id}`);
  return res.data;
};

// Search FeePayments
export const searchFeePayments = async (params) => {
  const res = await axiosClient.get("/fee-payments/search", { params });
  return res.data;
};

// ---------------- Fee APIs ----------------
export const getFees = async () => {
  const res = await axiosClient.get("/fees");
  return res.data;
};

// Get Fee by ID
export const getFeeById = async (id) => {
  const res = await axiosClient.get(`/fees/id/${id}`);
  return res.data;
};

// Get Fee by Type
export const getFeeByType = async (type) => {
  const res = await axiosClient.get(`/fees/type/${type}`);
  return res.data;
};

// Create Fee
export const createFee = async (data) => {
  const res = await axiosClient.post("/fees", data);
  return res.data;
};

// Update Fee by ID
export const updateFeeById = async (id, data) => {
  const res = await axiosClient.put(`/fees/id/${id}`, data);
  return res.data;
};

// Update Fee by Type
export const updateFeeByType = async (type, data) => {
  const res = await axiosClient.put(`/fees/type/${type}`, data);
  return res.data;
};

// Delete Fee
export const deleteFee = async (id) => {
  const res = await axiosClient.delete(`/fees/${id}`);
  return res.data;
};

// ---------------- Statistics APIs ----------------
export const getMonthlyRevenue = async ({ year, month }) => {
  const res = await axiosClient.get("/statistics/monthly-revenue", { params: { year, month } });
  return res.data;
};

export const getFeeSummary = async ({ feeId, year, month }) => {
  const res = await axiosClient.get("/statistics/fee-summary", { params: { feeId, year, month } });
  return res.data;
};

export const getVoluntarySummary = async ({ year, month }) => {
  const res = await axiosClient.get("/statistics/voluntary-summary", { params: { year, month } });
  return res.data;
};

export const getResidentsCount = async () => {
  const res = await axiosClient.get("/statistics/residents");
  return res.data;
};
