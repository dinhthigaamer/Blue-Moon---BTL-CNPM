import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useOutletContext } from "react-router-dom";
import authAPI from "../../api/authAPI";

export default function DangNhap({ account, setAccount }) {
    const [errorUserName, setErrorUserName] = useState("");
    const [errorPass, setErrorPass] = useState("");
    const [form, setForm] = useState({ username: "", password: "" });
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await authAPI.login(form);

            if (response.data.message === "Login success") {
                localStorage.setItem("accessToken", response.data.data.token);

                localStorage.setItem("user", JSON.stringify({
                    name: response.data.data.user.username,
                    role: response.data.data.user.role
                }));

                setAccount({
                    "name": response.data.data.user.username,
                    "role": response.data.data.user.role
                });

                setErrorUserName("");
                setErrorPass("");
                navigate("/");
            }
            else if (response.data.errorCode === "AUTH_USER_NOT_FOUND") {
                setErrorUserName("Tên đăng nhập không tồn tại");
                return;
            }
            else {
                setErrorPass("Mật khẩu sai !");
                return;
            }
        } catch (error) {
            console.log(error.response?.data);
            alert("Đăng nhập thất bại, vui lòng thử lại !");
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <form
                onSubmit={handleSubmit}
                className="w-full max-w-md bg-white rounded-lg shadow-md p-8"
            >
                <h2 className="text-2xl font-bold text-center mb-6">
                    <span className="text-teal-400">Đăng nhập</span>
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
                    {errorUserName && <span className="text-red-500 text-sm">{errorUserName}</span>}
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
                    {errorPass && <span className="text-red-500 text-sm">{errorPass}</span>}
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
