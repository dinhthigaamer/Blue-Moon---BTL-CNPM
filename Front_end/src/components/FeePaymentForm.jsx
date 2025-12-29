import React, { useState, useEffect } from "react";
import { createFeePayment, getFees } from "../services/feeService";

export default function FeePaymentForm({ onSuccess }) {
  const [form, setForm] = useState({
    householdId: "",
    feeId: "",
    usageAmount: "",
    voluntaryAmount: "",
    billingYear: new Date().getFullYear(),
    billingMonth: new Date().getMonth() + 1,
    startDate: "",
    dueDate: "",
    mandatory: true,
  });
  const [fees, setFees] = useState([]);

  useEffect(() => {
    getFees().then((res) => {
      if (res.success) setFees(res.data);
    });
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await createFeePayment(form);
    if (res.success) {
      alert("Tạo phiếu thu thành công!");
      onSuccess();
    } else {
      alert("Lỗi: " + res.message);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="border p-6 rounded bg-white max-w-4xl mx-auto">
  <h2 className="text-xl font-semibold mb-6">Tạo phiếu thu mới</h2>

  <div className="grid grid-cols-2 gap-x-8 gap-y-4">
    <div>
      <label className="block mb-1">Mã hộ dân:</label>
      <input name="householdId" value={form.householdId} onChange={handleChange} className="border w-full px-2 py-1" />
    </div>

    <div>
      <label className="block mb-1">Loại phí:</label>
      <select name="feeId" value={form.feeId} onChange={handleChange} className="border w-full px-2 py-1">
        <option value="">--Chọn loại phí--</option>
        {fees.map((f, idx) => (
          <option key={idx} value={idx + 1}>{f.name} ({f.type})</option>
        ))}
      </select>
    </div>

    <div>
      <label className="block mb-1">Mức sử dụng:</label>
      <input name="usageAmount" value={form.usageAmount} onChange={handleChange} className="border w-full px-2 py-1" />
    </div>

    <div>
      <label className="block mb-1">Khoản thu tự nguyện:</label>
      <input name="voluntaryAmount" value={form.voluntaryAmount} onChange={handleChange} className="border w-full px-2 py-1" />
    </div>

    <div>
      <label className="block mb-1">Năm:</label>
      <input name="billingYear" value={form.billingYear} onChange={handleChange} className="border w-full px-2 py-1" />
    </div>

    <div>
      <label className="block mb-1">Tháng:</label>
      <input name="billingMonth" value={form.billingMonth} onChange={handleChange} className="border w-full px-2 py-1" />
    </div>

    <div>
      <label className="block mb-1">Ngày bắt đầu:</label>
      <input type="date" name="startDate" value={form.startDate} onChange={handleChange} className="border w-full px-2 py-1" />
    </div>

    <div>
      <label className="block mb-1">Hạn nộp:</label>
      <input type="date" name="dueDate" value={form.dueDate} onChange={handleChange} className="border w-full px-2 py-1" />
    </div>

    <div className="col-span-2">
      <label className="inline-flex items-center">
        <input type="checkbox" name="mandatory" checked={form.mandatory} onChange={(e) => setForm({ ...form, mandatory: e.target.checked })} />
        <span className="ml-2">Bắt buộc</span>
      </label>
    </div>
  </div>

  <div className="mt-6">
    <button type="submit" className="bg-blue-500 text-white px-6 py-2 rounded">
      Tạo phiếu thu
    </button>
  </div>
</form>
  );
}
