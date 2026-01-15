import React, { useEffect, useState } from "react";
import { getFees, createFee, updateFeeById, deleteFee } from "../api/feeService";

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

// Input tái sử dụng
function InputField({ label, type = "text", value, onChange }) {
  return (
    <div>
      <label className="block font-medium mb-1">{label}</label>
      <input
        type={type}
        className="border px-3 py-2 w-full rounded"
        value={value}
        onChange={onChange}
      />
    </div>
  );
}

export default function QuanLyLoaiPhi() {
  const [fees, setFees] = useState([]);
  const [newFee, setNewFee] = useState({
    name: "",
    type: "OPTIONAL",
    defaultAmount: "",
    pricePerUnit: "",
    note: ""
  });
  const [editingFee, setEditingFee] = useState(null);

  const loadFees = async () => {
    const res = await getFees();
    if (res.success) setFees(res.data);
    else alert("Lỗi khi tải loại phí: " + res.message);
  };

  useEffect(() => {
    loadFees();
  }, []);

  // Tạo loại phí
  const handleCreateFee = async (e) => {
    e.preventDefault();
    const payload = {
      name: newFee.name,
      type: newFee.type,
      defaultAmount: newFee.defaultAmount || null,
      pricePerUnit: newFee.pricePerUnit || null,
      note: newFee.note
    };
    const res = await createFee(payload);
    if (res.success) {
      alert("Tạo loại phí thành công!");
      setNewFee({ name: "", type: "OPTIONAL", defaultAmount: "", pricePerUnit: "", note: "" });
      loadFees();
    } else {
      alert("Lỗi khi tạo loại phí: " + res.message);
    }
  };

  // Cập nhật loại phí
  const handleUpdateFee = async (e) => {
    e.preventDefault();
    const payload = {
      name: editingFee.name,
      defaultAmount: editingFee.defaultAmount || null,
      pricePerUnit: editingFee.pricePerUnit || null,
      note: editingFee.note
    };
    const res = await updateFeeById(editingFee.id, payload);
    if (res.success) {
      alert("Cập nhật loại phí thành công!");
      setEditingFee(null);
      loadFees();
    } else {
      alert("Lỗi khi cập nhật: " + res.message);
    }
  };

  // Xóa loại phí
  const handleDeleteFee = async (id) => {
    if (window.confirm("Bạn có chắc muốn xóa loại phí này?")) {
      const res = await deleteFee(id);
      if (res.success) {
        alert("Xóa loại phí thành công!");
        loadFees();
      } else {
        alert("Lỗi khi xóa: " + res.message);
      }
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Quản lý loại phí</h1>

      {/* Form tạo mới */}
      <form className="grid grid-cols-2 gap-4 mb-6" onSubmit={handleCreateFee}>
        <InputField
          label="Tên loại phí"
          value={newFee.name}
          onChange={(e) => setNewFee({ ...newFee, name: e.target.value })}
        />
        <div>
          <label className="block font-medium mb-1">Loại phí</label>
          <select
            className="border px-3 py-2 w-full rounded"
            value={newFee.type}
            onChange={(e) => setNewFee({ ...newFee, type: e.target.value })}
          >
            <option value="SERVICE_FEE">Phí dịch vụ (diện tích)</option> <option value="GUI_XE_MAY">Phí gửi xe máy</option> <option value="GUI_XE_O_TO">Phí gửi ô tô</option> <option value="ELECTRICITY">Phí điện</option> <option value="WATER">Phí nước</option> <option value="MANAGEMENT_FEE">Phí quản lý</option> <option value="OPTIONAL">Tự nguyện</option>
          </select>
        </div>
        <InputField
          label="Số tiền cố định"
          type="number"
          value={newFee.defaultAmount}
          onChange={(e) => setNewFee({ ...newFee, defaultAmount: e.target.value })}
        />
        <InputField
          label="Giá trên đơn vị"
          type="number"
          value={newFee.pricePerUnit}
          onChange={(e) => setNewFee({ ...newFee, pricePerUnit: e.target.value })}
        />
        <InputField
          label="Ghi chú"
          value={newFee.note}
          onChange={(e) => setNewFee({ ...newFee, note: e.target.value })}
        />
        <div className="col-span-2 flex justify-end gap-2">
          <Button type="submit">Tạo mới</Button>
        </div>
      </form>

      {/* Danh sách loại phí */}
      <h2 className="text-lg font-semibold mb-2">Danh sách loại phí</h2>
      <ul className="divide-y border rounded">
        {fees.map((f) => (
          <li key={f.id} className="p-2 flex justify-between items-center">
            <div>
              <span className="font-medium">{f.name}</span> — <span>{f.type}</span>
              {f.defaultAmount != null && <span> | Cố định: {f.defaultAmount}</span>}
              {f.pricePerUnit != null && <span> | Đơn giá: {f.pricePerUnit}</span>}
              {f.note && <span> | Ghi chú: {f.note}</span>}
            </div>
            <div className="space-x-2">
              <Button onClick={() => setEditingFee(f)}>Sửa</Button>
              <Button onClick={() => handleDeleteFee(f.id)}>Xóa</Button>
            </div>
          </li>
        ))}
        {fees.length === 0 && <li className="p-2 text-gray-500">Chưa có loại phí nào.</li>}
      </ul>

      {/* Form sửa loại phí */}
      {editingFee && (
        <div className="mt-6 border p-4 rounded bg-gray-50">
          <h3 className="font-semibold mb-2">Sửa loại phí</h3>
          <form className="space-y-2" onSubmit={handleUpdateFee}>
            <InputField
              label="Tên loại phí"
              value={editingFee.name}
              onChange={(e) => setEditingFee({ ...editingFee, name: e.target.value })}
            />
            <InputField
              label="Số tiền cố định"
              type="number"
              value={editingFee.defaultAmount || ""}
              onChange={(e) => setEditingFee({ ...editingFee, defaultAmount: e.target.value })}
            />
            <InputField
              label="Giá trên đơn vị"
              type="number"
              value={editingFee.pricePerUnit || ""}
              onChange={(e) => setEditingFee({ ...editingFee, pricePerUnit: e.target.value })}
            />
            <InputField
              label="Ghi chú"
              value={editingFee.note || ""}
              onChange={(e) => setEditingFee({ ...editingFee, note: e.target.value })}
            />
            <div className="flex gap-2">
              <Button type="submit">Lưu</Button>
              <Button type="button" onClick={() => setEditingFee(null)}>Hủy</Button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
}
