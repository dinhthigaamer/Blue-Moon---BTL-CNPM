import React from "react";

export default function FeePaymentTable({ payments, onEdit, onDelete }) {
  return (
    <table className="table-auto w-full border-collapse border border-gray-300 mt-4 bg-white">
      <thead>
        <tr className="bg-gray-100">
          <th className="border px-4 py-2">Hộ dân</th>
          <th className="border px-4 py-2">Loại phí</th>
          <th className="border px-4 py-2">Tháng/Năm</th>
          <th className="border px-4 py-2">Số tiền</th>
          <th className="border px-4 py-2">Trạng thái</th>
          <th className="border px-4 py-2">Hành động</th>
        </tr>
      </thead>
      <tbody>
        {payments.map((p) => (
          <tr key={p.id}>
            <td className="border px-4 py-2">{p.householdId}</td>
            <td className="border px-4 py-2">{p.name}</td>
            <td className="border px-4 py-2">{p.billingMonth}/{p.billingYear}</td>
            <td className="border px-4 py-2">{p.amount?.toLocaleString()} đ</td>
            <td className="border px-4 py-2">{p.paid ? "Đã nộp" : "Chưa nộp"}</td>
            <td className="border px-4 py-2 space-x-2">
              <button onClick={() => onEdit(p)} className="bg-yellow-500 text-white px-2 py-1 rounded">Sửa</button>
              <button onClick={() => onDelete(p.id)} className="bg-red-500 text-white px-2 py-1 rounded">Xóa</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
