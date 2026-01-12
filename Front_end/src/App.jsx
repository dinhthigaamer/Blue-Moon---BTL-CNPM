import { Routes, Route } from "react-router-dom";
import { useState, useEffect } from "react";
import MainLayout from "./MainLayout";

// pages
import TrangChu from "./pages/TrangChu";
import CanHo from "./pages/ql_can_ho/CanHo";
import SuaCanHo from "./pages/ql_can_ho/SuaCanHo";
import ThemCanHo from "./pages/ql_can_ho/ThemCanHo";

import CuDan from "./pages/ql_cu_dan/CuDan";
import ChiTietCuDan from "./pages/ql_cu_dan/ChiTietCuDan";
import SuaCuDan from "./pages/ql_cu_dan/SuaCuDan";
import ThemCuDan from "./pages/ql_cu_dan/ThemCuDan";

import ChiTietCanHo from "./pages/ql_can_ho/ChiTietCanHo";
import KhoanThu from "./pages/KhoanThu";
import TaoKhoanThu from "./pages/TaoKhoanThu";
import TraCuu from "./pages/TraCuu";
import QLTaiKhoan from "./pages/taikhoan/QLTaiKhoan";
import QuanLyLoaiPhi from "./pages/QuanLyLoaiPhi";   // 👈 thêm import

// Tài khoản
import TaiKhoan from "./pages/taikhoan/TaiKhoan";
import DangNhap from "./pages/taikhoan/DangNhap";
import DangKy from "./pages/taikhoan/DangKy";
import QuenMatKhau from "./pages/taikhoan/QuenMatKhau";

export default function App() {
  const [account, setAccount] = useState({
    name: "Dacia",
    role: "Admin",
  });

  const [namePage, setNamePage] = useState("Trang chủ");

  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      setAccount(JSON.parse(savedUser));
    }
  }, []);

  return (
    <Routes>
      {/* Auth pages – KHÔNG layout */}
      <Route
        path="/dang_nhap"
        element={<DangNhap account={account} setAccount={setAccount} />}
      />
      <Route path="/dang_ky" element={<DangKy />} />
      <Route path="/quen_mat_khau" element={<QuenMatKhau />} />

      {/* Main pages – CÓ layout */}
      <Route
        element={
          <MainLayout
            account={account}
            setAccount={setAccount}
            namePage={namePage}
            setNamePage={setNamePage}
          />
        }
      >
        <Route path="/" element={<TrangChu />} />
        <Route path="/tai_khoan" element={<TaiKhoan />} />
        <Route path="/quan_ly_tai_khoan" element={<QLTaiKhoan />} />

        <Route path="/can_ho" element={<CanHo />} />
        <Route path="/can_ho/:id" element={<ChiTietCanHo />} />
        <Route path="/can_ho/:id/edit" element={<SuaCanHo />} />
        <Route path="/can_ho/add" element={<ThemCanHo />} />

        <Route path="/cu_dan" element={<CuDan />} />
        <Route path="/cu_dan/:id" element={<ChiTietCuDan />} />
        <Route path="/cu_dan/:id/edit" element={<SuaCuDan />} />
        <Route path="/cu_dan/add" element={<ThemCuDan />} />

        <Route path="/khoan_thu" element={<KhoanThu />} />
        <Route path="/khoan_thu/tao" element={<TaoKhoanThu />} />
        <Route path="/tra_cuu" element={<TraCuu />} />
        <Route path="/loai_phi" element={<QuanLyLoaiPhi />} /> {/* 👈 thêm route */}
      </Route>
    </Routes>
  );
}
