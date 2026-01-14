import logo from "../assets/Avatar.png"
import { useState, useRef, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import ConfirmModal from "../components/ConfirmModal";

export default function HeaderPage({ account, setAccount, namePage, setNamePage }) {
    const [open, setOpen] = useState(false);
    const ref = useRef(null);
    const location = useLocation();

    const [confirm, setConfirm] = useState(false);
    const navigate = useNavigate();

    const handleLogout = () => {
        if (localStorage.getItem("accessToken")) {
            localStorage.removeItem("accessToken");
        }
        navigate("/dang_nhap");
    };
    const pageTitles = {
        "/": "Trang chủ",
        "/can_ho": "Căn hộ",
        "/cu_dan": "Cư dân",
        "/tra_cuu": "Tra cứu - Thống kê",
        "/dang_ky": "Đăng ký",
        "/dang_nhap": "Đăng nhập",
        "/khoan_thu": "Khoản thu",
        "/tai_khoan": "Tài khoản"
    };

    setNamePage(pageTitles[location.pathname]);

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
                {namePage}
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
                            <div className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-red-500"
                                onClick={() => setConfirm(true)}
                            >
                                Đăng xuất
                            </div>
                        </div>
                    )}
                </div>

            </div>

            <ConfirmModal
                open={confirm}
                title="Xác nhận đăng xuất"
                message="Bạn có muốn đăng xuất khỏi tài khoản ?"
                onConfirm={handleLogout}
                onClose={() => setConfirm(false)}
            />
        </header>
    );
}
