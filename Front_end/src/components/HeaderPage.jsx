import logo from "../assets/Avatar.png"
import { useState, useRef, useEffect } from "react";
import { Link } from "react-router-dom";

export default function HeaderPage({ account, setAccount }) {
    const [open, setOpen] = useState(false);
    const ref = useRef(null);

    // đóng menu khi click ra ngoài
    useEffect(() => {
        const handleClickOutside = (e) => {
            if (ref.current && !ref.current.contains(e.target)) {
                setOpen(false);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    return (
        <header className="bg-white px-6 py-4 flex justify-between items-center shadow-sm">
            <h1 className="text-xl font-semibold text-gray-700">
                Trang chủ
            </h1>

            <div className="flex items-center gap-4">
                <input
                    type="text"
                    placeholder="Tìm kiếm..."
                    className="px-4 py-2 border rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-teal-400"
                />

                <div className="text-right">
                    <p className="text-sm font-semibold text-gray-700">{account.name}</p>
                    <p className="text-xs text-gray-400">{account.role}</p>
                </div>

                <div className="relative" ref={ref}>
                    <img
                        src={logo}
                        className="w-10 h-10 rounded-full"
                        onClick={(e) => setOpen(!open)}
                    />

                    {/* Menu */}
                    {open && (
                        <div className="absolute right-0 mt-2 w-44 bg-white border rounded-lg shadow-lg z-50">
                            <Link
                                to="/tai_khoan"
                                className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                            >
                                Trang cá nhân
                            </Link>
                            <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                Cài đặt
                            </div>
                            <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-red-500">
                                Đăng xuất
                            </div>
                        </div>
                    )}
                </div>

            </div>
        </header>
    );
}
