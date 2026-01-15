import React from "react";
import FeePaymentForm from "../components/FeePaymentForm";
import { useNavigate } from "react-router-dom";

export default function TaoKhoanThu() {
  const navigate = useNavigate();

  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <div className="max-w-4xl mx-auto bg-white shadow rounded-lg p-8">
        <h1 className="text-3xl font-bold mb-6 text-teal-700">Tạo phiếu thu mới</h1>
        <FeePaymentForm onSuccess={() => navigate("/khoan_thu")} />
      </div>
    </div>
  );
}
