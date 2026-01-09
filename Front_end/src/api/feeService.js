import axiosClient from "./axiosClient";  // dùng client đã cấu hình sẵn

// FeePayment APIs
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

// Fee APIs
export const getFees = async () => {
  const res = await axiosClient.get("/fees");
  return res.data;
};

// Thống kê APIs
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
