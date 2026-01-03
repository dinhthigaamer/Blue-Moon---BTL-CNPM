import { NavLink } from "react-router-dom";

export default function Taskbar({ collapsed, toggle }) {
  const menu = [
    { label: "Trang chủ", path: "/" },
    { label: "Căn hộ", path: "/can_ho" },
    { label: "Cư dân", path: "/cu_dan" },
    { label: "Khoản thu", path: "/khoan_thu" },
    { label: "Tra cứu - Thống kê", path: "/tra_cuu" },
  ];

  return (
    <aside
      className={`bg-white h-full shadow-sm transition-all duration-300
        ${collapsed ? "w-20" : "w-64"}
      `}
    >
      {/* Header sidebar */}
      <div className="flex flex-col items-center px-4 py-6">
        {/* Logo luôn hiển thị ở giữa lề trái */}
        <img
          src="/images/logo.png"
          alt="Logo"
          className="h-16 w-auto mb-4"
        />

        {/* Hamburger toggle */}
        <button
          onClick={toggle}
          className="text-gray-600 hover:text-teal-400 focus:outline-none"
        >
          ☰
        </button>
      </div>

      {/* Menu */}
      <nav className="mt-6 flex flex-col space-y-2">
        {menu.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) =>
              `mx-2 px-4 py-2 rounded cursor-pointer transition-colors
              ${
                isActive
                  ? "bg-teal-50 text-teal-500 font-semibold"
                  : "text-gray-600 hover:bg-gray-100"
              }`
            }
          >
            {!collapsed && item.label}
          </NavLink>
        ))}
      </nav>

      {/* Logout */}
      <button
        className={`absolute bottom-4 mx-4 text-gray-400 hover:text-red-400
          ${collapsed ? "hidden" : "block"}`}
      >
        Đăng xuất
      </button>
    </aside>
  );
}
