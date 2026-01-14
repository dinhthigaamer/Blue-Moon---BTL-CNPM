import React, { useState, useEffect } from "react";
import { getResidentsCount } from "../api/feeService"; // gọi API cư dân

export default function TrangChu() {
  const [residentsCount, setResidentsCount] = useState(0);
  const [roomCount, setRoomCount] = useState(0);

  // Thông báo mới nhất
  const [latestNotice, setLatestNotice] = useState("");

  useEffect(() => {
    setLatestNotice("🔔 Chung cư sẽ bảo trì thang máy từ ngày 5/1 đến 7/1.");

    // gọi API lấy tổng số cư dân và hộ
    const fetchResidents = async () => {
      try {
        const data = await getResidentsCount();
        setResidentsCount(data.residentCount);
        setRoomCount(data.householdCount);
      } catch (err) {
        console.error("Error fetching residents count:", err);
      }
    };
    fetchResidents();
  }, []);

  // Tin tức + ảnh (ảnh local trong public/images)
  const newsItems = [
    {
      title: "Giá chung cư Hà Nội tăng mạnh",
      link: "https://kenh14.vn/chung-cu-moi-o-ha-noi-cang-ra-hang-gia-cang-cao-xuat-hien-du-an-cham-nguong-300-trieu-dong-m2-21525072808403116.chn",
      image: "/images/chungcu1.jpg",
    },
    {
      title: "Chung cư mới ồ ạt ra hàng",
      link: "https://baodautu.vn/batdongsan/ha-noi-chung-cu-moi-o-at-ra-hang-trai-rong-tu-noi-do-ra-ngoai-thanh-d376534.html",
      image: "/images/chungcu2.jpg",
    },
    {
      title: "Nguy cơ thừa cung từ 2026",
      link: "https://vietstock.vn/2025/12/chung-cu-gia-cao-nguy-co-thua-cung-tu-2026-4220-1384548.htm",
      image: "/images/chungcu3.jpg",
    },
  ];

  const [currentIndex, setCurrentIndex] = useState(0);

  // Tự động chuyển tin tức sau 3 giây
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prev) => (prev + 1) % newsItems.length);
    }, 3000);
    return () => clearInterval(interval);
  }, [newsItems.length]);

  return (
    <div className="p-6">
      {/* Header */}
      <h1 className="text-3xl font-bold mb-6 ">Welcome to Bluemoon</h1>

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
          <h2 className="text-xl font-semibold text-teal-600">Tổng số hộ</h2>
          <p className="text-2xl font-bold">{roomCount}</p>
        </div>
      </div>

      {/* Tin tức */}
      <h2 className="text-2xl font-bold mb-4">Tin tức mới nhất</h2>
      <div className="bg-gray-100 p-4 rounded shadow text-center">
        <a
          href={newsItems[currentIndex].link}
          target="_blank"
          rel="noopener noreferrer"
        >
          <img
            src={newsItems[currentIndex].image}
            alt={newsItems[currentIndex].title}
            className="w-full h-[400px] object-cover rounded mb-2"
          />
          <h3 className="text-lg font-semibold">
            {newsItems[currentIndex].title}
          </h3>
        </a>
      </div>
    </div>
  );
}
