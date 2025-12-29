import React, { useEffect, useState } from "react";
import { getFeePaymentById } from "../services/feeService";
import { useParams } from "react-router-dom";

export default function ChiTietKhoanThu() {
  const { id } = useParams();
  const [payment, setPayment] = useState(null);

  useEffect(() => {
    getFeePaymentById(id).then((res) => {
      if (res.success) setPayment(res.data);
    });
  }, [id]);

  if (!payment) return <div className="p-6">Đang tải...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Chi tiết phiếu thu #{payment.id}</h1>
      <ul className="space-y-2">
        <li>Hộ dân: {payment.householdId}</li>
        <li>Loại phí: {payment.name}</li>
        <li>Tháng/Năm: {payment.billingMonth}/{payment.billingYear}</li>
        <li>Số tiền: {payment.amount?.toLocaleString()} đ</li>
        <li>Trạng thái: {payment.paid ? "Đã nộp" : "Chưa nộp"}</li>
      </ul>
    </div>
  );
}
