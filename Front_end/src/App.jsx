import { Routes, Route } from "react-router-dom";
import { useState } from "react";
import MainLayout from "./MainLayout";

// pages
import TrangChu from "./pages/TrangChu";
import CanHo from "./pages/QLCuDan_CanHo/CanHo";
import CuDan from "./pages/QLCuDan_CanHo/CuDan";
import ChiTietCuDan from "./pages/QLCuDan_CanHo/ChiTietCuDan";
import ChiTietCanHo from "./pages/QLCuDan_CanHo/ChiTietCanHo";
import KhoanThu from "./pages/KhoanThu";
import TraCuu from "./pages/TraCuu";
import TaiKhoan from "./pages/taikhoan/TaiKhoan"
import DangNhap from "./pages/taikhoan/DangNhap";
import DangKy from "./pages/taikhoan/DangKy";
import QuenMatKhau from "./pages/taikhoan/QuenMatKhau";

export default function App() {
  const [account, setAccount] = useState({
    "name": "Dacia",
    "role": "Admin"
  });

  return (
    <Routes>
      {/* Auth pages – KHÔNG layout */}
      <Route path="/dang_nhap" element={<DangNhap account={account} setAccount={setAccount} />} />
      <Route path="/dang_ky" element={<DangKy />} />
      <Route path="/quen_mat_khau" element={<QuenMatKhau />} />

      {/* Main pages – CÓ layout */}
      <Route element={<MainLayout account={account} setAccount={setAccount} />}>
        <Route path="/" element={<TrangChu />} />
        <Route path="/tai_khoan" element={<TaiKhoan />} />

        <Route path="/can_ho">
          <Route index element={<CanHo />} />
          <Route path=":id" element={<ChiTietCanHo />} />
        </Route>

        <Route path="/cu_dan" >
          <Route index element={<CuDan />} />
          <Route path=":id" element={<ChiTietCuDan />} />
        </Route>

        <Route path="/khoan_thu" element={<KhoanThu />} />
        <Route path="/tra_cuu" element={<TraCuu />} />
      </Route>
    </Routes>
  );
}
