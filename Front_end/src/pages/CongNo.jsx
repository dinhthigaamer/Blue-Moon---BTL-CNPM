import React, { useState, useEffect } from "react";
import { getOutstandingFees } from "../api/feeService";

export default function CongNo() {
  const [outstandingFees, setOutstandingFees] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchOutstandingFees = async () => {
      setLoading(true);
      try {
        const data = await getOutstandingFees(); // gọi API mới
        setOutstandingFees(data);
      } catch (err) {
        console.error("Lỗi khi thống kê công nợ:", err);
        alert("Không thể lấy dữ liệu công nợ. Vui lòng thử lại sau.");
      } finally {
        setLoading(false);
      }
    };

    fetchOutstandingFees();
  }, []);

  const totalDebt = outstandingFees.reduce(
    (sum, item) => sum + item.totalOutstandingAmount,
    0
  );

  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <h1 className="text-2xl font-bold mb-6 text-teal-700">Thống kê công nợ</h1>

      {loading ? (
        <p>Đang tải dữ liệu...</p>
      ) : (
        <div className="bg-white shadow rounded-lg p-6">
          <table className="w-full text-left border">
            <thead>
              <tr className="border-b">
                <th className="py-2 px-3">Phòng</th>
                <th className="py-2 px-3">Tổng số nợ</th>
              </tr>
            </thead>
            <tbody>
              {outstandingFees.length > 0 ? (
                outstandingFees.map((item, idx) => (
                  <tr key={idx} className="border-b">
                    <td className="py-2 px-3">{item.roomNumber}</td>
                    <td className="py-2 px-3 text-red-600">
                      {item.totalOutstandingAmount.toLocaleString()} đ
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="2" className="py-4 text-center text-gray-500">
                    Không có dữ liệu công nợ
                  </td>
                </tr>
              )}
            </tbody>
            {outstandingFees.length > 0 && (
              <tfoot>
                <tr className="border-t font-semibold">
                  <td className="py-2 px-3 text-right">Tổng cộng:</td>
                  <td className="py-2 px-3 text-red-700">
                    {totalDebt.toLocaleString()} đ
                  </td>
                </tr>
              </tfoot>
            )}
          </table>
        </div>
      )}
    </div>
  );
}
