import { useState } from "react";
import { Link } from "react-router-dom";

export default function QuenMatKhau() {
    const [email, setEmail] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Reset password for:", email);
        alert("Link đặt lại mật khẩu đã được gửi (demo)");
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <form
                onSubmit={handleSubmit}
                className="w-full max-w-md bg-white rounded-lg shadow-md p-8"
            >
                <h2 className="text-xl font-bold text-center mb-4 text-gray-700">
                    Quên mật khẩu
                </h2>

                <p className="text-sm text-gray-500 text-center mb-6">
                    Nhập email để nhận link đặt lại mật khẩu
                </p>

                <div className="mb-6">
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        className="w-full px-4 py-2 border rounded focus:ring-2 focus:ring-teal-400 outline-none"
                    />
                </div>

                <button
                    type="submit"
                    className="w-full bg-teal-400 hover:bg-teal-500 text-white font-semibold py-2 rounded transition"
                >
                    Gửi yêu cầu
                </button>

                <p className="text-center text-sm text-gray-500 mt-4">
                    <Link to="/dang_nhap" className="text-teal-400 hover:underline">
                        Quay lại đăng nhập
                    </Link>
                </p>
            </form>
        </div>
    );
}