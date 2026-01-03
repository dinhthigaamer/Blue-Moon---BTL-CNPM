import React from "react";
import FeePaymentForm from "../components/FeePaymentForm";
import { useNavigate } from "react-router-dom";

export default function TaoKhoanThu() {
  const navigate = useNavigate();

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Tạo phiếu thu mới</h1>
      <FeePaymentForm onSuccess={() => navigate("/khoan_thu")} />
    </div>
  );
}
