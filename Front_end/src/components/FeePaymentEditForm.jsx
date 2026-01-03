import React, { useState } from "react";
import { updateFeePayment } from "../services/feeService";

export default function FeePaymentEditForm({ payment, onSuccess, onCancel }) {
  const [form, setForm] = useState({
    ...payment,
    paidDate: payment.paidDate || "",
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({
      ...form,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await updateFeePayment(payment.id, form);
    if (res.success) {
      alert(" Cập nhật phiếu thu thành công!");
      onSuccess();
    } else {
      alert(" Lỗi: " + res.message);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 border p-4 rounded bg-white mt-4">
      <h2 className="text-xl font-semibold">Sửa phiếu thu #{payment.id}</h2>

      <div>
        <label>Sử dụng:</label>
        <input
          name="usageAmount"
          value={form.usageAmount || ""}
          onChange={handleChange}
          className="border px-2 ml-2"
        />
      </div>

      <div>
        <label>Tự nguyện:</label>
        <input
          name="voluntaryAmount"
          value={form.voluntaryAmount || ""}
          onChange={handleChange}
          className="border px-2 ml-2"
        />
      </div>

      <div>
        <label>Đã nộp:</label>
        <input
          type="checkbox"
          name="paid"
          checked={form.paid || false}
          onChange={handleChange}
          className="ml-2"
        />
      </div>

      {form.paid && (
        <div>
          <label>Ngày nộp:</label>
          <input
            type="date"
            name="paidDate"
            value={form.paidDate || ""}
            onChange={handleChange}
            className="border px-2 ml-2"
          />
        </div>
      )}

      <div className="space-x-2">
        <button type="submit" className="bg-green-500 text-white px-4 py-2 rounded">
          Lưu
        </button>
        <button type="button" onClick={onCancel} className="bg-gray-400 text-white px-4 py-2 rounded">
          Hủy
        </button>
      </div>
    </form>
  );
}
