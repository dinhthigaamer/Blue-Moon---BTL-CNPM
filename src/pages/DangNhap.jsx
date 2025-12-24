import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function DangNhap() {
    const [form, setForm] = useState({ username: "", password: "" });
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        console.log("Login data:", form);
        // demo thành công
        navigate("/");
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <form
                onSubmit={handleSubmit}
                className="w-full max-w-md bg-white rounded-lg shadow-md p-8"
            >
                <h2 className="text-2xl font-bold text-center mb-6">
                    <span className="text-teal-400">face</span>
                    <span className="text-gray-700">WORKS</span>
                </h2>

                <div className="mb-4">
                    <input
                        type="text"
                        name="username"
                        placeholder="Tên đăng nhập"
                        value={form.username}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:ring-2 focus:ring-teal-400 outline-none"
                    />
                </div>

                <div className="mb-4">
                    <input
                        type="password"
                        name="password"
                        placeholder="Mật khẩu"
                        value={form.password}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:ring-2 focus:ring-teal-400 outline-none"
                    />
                </div>

                <div className="flex justify-between items-center mb-6 text-sm">
                    <Link
                        to="/quen_mat_khau"
                        className="text-teal-400 hover:underline"
                    >
                        Quên mật khẩu?
                    </Link>
                </div>

                <button
                    type="submit"
                    className="w-full bg-teal-400 hover:bg-teal-500 text-white font-semibold py-2 rounded transition"
                >
                    Đăng nhập
                </button>

                <p className="text-center text-sm text-gray-500 mt-4">
                    Chưa có tài khoản?{" "}
                    <Link to="/dang_ky" className="text-teal-400 hover:underline">
                        Đăng ký
                    </Link>
                </p>
            </form>
        </div>
    );
}
