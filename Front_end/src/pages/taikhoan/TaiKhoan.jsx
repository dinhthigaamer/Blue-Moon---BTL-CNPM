import { useEffect, useState } from "react";
import authAPI from "../../api/authAPI"
import { useNavigate } from "react-router-dom";

export default function TaiKhoan() {
    const navigate = useNavigate();
    const [user, setUser] = useState({
        username: "",
        password: "",
        fullname: "", email: "", phone: "",
        cccd: "",
        role: ""
    });

    const role = {
        "ADMIN": "Ban quản lý",
        "ACCOUNTANT": "Kế toán"
    };

    useEffect(() => {
        const fetchMe = async () => {
            try {
                const response = await authAPI.getMe();

                if (response.data.success === true) {
                    // console.log(response.data.data);
                    setUser(response.data.data);
                }
            } catch (e) {
                console.log(e);
                alert("Đã xảy ra lỗi, vui lòng thử lại !");
                navigate("/");
            }
        };

        fetchMe();
    }, []);

    const [editing, setEditing] = useState(false);
    const [showPasswordForm, setShowPasswordForm] = useState(false);
    const [password, setPassword] = useState({
        oldPassword: "",
        newPassword: "",
        confirm: "",
    });
    const [errorPass, setErrorPass] = useState("");

    {/*Kiểm tra mật khẩu có trùng khớp*/ }
    useEffect(() => {
        if (!password.confirm) return;

        if (password.newPassword !== password.confirm) {
            setErrorPass("Mật khẩu không khớp");
        } else {
            setErrorPass("");
        }
    }, [password.newPassword, password.confirm]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUser((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await authAPI.updateMe({ ...user, ...password });

            if (response.data.success === true) {
                alert("Cập nhật thành công");
                navigate("/tai_khoan");
            }
        } catch (e) {
            if (e.response.data.errorCode === "AUTH_PHONE_EXISTED") {
                alert("Số điện thoại đã được sử dụng");
            }
            else if (e.response.data.errorCode === "AUTH_CCCD_EXISTED") {
                alert("Số căn cước đã được sử dụng");
            }
            else if (e.response.data.errorCode === "AUTH_USER_NOT_FOUND") {
                alert("Không tìm thấy tài khoản");
            }
            else
                alert("Đã xảy ra lỗi, vui lòng thử lại !");
        }
    };

    const handlePasswordChange = (e) => {
        const { name, value } = e.target;
        setPassword((prev) => ({ ...prev, [name]: value }));
    };

    const handlePasswordSubmit = async (e) => {
        e.preventDefault();
        if (password.newPass !== password.confirm) {
            alert("Mật khẩu xác nhận không khớp");
            return;
        }

        try {
            const response = await authAPI.updateMe({ ...user, ...password });

            if (response.data.success === true) {
                alert("Cập nhật thành công");
                navigate("/tai_khoan");
            }
        } catch (e) {
            if (e.response.data.errorCode === "AUTH_INVALID_PASSWORD") {
                alert("Mật khẩu sai");
            }
            else if (e.response.data.errorCode === "AUTH_USER_NOT_FOUND") {
                alert("Không tìm thấy tài khoản");
            }
            else
                alert("Đã xảy ra lỗi, vui lòng thử lại !");
        };

        setShowPasswordForm(false);
        setPassword({ current: "", newPass: "", confirm: "" });
    };

    return (
        <div className="px-6 py-6 bg-gray-100 min-h-screen">
            {/* KHUNG NỘI DUNG */}
            <div className="max-w-3xl">
                <div className="bg-white rounded-lg shadow-md p-6">
                    <h1 className="text-xl font-semibold mb-6">
                        Thông tin tài khoản
                    </h1>

                    {/* XEM THÔNG TIN */}
                    {!editing && !showPasswordForm && (
                        <div className="space-y-4">
                            <div>
                                <p className="text-sm text-gray-500">Tên đăng nhập</p>
                                <p className="font-medium">{user.username}</p>
                            </div>

                            <div>
                                <p className="text-sm text-gray-500">Họ và tên</p>
                                <p className="font-medium">{user.fullname}</p>
                            </div>

                            <div>
                                <p className="text-sm text-gray-500">Mã căn cước</p>
                                <p className="font-medium">{user.cccd}</p>
                            </div>

                            <div>
                                <p className="text-sm text-gray-500">Email</p>
                                <p className="font-medium">{user.email}</p>
                            </div>

                            <div>
                                <p className="text-sm text-gray-500">Số điện thoại</p>
                                <p className="font-medium">
                                    {user.phone || "Chưa cập nhật"}
                                </p>
                            </div>

                            <div>
                                <p className="text-sm text-gray-500">Vai trò</p>
                                <p className="font-medium">{role[user.role]}</p>
                            </div>

                            <div className="flex gap-3 pt-4">
                                <button
                                    onClick={() => setEditing(true)}
                                    className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                                >
                                    Cập nhật thông tin
                                </button>

                                <button
                                    onClick={() => setShowPasswordForm(true)}
                                    className="px-4 py-2 bg-orange-500 text-white rounded hover:bg-orange-600"
                                >
                                    Đổi mật khẩu
                                </button>
                            </div>
                        </div>
                    )}

                    {/* SỬA THÔNG TIN */}
                    {editing && (
                        <form onSubmit={handleSubmit} className="space-y-4">
                            <p className="text-sm text-gray-500">Email</p>
                            <input
                                name="email"
                                value={user.email}
                                onChange={handleChange}
                                className="w-full border px-3 py-2 rounded"
                                placeholder="Email"
                                required
                            />

                            <p className="text-sm text-gray-500">Số điện thoại</p>
                            <input
                                name="phone"
                                value={user.phone}
                                onChange={handleChange}
                                className="w-full border px-3 py-2 rounded"
                                placeholder="Số điện thoại"
                                required
                            />

                            <p className="text-sm text-gray-500">Vai trò</p>
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
                                {["ADMIN", "ACCOUNTANT"].map((item, index) => (
                                    <option value={item}>{role[item]}</option>
                                ))}
                            </select>

                            <div className="flex justify-end gap-2">
                                <button
                                    type="button"
                                    onClick={() => setEditing(false)}
                                    className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                                >
                                    Hủy
                                </button>

                                <button
                                    type="submit"
                                    className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                                >
                                    Lưu
                                </button>
                            </div>
                        </form>
                    )}

                    {/* ĐỔI MẬT KHẨU */}
                    {showPasswordForm && (
                        <form onSubmit={handlePasswordSubmit} className="space-y-4">
                            <h2 className="text-lg font-semibold">Đổi mật khẩu</h2>

                            <input
                                type="password"
                                name="oldPassword"
                                placeholder="Mật khẩu hiện tại"
                                value={password.oldPassword}
                                onChange={handlePasswordChange}
                                className="w-full border px-3 py-2 rounded"
                                required
                            />

                            <input
                                type="password"
                                name="newPassword"
                                placeholder="Mật khẩu mới"
                                value={password.newPassword}
                                onChange={handlePasswordChange}
                                className="w-full border px-3 py-2 rounded"
                                required
                            />

                            <div>
                                <input
                                    type="password"
                                    name="confirm"
                                    placeholder="Xác nhận mật khẩu mới"
                                    value={password.confirm}
                                    onChange={handlePasswordChange}
                                    className="w-full border px-3 py-2 rounded"
                                    required
                                />
                                {errorPass && <span className="text-red-500 text-sm">{errorPass}</span>}
                            </div>

                            <div className="flex justify-end gap-2">
                                <button
                                    type="button"
                                    onClick={() => setShowPasswordForm(false)}
                                    className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                                >
                                    Hủy
                                </button>

                                <button
                                    type="submit"
                                    className="px-4 py-2 bg-orange-500 text-white rounded hover:bg-orange-600"
                                >
                                    Cập nhật mật khẩu
                                </button>
                            </div>
                        </form>
                    )}
                </div>
            </div>
        </div>
    );
}
