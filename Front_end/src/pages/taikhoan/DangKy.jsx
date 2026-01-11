import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import authAPI from "../../api/authAPI"

export default function DangKy() {
    const navigate = useNavigate()
    const roles = ["ADMIN", "ACCOUNTANT"];
    const names = ["Quản lý", "Kế toán"];
    const [error, setError] = useState("");
    const [errorUserName, setErrorUserName] = useState("");
    const [errorCccd, setErrorCccd] = useState("")
    const [errorPhone, setErrorPhone] = useState("")
    const [errorEmail, setErrorEmail] = useState("")

    const [form, setForm] = useState({
        username: "",
        password: "",
        confirmPassword: "",
        fullName: "", email: "", phone: "",
        cccd: "",
        role: "ADMIN"
    });

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    };

    {/*Kiểm tra mật khẩu có trùng khớp*/ }
    useEffect(() => {
        if (!form.confirmPassword) return;

        if (form.password !== form.confirmPassword) {
            setError("Mật khẩu không khớp");
        } else {
            setError("");
        }
    }, [form.password, form.confirmPassword]);

    {/*Kiểm tra cccd có đúng form*/ }
    useEffect(() => {
        if (form.cccd.length != 12) {
            setErrorCccd("Số căn cước không hợp lệ")
        } else {
            const hasNonDigit = /[^0-9]/.test(form.cccd)

            if (hasNonDigit)
                setErrorCccd("Số căn cước không hợp lệ")
            else
                setErrorCccd("")
        }

    }, [form.cccd]);

    {/*Kiểm tra phone có đúng form*/ }
    useEffect(() => {
        if (form.phone.length != 10) {
            setErrorPhone("Số điện thoại ko hợp lệ");
        } else {
            const hasNonDigit = /[^0-9]/.test(form.phone)

            if (hasNonDigit)
                setErrorPhone("Số căn cước không hợp lệ")
            else
                setErrorPhone("")
        }

    }, [form.phone]);

    // Hàm kiểm tra dữ liệu vào và gọi API đăng ký
    const handleSubmit = async (e) => {
        e.preventDefault();

        if (error === "Mật khẩu không khớp") {
            alert("Mật khẩu xác nhận không khớp!");
            return;
        }

        if (errorCccd === "Số căn cước không hợp lệ") {
            alert("Mã căn cước không hợp lệ!");
            return;
        }

        // Gọi API đăng ký
        try {
            console.log(form);
            const payload = {
                username: form.username,
                password: form.password,
                fullName: form.fullName,
                email: form.email,
                phone: form.phone,
                cccd: form.cccd,
                role: form.role, 
            };

            const response = await authAPI.register(payload);
            const res = response?.data;

            // Đăng ký thành công -> về trang đăng nhập
            if (res?.success === true) {
                alert("Đăng ký thành công, vui lòng đợi ban quản trị chung cư xét duyệt");

                setError("");
                setErrorUserName("");
                setErrorPhone("");
                setErrorCccd("");

                navigate("/dang_nhap");
            }
            else if (res?.errorCode === "AUTH_USERNAME_EXISTED") {
                setErrorUserName("Tên đăng nhập đã được sử dụng");
                return;
            }
            else if (res?.errorCode === "AUTH_PHONE_EXISTED") {
                setErrorPhone("Số điện thoại đã được sử dụng");
                return;
            }
            else if (res?.errorCode === "AUTH_CCCD_EXISTED") {
                setErrorCccd("Số căn cước đã được sử dụng");
                return;
            }
            else {
                alert("Đăng ký thất bại, vui lòng thử lại !");
                return;
            }
        } catch (e) {
            console.log("REGISTER ERROR:", e);
            console.log("status:", e?.response?.status);
            console.log("data:", e?.response?.data);

            alert(e?.response?.data?.message || "Đăng ký thất bại, vui lòng kiểm tra lại");
        }

    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <form
                onSubmit={handleSubmit}
                className="w-full max-w-md bg-white rounded-lg shadow-md p-8"
            >
                {/* Logo / Title */}
                <h2 className="text-2xl font-bold text-center mb-6">
                    <span className="text-teal-400">Đăng ký tài khoản</span>
                </h2>

                {/* Full Name */}
                <div className="mb-6">
                    <span>Họ và tên <span className="text-red-300">*</span></span>
                    <input
                        type="text"
                        name="fullName"
                        placeholder="Họ và tên đầy đủ"
                        value={form.fullName}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                </div>

                {/* Email */}
                <div className="mb-6">
                    <span>Email <span className="text-red-300">*</span></span>
                    <input
                        type="email"
                        name="email"
                        placeholder="example@gmail.com"
                        value={form.email}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                    {errorEmail && <span className="text-red-500 text-sm">{errorEmail}</span>}
                </div>

                {/* Phone */}
                <div className="mb-6">
                    <span>Số điện thoại <span className="text-red-300">*</span></span>
                    <input
                        type="tel"
                        name="phone"
                        placeholder="0987654321"
                        value={form.phone}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                    {errorPhone && <span className="text-red-500 text-sm">{errorPhone}</span>}
                </div>

                {/* CCCD */}
                <div className="mb-6">
                    <span>Số căn cước công dân <span className="text-red-300">*</span></span>
                    <input
                        type="text"
                        name="cccd"
                        placeholder="012345678901"
                        value={form.cccd}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                    {errorCccd && <span className="text-red-500 text-sm">{errorCccd}</span>}
                </div>

                {/* Role */}
                <div className="mb-6">
                    <span>Vai trò <span className="text-red-300">*</span></span>
                    <select name="role"
                        className="w-full px-4 py-2
                                    border rounded
                                    bg-white
                                    focus:outline-none
                                    focus:ring-2 focus:ring-teal-400
                                    "
                        onChange={handleChange}
                        required
                    >
                        {roles.map((item, index) => (
                            <option value={item}>{names[index]}</option>
                        ))}
                    </select>
                </div>

                {/* Username */}
                <div className="mb-4">
                    <span>Tên đăng nhập <span className="text-red-300">*</span></span>
                    <input
                        type="text"
                        name="username"
                        placeholder="Nguyễn Văn A"
                        value={form.username}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                    {errorUserName && <span className="text-red-500 text-sm">{errorUserName}</span>}
                </div>

                {/* Password */}
                <div className="mb-4">
                    <span>Mật khẩu <span className="text-red-300">*</span></span>
                    <input
                        type="password"
                        name="password"
                        placeholder="***"
                        value={form.password}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                </div>

                {/* Confirm Password */}
                <span>Xác nhận mật khẩu <span className="text-red-300">*</span></span>
                <div className="mb-6" id="confirmPassword">
                    <input
                        type="password"
                        name="confirmPassword"
                        placeholder="***"
                        value={form.confirmPassword}
                        onChange={handleChange}
                        required
                        className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
                    />
                    {error && <span className="text-red-500 text-sm">{error}</span>}
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
            </form >
        </div >
    );
}