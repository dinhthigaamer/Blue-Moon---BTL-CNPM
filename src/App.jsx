import { useState } from "react";
import { Routes, Route } from "react-router-dom";

import HeaderPage from "./components/HeaderPage";
import Taskbar from "./components/Taskbar";
import Body from "./components/Body";

import CuDan from "./pages/CuDan";
import KhoanThu from "./pages/KhoanThu";
import TraCuu from "./pages/TraCuu";
import TrangChu from "./pages/TrangChu"

export default function App() {
  const [collapsed, setCollapsed] = useState(false);

  return (
    <div className="flex h-screen bg-gray-100">
      <Taskbar
        collapsed={collapsed}
        toggle={() => setCollapsed(prev => !prev)}
      />

      <div className="flex flex-col flex-1">
        <HeaderPage />

        <Body>
          <Routes>
            <Route path="/" element={<TrangChu />} />
            <Route path="/cu_dan" element={<CuDan />} />
            <Route path="/khoan_thu" element={<KhoanThu />} />
            <Route path="/tra_cuu" element={<TraCuu />}></Route>
          </Routes>
          {/* content*/}
        </Body>
      </div>
    </div>
  );
}
