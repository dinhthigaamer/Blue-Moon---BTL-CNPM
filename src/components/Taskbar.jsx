export default function Taskbar({ collapsed, toggle }) {
    return (
        <aside
            className={`bg-white h-full shadow-sm transition-all duration-300
        ${collapsed ? "w-20" : "w-64"}
      `}
        >
            {/* Header sidebar */}
            <div className="flex items-center justify-between px-4 py-4">
                {!collapsed && (
                    <h2 className="text-xl font-bold text-teal-400">
                        face<span className="text-gray-700">WORKS</span>
                    </h2>
                )}

                {/* Hamburger */}
                <button
                    onClick={toggle}
                    className="text-gray-600 hover:text-teal-400 focus:outline-none"
                >
                    ☰
                </button>
            </div>

            {/* Menu */}
            <ul className="mt-6 space-y-2 text-gray-600">
                {[
                    "Trang chủ",
                    "Khách hàng",
                    "Căn hộ",
                    "Hợp đồng",
                    "Hỗ trợ",
                    "Báo cáo",
                ].map((item) => (
                    <li
                        key={item}
                        className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                    >
                        {!collapsed && item}
                    </li>
                ))}
            </ul>

            {/* Logout */}
            <div className="absolute bottom-4 px-4 text-gray-400 cursor-pointer hover:text-red-400">
                {!collapsed && "Đăng xuất"}
            </div>
        </aside>
    );
}
