import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import authAPI from "../../api/authAPI";

export default function QuenMatKhau() {
    const navigate = useNavigate();
    const [step, setStep] = useState(1);

    const [email, setEmail] = useState("");
    const [otp, setOtp] = useState("");
    const [timeLeft, setTimeLeft] = useState(0);

    const [resendCount, setResendCount] = useState(0);
    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");

    const [error, setError] = useState("");

    // ⏱ Countdown OTP
    useEffect(() => {
        if (step !== 2) return;
        setTimeLeft(300);

        const timer = setInterval(() => {
            setTimeLeft(prev => {
                if (prev <= 1) {
                    clearInterval(timer);
                    return 0;
                }
                return prev - 1;
            });
        }, 1000);

        return () => clearInterval(timer);
    }, [step, resendCount]);

    // STEP 1: gửi OTP (demo)
    const sendOtp = async (e) => {
        e.preventDefault();

        try {
            setStep(2);
            setResendCount(c => c + 1);
            const response = await authAPI.requestOTP({ "email": email });
        } catch (error) {
            if (error?.response?.data?.errorCode === "AUTH_USER_NOT_FOUND") {
                setError("Email không hợp lệ !");
            }
            else if (error?.response?.data?.errorCode === "GLOBAL_ERROR") {
                alert("Đã xảy ra lỗi, vui lòng thử lại");
            }
            else
                alert("Đã xảy ra lỗi, vui lòng thử lại");
        }
    };

    // STEP 2: xác thực OTP & set password (demo)
    const verifyOtp = async (e) => {
        e.preventDefault();

        try {
            const payload = {
                "email": email,
                "otp": otp,
                "newPassword": password
            }

            const response = await authAPI.confirmOTP(payload);

            if (response?.data?.success === true) {
                alert("Thay đổi mật khẩu thành công");
                setError("");
                navigate("/dang_nhap");
                return;
            }
        } catch (error) {
            if (error.response.data.errorCode === "AUTH_OTP_INVALID") {
                setError("OTP không hợp lệ");
            }
            else if (error.response.data.errorCode === "AUTH_OTP_EXPIRED") {
                setError("OTP đã hết hạn");
            }
            else if (error.response.data.errorCode === "AUTH_USER_NOT_FOUND") {
                setError("Tài khoản không hợp lệ");
            }
            else {
                alert("Đã xảy ra lỗi, vui lòng thử lại");
            }
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <form
                onSubmit={
                    step === 1 ? sendOtp :
                        step === 2 ? verifyOtp : None
                }
                className="w-full max-w-md bg-white p-8 rounded-lg shadow-md"
            >
                <h2 className="text-xl font-bold text-center mb-4 text-gray-700">
                    {step === 1 && "Quên mật khẩu"}
                    {step === 2 && "Chúng tôi đã gửi mã OTP về gmail của bạn, vui lòng nhập mã để tiếp tục"}
                    {/* {step === 3 && "Đặt mật khẩu mới"} */}
                </h2>

                {/* STEP 1: Email */}
                {step === 1 && (
                    <input
                        type="email"
                        placeholder="Nhập email của bạn"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        required
                        className="w-full px-4 py-2 border rounded mb-4 focus:ring-2 focus:ring-teal-400 outline-none"
                    />
                )}

                {/* STEP 2: OTP */}
                {step === 2 && (
                    <>
                        <input
                            type="text"
                            placeholder="Nhập OTP"
                            value={otp}
                            onChange={e => setOtp(e.target.value)}
                            required
                            className="w-full px-4 py-2 border rounded mb-2 focus:ring-2 focus:ring-teal-400 outline-none"
                        />
                        <input
                            type="password"
                            placeholder="Mật khẩu mới"
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                            required
                            className="w-full px-4 py-2 border rounded mb-3 focus:ring-2 focus:ring-teal-400 outline-none"
                        />
                        <input
                            type="password"
                            placeholder="Xác nhận mật khẩu"
                            value={confirm}
                            onChange={e => setConfirm(e.target.value)}
                            required
                            className="w-full px-4 py-2 border rounded mb-3 focus:ring-2 focus:ring-teal-400 outline-none"
                        />
                        <p className="text-sm text-gray-500 mb-4">
                            OTP hết hạn sau <span className="font-semibold">{timeLeft}s</span>
                        </p>
                    </>
                )}

                {error && (
                    <p className="text-red-500 text-sm mb-3">{error}</p>
                )}

                <div className="flex gap-3">
                    {/* Nút submit chính */}
                    <button
                        type="submit"
                        className="flex-1 bg-teal-400 hover:bg-teal-500 text-white font-semibold py-2 rounded transition"
                    >
                        {step === 1 && "Gửi OTP"}
                        {step === 2 && "Đổi mật khẩu"}
                    </button>

                    {/* Nút gửi lại OTP */}
                    <button
                        type="button"
                        onClick={sendOtp}
                        disabled={step !== 2}
                        className="px-4 bg-gray-200 hover:bg-gray-300 text-gray-700 font-medium py-2 rounded transition disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                        Gửi lại OTP
                    </button>
                </div>

                <p className="text-center text-sm text-gray-500 mt-4">
                    <Link to="/dang_nhap" className="text-teal-400 hover:underline">
                        Quay lại đăng nhập
                    </Link>
                </p>
            </form>
        </div>
    );
}
