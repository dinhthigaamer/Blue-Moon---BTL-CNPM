import { useState } from "react";
import HeaderPage from "./components/HeaderPage";
import Taskbar from "./components/Taskbar";
import Body from "./components/Body";

export default function App() {
  const [collapsed, setCollapsed] = useState(false);

  return (
    <div className="flex h-screen bg-gray-100">
      <Taskbar
        collapsed={collapsed}
        toggle={() => setCollapsed(!collapsed)}
      />

      <div className="flex flex-col flex-1">
        <HeaderPage />
        <Body>{/* content */}</Body>
      </div>
    </div>
  );
}
