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

const toDateString = (d) => {
  if (!d) return null;

  // Trường hợp backend trả về dạng [yyyy, mm, dd]
  if (Array.isArray(d)) {
    const [y, m, day] = d;
    return `${y}-${String(m).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
  }

  // Đã là string yyyy-MM-dd
  return d;
};
export default function KhoanThu() {
  const [payments, setPayments] = useState([]);
  const [filters, setFilters] = useState({
    roomNumber: "",
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

  // Tim kiem/ loc phieu thu
 const handleSearch = async () => {
  const params = {};

  if (filters.roomNumber) params.roomNumber = filters.roomNumber.trim();
  if (filters.feeId) params.feeId = Number(filters.feeId);
  if (filters.billingYear) params.billingYear = Number(filters.billingYear);
  if (filters.billingMonth) params.billingMonth = Number(filters.billingMonth);

  if (filters.startFrom) params.startFrom = filters.startFrom;
  if (filters.startTo) params.startTo = filters.startTo;
  if (filters.dueFrom) params.dueFrom = filters.dueFrom;
  if (filters.dueTo) params.dueTo = filters.dueTo;

  if (filters.mandatory != null && filters.mandatory !== "")
    params.mandatory = filters.mandatory === "true";

  if (filters.paid != null && filters.paid !== "")
    params.paid = filters.paid === "true";

  console.log("SEARCH PARAMS >>>", params);

  try {
    const data = await searchFeePayments(params);
    setPayments(data);
  } catch (error) {
    console.error("Error searching fee payments:", error);
    alert("Lỗi khi lọc phiếu thu");
  }
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
  const payload = {
    roomNumber: editingPayment.roomNumber,
    feeId: editingPayment.feeId,
    billingYear: editingPayment.billingYear,
    billingMonth: editingPayment.billingMonth,
    mandatory: editingPayment.mandatory,
    paid: editingPayment.paid,

    // BigDecimal
    usageAmount: editingPayment.usageAmount?.toString(),
    voluntaryAmount: editingPayment.voluntaryAmount?.toString(),
    startDate: toDateString(editingPayment.startDate),
    dueDate: toDateString(editingPayment.dueDate),
    paidDate: toDateString(editingPayment.paidDate),
  };

  console.log("PAYLOAD SENT >>>", payload);

  const res = await updateFeePayment(editingPayment.id, payload);

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
    placeholder="Số căn hộ"
    className="border px-2 py-1"
    value={filters.roomNumber || ""}
    onChange={(e) => setFilters({ ...filters, roomNumber: e.target.value })}
  />
  <input
    type="number"
    placeholder="Loại phí ID"
    className="border px-2 py-1"
    value={filters.feeId || ""}
    onChange={(e) => setFilters({ ...filters, feeId: e.target.value })}
  />
  <input
    type="number"
    placeholder="Năm"
    className="border px-2 py-1"
    value={filters.billingYear || ""}
    onChange={(e) => setFilters({ ...filters, billingYear: e.target.value })}
  />
  <input
    type="number"
    placeholder="Tháng"
    className="border px-2 py-1"
    value={filters.billingMonth || ""}
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
        label="Số căn hộ"
        value={editingPayment.roomNumber}
        onChange={(e) =>
          setEditingPayment({
            ...editingPayment,
            roomNumber: e.target.value
          })
        }
      />
      <InputField
        label="Loại phí (Fee ID)"
        type="number"
        value={editingPayment.feeId}
        onChange={(e) =>
          setEditingPayment({
            ...editingPayment,
            feeId: Number(e.target.value)
          })
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
        value={toDateString(editingPayment.startDate) || ""}
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
        value={toDateString(editingPayment.dueDate) || ""}
        onChange={(e) =>
          setEditingPayment({
            ...editingPayment,
            dueDate: e.target.value
          })
        }
      />

      <div>
        <label className="block font-medium mb-1">Bắt buộc</label>
        <input
          type="checkbox"
          checked={editingPayment.mandatory || false}
          onChange={(e) =>
            setEditingPayment({
              ...editingPayment,
              mandatory: e.target.checked
            })
          }
        />
      </div>

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
          value={toDateString(editingPayment.paidDate) || ""}
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
        <Button onClick={() => navigate("/loai_phi")}>Quản lý loại phí</Button>
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
