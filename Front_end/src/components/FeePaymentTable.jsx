import React from "react";

// Button tái sử dụng
function Button({ children, ...props }) {
  return (
    <button
      {...props}
      className="bg-teal-500 hover:bg-teal-600 text-white px-2 py-1 rounded transition-colors"
    >
      {children}
    </button>
  );
}

export default function FeePaymentTable({ payments, onEdit, onDelete, onDetail }) {
  if (!payments || payments.length === 0) {
    return <p className="text-gray-500 mt-4">Chưa có phiếu thu nào.</p>;
  }

  return (
    <table className="table-auto w-full border-collapse border border-gray-300 mt-4 bg-white">
      <thead>
        <tr className="bg-gray-100">
          <th className="border px-4 py-2">Số căn hộ</th>
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
            {/* Đổi householdId -> roomNumber */}
            <td className="border px-4 py-2">{p.roomNumber}</td>
            <td className="border px-4 py-2">{p.name}</td>
            <td className="border px-4 py-2">
              {p.billingMonth ? p.billingMonth : "?"}/{p.billingYear ? p.billingYear : "?"}
            </td>
            <td className="border px-4 py-2">
              {p.amount ? p.amount.toLocaleString() : 0} đ
            </td>
            <td className="border px-4 py-2">
              {p.paid ? "Đã nộp" : "Chưa nộp"}
            </td>
            <td className="border px-4 py-2 space-x-2">
              {onEdit && (
                <Button onClick={() => onEdit(p)}>
                  Sửa
                </Button>
              )}
              {onDelete && (
                <Button onClick={() => onDelete(p.id)}>
                  Xóa
                </Button>
              )}
              {
              }
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
