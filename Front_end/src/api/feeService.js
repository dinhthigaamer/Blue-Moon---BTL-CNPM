import axiosClient from "./axiosClient";  // client đã cấu hình sẵn

// ---------------- FeePayment APIs ----------------

// Lấy toàn bộ danh sách khoản thu
export const getFeePayments = async () => {
  const res = await axiosClient.get("/fee-payments/");
  return res.data;
};

// Tạo mới khoản thu (data phải có roomNumber thay vì householdId)
export const createFeePayment = async (data) => {
  const res = await axiosClient.post("/fee-payments", data);
  return res.data;
};

// Cập nhật khoản thu theo id (data phải có roomNumber thay vì householdId)
export const updateFeePayment = async (id, data) => {
  const res = await axiosClient.put(`/fee-payments/${id}`, data);
  return res.data;
};

// Xóa khoản thu theo id
export const deleteFeePayment = async (id) => {
  const res = await axiosClient.delete(`/fee-payments/${id}`);
  return res.data;
};

// Lấy khoản thu theo id
export const getFeePaymentById = async (id) => {
  const res = await axiosClient.get(`/fee-payments/search/${id}`);
  return res.data;
};

// Tìm kiếm khoản thu
export const searchFeePayments = async (params) => {
  const res = await axiosClient.get("/fee-payments/search", { params });
  return res.data.data; // nếu backend trả ApiResponse<List<FeePaymentDTO>>
};


// ---------------- Fee APIs ----------------

// Lấy toàn bộ loại phí
export const getFees = async () => {
  const res = await axiosClient.get("/fees");
  return res.data;
};

// Lấy loại phí theo id
export const getFeeById = async (id) => {
  const res = await axiosClient.get(`/fees/id/${id}`);
  return res.data;
};

// Lấy loại phí theo type
export const getFeeByType = async (type) => {
  const res = await axiosClient.get(`/fees/type/${type}`);
  return res.data;
};

// Tạo mới loại phí
export const createFee = async (data) => {
  const res = await axiosClient.post("/fees", data);
  return res.data;
};

// Cập nhật loại phí theo id
export const updateFeeById = async (id, data) => {
  const res = await axiosClient.put(`/fees/id/${id}`, data);
  return res.data;
};

// Cập nhật loại phí theo type
export const updateFeeByType = async (type, data) => {
  const res = await axiosClient.put(`/fees/type/${type}`, data);
  return res.data;
};

// Xóa loại phí theo id
export const deleteFee = async (id) => {
  const res = await axiosClient.delete(`/fees/${id}`);
  return res.data;
};

// ---------------- Statistics APIs ----------------



// Thống kê tổng thu theo tháng (GET + query params)
export const getMonthlyRevenue = async ({ year, month }) => {
  const res = await axiosClient.get("/statistics/monthly-revenue", {
    params: { year, month }
  });
  return res.data.data; // ApiResponse<MonthlyRevenueOutDTO> → lấy data
};

// Thống kê theo từng loại phí (GET + query params)
export const getFeeSummary = async ({ feeId, year, month }) => {
  const res = await axiosClient.get("/statistics/fee-summary", {
    params: { feeId, year, month }
  });
  return res.data.data; // ApiResponse<FeeSummaryOutDTO>
};

// Thống kê khoản thu tự nguyện (GET + query params)
export const getVoluntarySummary = async ({ year, month }) => {
  const res = await axiosClient.get("/statistics/voluntary-summary", {
    params: { year, month }
  });
  return res.data.data; // ApiResponse<VoluntarySummaryOutDTO>
};

// Thống kê cư trú (GET, không có params)
export const getResidentsStatistics = async () => {
  const res = await axiosClient.get("/statistics/residents");
  return res.data; // { success, message, data: { residentCount, householdCount } }
};


export const getOutstandingFees = async () => {
  const res = await axiosClient.get("/fee-payments/outstanding");
  return res.data.data; // [{ roomNumber, totalOutstandingAmount }, ...]
};




