import React, { useState, useEffect } from "react";
import { getResidentsStatistics } from "../api/feeService"; // gọi API thống kê cư trú

export default function TrangChu() {
  const [residentsCount, setResidentsCount] = useState(0);
  const [householdCount, setHouseholdCount] = useState(0);

  // Thông báo mới nhất
  const [latestNotice, setLatestNotice] = useState("");

  useEffect(() => {
    setLatestNotice("🔔 Chung cư sẽ bảo trì thang máy từ ngày 5/1 đến 7/1.");

    // gọi API lấy thống kê cư trú
    const fetchStatistics = async () => {
      try {
        const res = await getResidentsStatistics();
        if (res.success) {
          setResidentsCount(res.data.residentCount);
          setHouseholdCount(res.data.householdCount);
        } else {
          console.error("API trả về lỗi:", res.message);
        }
      } catch (err) {
        console.error("Error fetching residents statistics:", err);
      }
    };
    fetchStatistics();
  }, []);

  return (
    <div className="p-6">
      {/* Header */}
      <h1 className="text-3xl font-bold mb-6">Welcome to Bluemoon</h1>

      {/* Thông báo */}
      <div className="bg-yellow-100 border-l-4 border-yellow-500 text-yellow-700 p-4 mb-6">
        <p className="font-semibold">Thông báo mới nhất:</p>
        <p>{latestNotice}</p>
      </div>

      {/* Tổng số cư dân và hộ */}
      <div className="grid grid-cols-2 gap-6 mb-8">
        <div className="bg-white shadow rounded-lg p-6 text-center">
          <h2 className="text-xl font-semibold text-teal-600">Tổng số cư dân</h2>
          <p className="text-2xl font-bold">{residentsCount}</p>
        </div>
        <div className="bg-white shadow rounded-lg p-6 text-center">
          <h2 className="text-xl font-semibold text-teal-600">Tổng số hộ dân</h2>
          <p className="text-2xl font-bold">{householdCount}</p>
        </div>
      </div>

      {/* Giới thiệu chung cư */}
      <h2 className="text-2xl font-bold mb-4">Giới thiệu chung cư</h2>
      <div className="grid grid-cols-2 gap-6 bg-gray-100 p-6 rounded shadow">
        {/* Bên trái: ảnh */}
        <img
          src="/images/chungcu1.jpg"
          alt="Chung cư Bluemoon"
          className="w-full h-auto object-contain rounded shadow"
        />

        {/* Bên phải: thông tin */}
        <div className="flex flex-col justify-center">
          <h3 className="text-xl font-semibold mb-2">Chung cư Bluemoon</h3>
          <p className="text-gray-700 mb-2">
            Chung cư BlueMoon tọa lạc ngay ngã tư Văn Phú được khởi công xây dựng năm 2021 và hoàn thành vào 2023. Chung cư được xây dựng trên diện tích 450m2, gồm 30 tầng, tầng 1 làm kiot, 4 tầng đế, 24 tầng nhà ở và 1 tầng penhouse.
          </p>
          
        </div>
      </div>
    </div>
  );
}
