import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export default function QuenMatKhau() {
    const [step, setStep] = useState(1);

    const [email, setEmail] = useState("");
    const [otp, setOtp] = useState("");
    const [timeLeft, setTimeLeft] = useState(60);

    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");

    const [error, setError] = useState("");

    // ⏱ Countdown OTP
    useEffect(() => {
        if (step !== 2) return;

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
    const sendOtp = (e) => {
        e.preventDefault();
        console.log("Gửi OTP tới:", email);
        setTimeLeft(60);
        setStep(2);
    };

    // STEP 2: xác thực OTP (demo)
    const verifyOtp = (e) => {
        e.preventDefault();
        if (otp === "123456") {
            setStep(3);
            setError("");
        } else {
            setError("OTP không hợp lệ");
        }
    };

    // STEP 3: đổi mật khẩu
    const resetPassword = (e) => {
        e.preventDefault();

        if (password !== confirm) {
            setError("Mật khẩu không khớp");
            return;
        }

        console.log("Đổi mật khẩu cho:", email);
        alert("Đổi mật khẩu thành công (demo)");
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <form
                onSubmit={
                    step === 1 ? sendOtp :
                        step === 2 ? verifyOtp :
                            resetPassword
                }
                className="w-full max-w-md bg-white p-8 rounded-lg shadow-md"
            >
                <h2 className="text-xl font-bold text-center mb-4 text-gray-700">
                    {step === 1 && "Quên mật khẩu"}
                    {step === 2 && "Nhập OTP"}
                    {step === 3 && "Đặt mật khẩu mới"}
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
                        <p className="text-sm text-gray-500 mb-4">
                            OTP hết hạn sau <span className="font-semibold">{timeLeft}s</span>
                        </p>
                    </>
                )}

                {/* STEP 3: New password */}
                {step === 3 && (
                    <>
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
                    </>
                )}

                {error && (
                    <p className="text-red-500 text-sm mb-3">{error}</p>
                )}

                <button
                    type="submit"
                    className="w-full bg-teal-400 hover:bg-teal-500 text-white font-semibold py-2 rounded transition"
                >
                    {step === 1 && "Gửi OTP"}
                    {step === 2 && "Xác nhận OTP"}
                    {step === 3 && "Đổi mật khẩu"}
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
