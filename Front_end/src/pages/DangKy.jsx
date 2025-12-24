import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function DangKy() {
    const [form, setForm] = useState({
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
    });

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (form.password !== form.confirmPassword) {
            alert("Mật khẩu xác nhận không khớp!");
            return;
        }

        console.log("Register data:", form);
        alert("Đăng ký thành công (demo)");
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <form
                onSubmit={handleSubmit}
                className="w-full max-w-md bg-white rounded-lg shadow-md p-8"
            >
                {/* Logo / Title */}
                <h2 className="text-2xl font-bold text-center mb-6">
                    <span className="text-teal-400">face</span>
                    <span className="text-gray-700">WORKS</span>
                </h2>

                {/* Username */}
                <div className="mb-4">
                    <input
                        type="text"
                        name="username"
                        placeholder="Tên đăng nhập"
                        value={form.username}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                </div>

                {/* Email */}
                <div className="mb-4">
                    <input
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={form.email}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                </div>

                {/* Password */}
                <div className="mb-4">
                    <input
                        type="password"
                        name="password"
                        placeholder="Mật khẩu"
                        value={form.password}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                </div>

                {/* Confirm Password */}
                <div className="mb-6">
                    <input
                        type="password"
                        name="confirmPassword"
                        placeholder="Xác nhận mật khẩu"
                        value={form.confirmPassword}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                </div>

                {/* Submit */}
                <button
                    type="submit"
                    className="w-full bg-teal-400 hover:bg-teal-500 text-white font-semibold py-2 rounded transition-colors"
                >
                    Đăng ký
                </button>

                {/* Login link */}
                <p className="text-center text-sm text-gray-500 mt-4">
                    Đã có tài khoản?{" "}
                    <Link to="/dang_nhap" className="text-teal-400 hover:underline cursor-pointer">
                        Đăng nhập
                    </Link>
                </p>
            </form>
        </div>
    );
}
