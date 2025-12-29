import React, { useEffect, useState } from "react";
import { getFeePayments, deleteFeePayment } from "../services/feeService";
import FeePaymentTable from "../components/FeePaymentTable";
import { useNavigate } from "react-router-dom";

export default function KhoanThu() {
  const [payments, setPayments] = useState([]);
  const [filters, setFilters] = useState({ householdId: "", feeId: "", billingYear: "", billingMonth: "" });
  const navigate = useNavigate();

  const loadPayments = () => {
    getFeePayments().then((res) => {
      if (res.success) setPayments(res.data);
    });
  };

  useEffect(() => {
    loadPayments();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm("Bạn có chắc chắn muốn xóa phiếu thu này?")) {
      const res = await deleteFeePayment(id);
      if (res.success) {
        alert("Xóa thành công!");
        loadPayments();
      } else {
        alert(" Lỗi: " + res.message);
      }
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Khoản thu</h1>
      
      


      {/* Form lọc */}
      <div className="grid grid-cols-4 gap-4 mb-4">
        <input placeholder="Mã hộ dân" className="border px-2 py-1" value={filters.householdId} onChange={(e) => setFilters({ ...filters, householdId: e.target.value })} />
        <input placeholder="Loại phí ID" className="border px-2 py-1" value={filters.feeId} onChange={(e) => setFilters({ ...filters, feeId: e.target.value })} />
        <input placeholder="Năm" className="border px-2 py-1" value={filters.billingYear} onChange={(e) => setFilters({ ...filters, billingYear: e.target.value })} />
        <input placeholder="Tháng" className="border px-2 py-1" value={filters.billingMonth} onChange={(e) => setFilters({ ...filters, billingMonth: e.target.value })} />
      </div>

      {/* Bảng danh sách phiếu thu */}
      <FeePaymentTable
        payments={payments}
        onDelete={handleDelete}
        onDetail={(id) => navigate(`/khoan_thu/${id}`)}
      />

      {/* Nút tạo khoản thu */}
      <div className="mt-6">
        <button
          onClick={() => navigate("/khoan_thu/tao")}
          className="bg-blue-500 text-white px-4 py-2 rounded"
        >
           Tạo khoản thu
        </button>
      </div>
    </div>
  );
}
