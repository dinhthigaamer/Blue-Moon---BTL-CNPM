import React, { useEffect, useState } from "react";
import { getFees, createFee, updateFee, deleteFee } from "../api/feeService";


export default function LoaiPhi() {
  const [fees, setFees] = useState([]);
  const [newFee, setNewFee] = useState({ name: "", type: "", defaultAmount: "", pricePerUnit: "" });

  const loadFees = () => {
    getFees().then((res) => {
      if (res.success) setFees(res.data);
    });
  };

  useEffect(() => {
    loadFees();
  }, []);

  const handleCreate = async () => {
    const res = await createFee(newFee);
    if (res.success) {
      alert("Tạo loại phí thành công!");
      loadFees();
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Quản lý loại phí</h1>

      <div className="mb-6">
        <input placeholder="Tên phí" className="border px-2 py-1 mr-2" value={newFee.name} onChange={(e) => setNewFee({ ...newFee, name: e.target.value })} />
        <input placeholder="Loại" className="border px-2 py-1 mr-2" value={newFee.type} onChange={(e) => setNewFee({ ...newFee, type: e.target.value })} />
        <button onClick={handleCreate} className="bg-blue-500 text-white px-4 py-2 rounded">Thêm loại phí</button>
      </div>

      <table className="table-auto w-full border-collapse border border-gray-300">
        <thead>
          <tr className="bg-gray-100">
            <th className="border px-4 py-2">Tên phí</th>
            <th className="border px-4 py-2">Loại</th>
            <th className="border px-4 py-2">Hành động</th>
          </tr>
        </thead>
        <tbody>
          {fees.map((f, idx) => (
            <tr key={idx}>
              <td className="border px-4 py-2">{f.name}</td>
              <td className="border px-4 py-2">{f.type}</td>
              <td className="border px-4 py-2">
                <button onClick={() => deleteFee(idx)} className="bg-red-500 text-white px-2 py-1 rounded">Xóa</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
