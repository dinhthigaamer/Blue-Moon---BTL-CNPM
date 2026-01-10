import React, { useEffect, useState } from "react";
import {
  getFeePayments,
  deleteFeePayment,
  updateFeePayment,
  searchFeePayments,
  getFees,
  createFee
} from "../api/feeService";
import FeePaymentTable from "../components/FeePaymentTable";
import { useNavigate } from "react-router-dom";

export default function KhoanThu() {
  const [payments, setPayments] = useState([]);
  const [filters, setFilters] = useState({
    householdId: "",
    feeId: "",
    billingYear: "",
    billingMonth: ""
  });
  const [editingPayment, setEditingPayment] = useState(null);
  // Quản lý loại phí
  const [isFeeOpen, setIsFeeOpen] = useState(false);
  const [fees, setFees] = useState([]);
  const [editingFee, setEditingFee] = useState(null);
  const [newFee, setNewFee] = useState({
    name: "",
    type: "OPTIONAL",
    defaultAmount: "",
    pricePerUnit: "",
    note: ""
  });

  const navigate = useNavigate();

  // Load danh sách phiếu thu
  const loadPayments = async () => {
    const res = await getFeePayments();
    if (res.success) setPayments(res.data);
    else alert("Lỗi khi tải danh sách phiếu thu: " + res.message);
  };

  useEffect(() => {
    loadPayments();
  }, []);

  // Lọc phiếu thu theo filters
  const handleSearch = async () => {
    const params = {};
    if (filters.householdId) params.householdId = filters.householdId;
    if (filters.feeId) params.feeId = filters.feeId;
    if (filters.billingYear) params.billingYear = filters.billingYear;
    if (filters.billingMonth) params.billingMonth = filters.billingMonth;

    const res = await searchFeePayments(params);
    if (res.success) setPayments(res.data);
    else alert("Lỗi khi lọc phiếu thu: " + res.message);
  };

  // Load loại phí
  const loadFees = async () => {
    const res = await getFees();
    if (res.success) setFees(res.data);
    else alert("Lỗi khi tải loại phí: " + res.message);
  };

  // Xóa phiếu thu
  const handleDelete = async (id) => {
    if (window.confirm("Bạn có chắc chắn muốn xóa phiếu thu này?")) {
      const res = await deleteFeePayment(id);
      if (res.success) {
        alert("Xóa thành công!");
        loadPayments();
      } else {
        alert("Lỗi khi xóa: " + res.message);
      }
    }
  };

  // Cập nhật phiếu thu
  const handleUpdate = async () => {
    const res = await updateFeePayment(editingPayment.id, editingPayment);
    if (res.success) {
      alert("Cập nhật thành công!");
      setEditingPayment(null);
      loadPayments();
    } else {
      alert("Lỗi khi cập nhật: " + res.message);
    }
  };

  // Tạo loại phí mới
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

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Khoản thu</h1>

      {/* Form lọc */}
      <div className="grid grid-cols-5 gap-4 mb-4 items-end">
        <input
          placeholder="Mã hộ dân"
          className="border px-2 py-1"
          value={filters.householdId}
          onChange={(e) => setFilters({ ...filters, householdId: e.target.value })}
        />
        <input
          placeholder="Loại phí ID"
          className="border px-2 py-1"
          value={filters.feeId}
          onChange={(e) => setFilters({ ...filters, feeId: e.target.value })}
        />
        <input
          placeholder="Năm"
          className="border px-2 py-1"
          value={filters.billingYear}
          onChange={(e) => setFilters({ ...filters, billingYear: e.target.value })}
        />
        <input
          placeholder="Tháng"
          className="border px-2 py-1"
          value={filters.billingMonth}
          onChange={(e) => setFilters({ ...filters, billingMonth: e.target.value })}
        />
        <Button onClick={handleSearch}>Lọc</Button>

      </div>

      {/* Bảng danh sách phiếu thu */}
      <FeePaymentTable
        payments={payments}
        onDelete={handleDelete}
        onDetail={(id) => navigate(`/khoan_thu/${id}`)}
        onEdit={(payment) => setEditingPayment(payment)}
      />

      {/* Form chỉnh sửa phiếu thu */}
      {editingPayment && (
        <div className="mt-6 border p-6 rounded bg-white shadow">
          <h2 className="text-xl font-bold mb-4">Chỉnh sửa phiếu thu</h2>

          <div className="grid grid-cols-2 gap-4">
            <InputField
              label="Hộ dân"
              value={editingPayment.householdId}
              onChange={(e) =>
                setEditingPayment({
                  ...editingPayment,
                  householdId: e.target.value
                })
              }
            />
            <InputField
              label="Loại phí"
              value={editingPayment.feeId}
              onChange={(e) =>
                setEditingPayment({ ...editingPayment, feeId: e.target.value })
              }
            />
            <InputField
              label="Tháng"
              type="number"
              value={editingPayment.billingMonth}
              onChange={(e) =>
                setEditingPayment({
                  ...editingPayment,
                  billingMonth: Number(e.target.value)
                })
              }
            />
            <InputField
              label="Năm"
              type="number"
              value={editingPayment.billingYear}
              onChange={(e) =>
                setEditingPayment({
                  ...editingPayment,
                  billingYear: Number(e.target.value)
                })
              }
            />
            <InputField
              label="Mức sử dụng"
              type="number"
              value={editingPayment.usageAmount || ""}
              onChange={(e) =>
                setEditingPayment({
                  ...editingPayment,
                  usageAmount: Number(e.target.value)
                })
              }
            />
            <InputField
              label="Khoản tự nguyện"
              type="number"
              value={editingPayment.voluntaryAmount || ""}
              onChange={(e) =>
                setEditingPayment({
                  ...editingPayment,
                  voluntaryAmount: Number(e.target.value)
                })
              }
            />
            <InputField
              label="Ngày bắt đầu"
              type="date"
              value={editingPayment.startDate || ""}
              onChange={(e) =>
                setEditingPayment({
                  ...editingPayment,
                  startDate: e.target.value
                })
              }
            />
            <InputField
              label="Hạn nộp"
              type="date"
              value={editingPayment.dueDate || ""}
              onChange={(e) =>
                setEditingPayment({
                  ...editingPayment,
                  dueDate: e.target.value
                })
              }
            />
            <div>
              <label className="block font-medium mb-1">Trạng thái</label>
              <select
                className="border px-3 py-2 w-full rounded"
                value={editingPayment.paid ? "true" : "false"}
                onChange={(e) =>
                  setEditingPayment({
                    ...editingPayment,
                    paid: e.target.value === "true"
                  })
                }
              >
                <option value="false">Chưa nộp</option>
                <option value="true">Đã nộp</option>
              </select>
            </div>
            {editingPayment.paid && (
              <InputField
                label="Ngày nộp"
                type="date"
                value={editingPayment.paidDate || ""}
                onChange={(e) =>
                  setEditingPayment({
                    ...editingPayment,
                    paidDate: e.target.value
                  })
                }
              />
            )}
          </div>

          <div className="mt-6 space-x-2">
            <Button onClick={handleUpdate}>Lưu thay đổi</Button>

            <button
              onClick={() => setEditingPayment(null)}
              className="bg-gray-400 text-white px-4 py-2 rounded"
            >
              Hủy
            </button>
          </div>
        </div>
      )}

      {/* Nút tạo khoản thu và quản lý loại phí */}
      <div className="mt-6 space-x-2">
        <Button onClick={() => navigate("/khoan_thu/tao")}>Tạo khoản thu</Button>

        <Button onClick={() => navigate("/loai_phi")}>
  Quản lý loại phí
</Button>


      </div>

    </div>
  );
}

// Component input tái sử dụng
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

// Component button tái sử dụng
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
