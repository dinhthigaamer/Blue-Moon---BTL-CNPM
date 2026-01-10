import React, { useState } from "react";
import {
  getMonthlyRevenue,
  getFeeSummary,
  getVoluntarySummary,
  getResidentsCount
} from "../api/feeService";

// Button tái sử dụng
function Button({ children, ...props }) {
  return (
    <button
      {...props}
      className="bg-teal-500 hover:bg-teal-600 text-white px-4 py-2 rounded transition-colors"
    >
      {children}
    </button>
  );
}

export default function TraCuu() {
  const [year, setYear] = useState("");
  const [month, setMonth] = useState("");
  const [feeId, setFeeId] = useState("");
  const [result, setResult] = useState({});

  const handleMonthlyRevenue = async () => {
    const res = await getMonthlyRevenue({ year, month });
    if (res.success) setResult({ ...result, monthlyRevenue: res.data });
  };

  const handleFeeSummary = async () => {
    const res = await getFeeSummary({ feeId, year, month });
    if (res.success) setResult({ ...result, feeSummary: res.data });
  };

  const handleVoluntarySummary = async () => {
    const res = await getVoluntarySummary({ year, month });
    if (res.success) setResult({ ...result, voluntarySummary: res.data });
  };

  const handleResidentsCount = async () => {
    const res = await getResidentsCount();
    if (res.success) setResult({ ...result, residentsCount: res.data });
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Thống kê</h1>

      <div className="grid grid-cols-3 gap-4 mb-6">
        <div>
          <label className="block mb-1">Năm:</label>
          <input
            type="number"
            value={year}
            onChange={(e) => setYear(e.target.value)}
            className="border px-2 py-1 w-full"
          />
        </div>
        <div>
          <label className="block mb-1">Tháng:</label>
          <input
            type="number"
            value={month}
            onChange={(e) => setMonth(e.target.value)}
            className="border px-2 py-1 w-full"
          />
        </div>
        <div>
          <label className="block mb-1">Fee ID:</label>
          <input
            type="number"
            value={feeId}
            onChange={(e) => setFeeId(e.target.value)}
            className="border px-2 py-1 w-full"
          />
        </div>
      </div>

      <div className="space-x-2 mb-6">
        <Button onClick={handleMonthlyRevenue}>Thống kê doanh thu tháng</Button>
        <Button onClick={handleFeeSummary}>Thống kê theo loại phí</Button>
        <Button onClick={handleVoluntarySummary}>Thống kê khoản tự nguyện</Button>
        <Button onClick={handleResidentsCount}>Tổng số cư dân</Button>
      </div>

      {/* Hiển thị kết quả */}
      <div className="space-y-4">
        {result.monthlyRevenue && (
          <div className="border p-4 rounded bg-white">
            <h2 className="font-semibold">Doanh thu tháng</h2>
            <p>
              Năm {result.monthlyRevenue.year}, Tháng {result.monthlyRevenue.month}:{" "}
              {result.monthlyRevenue.total.toLocaleString()} đ
            </p>
          </div>
        )}

        {result.feeSummary && (
          <div className="border p-4 rounded bg-white">
            <h2 className="font-semibold">Theo loại phí</h2>
            <p>
              {result.feeSummary.name} (ID {result.feeSummary.fee_id}) - Tháng{" "}
              {result.feeSummary.month}/{result.feeSummary.year}:{" "}
              {result.feeSummary.total.toLocaleString()} đ
            </p>
          </div>
        )}

        {result.voluntarySummary && (
          <div className="border p-4 rounded bg-white">
            <h2 className="font-semibold">Khoản thu tự nguyện</h2>
            <p>
              Năm {result.voluntarySummary.year}, Tháng {result.voluntarySummary.month}:{" "}
              {result.voluntarySummary.total.toLocaleString()} đ
            </p>
          </div>
        )}

        {result.residentsCount && (
          <div className="border p-4 rounded bg-white">
            <h2 className="font-semibold">Tổng số cư dân</h2>
            <p>{result.residentsCount} người</p>
          </div>
        )}
      </div>
    </div>
  );
}
