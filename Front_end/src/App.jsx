import { Routes, Route } from "react-router-dom";

import MainLayout from "./MainLayout";

// pages
import TrangChu from "./pages/TrangChu";
import CuDan from "./pages/CuDan";
import KhoanThu from "./pages/KhoanThu";
import TraCuu from "./pages/TraCuu";

import DangNhap from "./pages/DangNhap";
import DangKy from "./pages/DangKy";
import QuenMatKhau from "./pages/QuenMatKhau";

export default function App() {
  return (
    <Routes>
      {/* Auth pages – KHÔNG layout */}
      <Route path="/dang_nhap" element={<DangNhap />} />
      <Route path="/dang_ky" element={<DangKy />} />
      <Route path="/quen_mat_khau" element={<QuenMatKhau />} />

      {/* Main pages – CÓ layout */}
      <Route element={<MainLayout />}>
        <Route path="/" element={<TrangChu />} />
        <Route path="/cu_dan" element={<CuDan />} />
        <Route path="/khoan_thu" element={<KhoanThu />} />
        <Route path="/tra_cuu" element={<TraCuu />} />
      </Route>
    </Routes>
  );
}
