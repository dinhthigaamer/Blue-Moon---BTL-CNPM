import React, { useState, useEffect } from "react";
import {
  getMonthlyRevenue,
  getFeeSummary,
  getVoluntarySummary,
} from "../api/feeService";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
} from "chart.js";
import { Bar } from "react-chartjs-2";
import { useNavigate } from "react-router-dom";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

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

export default function TraCuu() {
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState("");
  const [feeId, setFeeId] = useState("");
  const [result, setResult] = useState({});
  const [monthlyTotals, setMonthlyTotals] = useState([]);
  const navigate = useNavigate();

  // Gọi API cho tất cả 12 tháng khi chọn năm
  useEffect(() => {
    const fetchYearlyRevenue = async () => {
      if (!year) return;
      try {
        const promises = Array.from({ length: 12 }, (_, i) =>
          getMonthlyRevenue({ year: Number(year), month: i + 1 })
        );
        const results = await Promise.all(promises);
        setMonthlyTotals(results.map((r) => r.total));
      } catch (err) {
        console.error("Error fetching yearly revenue:", err);
      }
    };
    fetchYearlyRevenue();
  }, [year]);

  const handleMonthlyRevenue = async () => {
    try {
      const data = await getMonthlyRevenue({
        year: Number(year),
        month: Number(month)
      });
      setResult((prev) => ({ ...prev, monthlyRevenue: data }));
    } catch (err) {
      console.error(err);
      alert("Lỗi khi thống kê doanh thu tháng");
    }
  };

  const handleFeeSummary = async () => {
    try {
      const data = await getFeeSummary({
        feeId: Number(feeId),
        year: Number(year),
        month: Number(month)
      });
      setResult((prev) => ({ ...prev, feeSummary: data }));
    } catch (err) {
      console.error(err);
      alert("Lỗi khi thống kê theo loại phí");
    }
  };

  const handleVoluntarySummary = async () => {
    try {
      const data = await getVoluntarySummary({
        year: Number(year),
        month: Number(month)
      });
      setResult((prev) => ({ ...prev, voluntarySummary: data }));
    } catch (err) {
      console.error(err);
      alert("Lỗi khi thống kê khoản tự nguyện");
    }
  };

  // Dữ liệu biểu đồ
  const chartData = {
    labels: Array.from({ length: 12 }, (_, i) => `Tháng ${i + 1}`),
    datasets: [
      {
        label: "Doanh thu (VNĐ)",
        data: monthlyTotals,
        backgroundColor: "rgba(20, 184, 166, 0.7)",
        borderRadius: 6
      }
    ]
  };

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: { display: false },
      title: { display: true, text: `Tổng doanh thu theo tháng - Năm ${year}` }
    },
    scales: {
      y: {
        ticks: {
          callback: (value) => value.toLocaleString() + " đ"
        }
      }
    }
  };

  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <h1 className="text-2xl font-bold mb-4 text-teal-700">Thống kê</h1>

      {/* Input filter */}
      <div className="grid grid-cols-3 gap-4 mb-6">
        <div>
          <label className="block mb-1">Năm:</label>
          <input
            type="number"
            value={year}
            onChange={(e) => setYear(e.target.value)}
            className="border px-2 py-1 w-full"
          />
        </div>
        <div>
          <label className="block mb-1">Tháng:</label>
          <input
            type="number"
            value={month}
            onChange={(e) => setMonth(e.target.value)}
            className="border px-2 py-1 w-full"
          />
        </div>
        <div>
          <label className="block mb-1">Fee ID:</label>
          <input
            type="number"
            value={feeId}
            onChange={(e) => setFeeId(e.target.value)}
            className="border px-2 py-1 w-full"
          />
        </div>
      </div>

      {/* Action buttons */}
      <div className="space-x-2 mb-6">
        <Button onClick={handleMonthlyRevenue}>Thống kê doanh thu tháng</Button>
        <Button onClick={handleFeeSummary}>Thống kê theo loại phí</Button>
        <Button onClick={handleVoluntarySummary}>Thống kê khoản tự nguyện</Button>
        <Button onClick={() => navigate("/cong-no")}>Thống kê công nợ</Button>
      </div>

      {/* Hiển thị kết quả */}
      <div className="space-y-4 mb-10">
        {result.monthlyRevenue && (
          <div className="border p-4 rounded bg-white">
            <h2 className="font-semibold">Doanh thu tháng</h2>
            <p>
              Năm {result.monthlyRevenue.year}, Tháng {result.monthlyRevenue.month}:{" "}
              {result.monthlyRevenue.total.toLocaleString()} đ
            </p>
          </div>
        )}

        {result.feeSummary && (
          <div className="border p-4 rounded bg-white">
            <h2 className="font-semibold">Theo loại phí</h2>
            <p>
              {result.feeSummary.name} (ID {result.feeSummary.fee_id}) - Tháng{" "}
              {result.feeSummary.month}/{result.feeSummary.year}:{" "}
              {result.feeSummary.total.toLocaleString()} đ
            </p>
          </div>
        )}

        {result.voluntarySummary && (
          <div className="border p-4 rounded bg-white">
            <h2 className="font-semibold">Khoản thu tự nguyện</h2>
            <p>
              Năm {result.voluntarySummary.year}, Tháng {result.voluntarySummary.month}:{" "}
              {result.voluntarySummary.total.toLocaleString()} đ
            </p>
          </div>
        )}
      </div>

      {/* Biểu đồ doanh thu theo tháng */}
      <div className="bg-white shadow rounded-lg p-6">
        <Bar data={chartData} options={chartOptions} />
      </div>
    </div>
  );
}
