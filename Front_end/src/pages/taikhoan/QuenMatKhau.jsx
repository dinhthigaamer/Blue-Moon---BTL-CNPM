import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import authAPI from "../../api/authAPI";

export default function QuenMatKhau() {
    const navigate = useNavigate();
    const [step, setStep] = useState(2);

    const [email, setEmail] = useState("");
    const [otp, setOtp] = useState("");
    const [timeLeft, setTimeLeft] = useState(0);

    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");

    const [error, setError] = useState("");

    // ⏱ Countdown OTP
    useEffect(() => {
        if (step !== 2) return;
        setTimeLeft(60);

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
    }, [step]);

    // STEP 1: gửi OTP (demo)
    const sendOtp = async (e) => {
        e.preventDefault();

        try {
            const response = await authAPI.requestOTP({ "email": email });

            if (response.message === "OTP sent to email") {
                setStep(2);
            }
            else if (response.errorCode === "AUTH_USER_NOT_FOUND") {
                setError("Email không hợp lệ !");
            }
            else if (response.errorCode === "GLOBAL_ERROR") {
                alert("Đã xảy ra lỗi, vui lòng thử lại");
            }
        } catch (error) {
            alert("Đã xảy ra lỗi, vui lòng thử lại");
        }
    };

    // STEP 2: xác thực OTP & set password (demo)
    const verifyOtp = async (e) => {
        e.preventDefault();

        try {
            const response = await authAPI.confirmOTP({
                "email": email,
                "otp": otp,
                "password": password
            });

            if (response.message === "Password reset success") {
                alert("Thay đổi mật khẩu thành công");
                setError("");
                navigate("/dang_nhap");
                return;
            }
            else if (response.errorCode === "AUTH_OTP_INVALID") {
                setError("OTP không hợp lệ");
            }
            else if (response.errorCode === "AUTH_OTP_EXPIRED") {
                setError("OTP đã hết hạn");
            }
            else if (response.errorCode === "AUTH_USER_NOT_FOUND") {
                setError("Tài khoản không hợp lệ");
            }
            else {
                alert("Đã xảy ra lỗi, vui lòng thử lại");
            }
        } catch (error) {
            alert("Đã xảy ra lỗi, vui lòng thử lại !");
        }
    };

    // // STEP 3: đổi mật khẩu
    // const resetPassword = (e) => {
    //     e.preventDefault();

    //     if (password !== confirm) {
    //         setError("Mật khẩu không khớp");
    //         return;
    //     }

    //     console.log("Đổi mật khẩu cho:", email);
    //     alert("Đổi mật khẩu thành công (demo)");
    // };

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
                    {step === 2 && "Nhập OTP"}
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
