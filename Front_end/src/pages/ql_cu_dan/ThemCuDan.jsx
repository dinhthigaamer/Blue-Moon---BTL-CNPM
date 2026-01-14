import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from 'react'
import ConfirmModal from "../../components/ConfirmModal";
import residentAPI from "../../api/residentAPI";
import Input from "../../components/Input";

export default function ThemCuDan() {
    const navigate = useNavigate();

    const [confirm, setConfirm] = useState({
        open: false,
        action: null, // "change" | "add"
        user: null
    });

    const [cuDan, setCuDan] = useState({
        "bikeCount": 0,
        "carCount": 0,
        "residenceStatus": "Thường trú",
        "gender": "Nam"
    });

    const infor = [
        { label: "Phòng", key: "roomNumber", required: true },
        { label: "Họ và tên", key: "fullName", required: true },
        { label: "Email", key: "email", required: true },
        { label: "Số điện thoại", key: "phone", type: "tel" },
        { label: "Số căn cước", key: "cccd", required: true },
        { label: "Ngày sinh", key: "dateOfBirth", type: "date", required: true },
        {
            label: "Giới tính", key: "gender", type: "select",
            options: [{ label: "Nam", value: 1 }, { label: "Nữ", value: 2 }, { label: "Khác", value: 3 }]
        },
        {
            label: "Tạm trú/tạm vắng", key: "residenceStatus", type: "select",
            options: [{ label: "Thường trú", value: 1 }, { label: "Tạm trú", value: 2 }, { label: "Tạm vắng", value: 3 }]
        },
        { label: "Số xe máy", key: "bikeCount", type: "number" },
        { label: "Số xe ô tô", key: "carCount", type: "number" },
        { label: "Dân tộc", key: "ethnicity" },
        { label: "Tôn giáo", key: "religion" },
        { label: "Nghề nghiệp", key: "occupation" },
    ];

    const handleChange = (e) => {
        setCuDan({ ...cuDan, [e.target.name]: e.target.value });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(cuDan);

        try {
            const response = await residentAPI.createRes(cuDan);

            console.log(cuDan);
            alert("Đã thêm thành công !");
            navigate("/cu_dan");
        } catch (error) {
            console.log(error);
            if (error.response.data.errorCode === "VALIDATION_ERROR") {
                alert("Mã căn cước hoặc email không hợp lệ");
            }
            else if (error.response.data.errorCode === "NOT_FOUND") {
                alert("Số phòng không hợp lệ");
            }
            else if (error.response.data.errorCode === "NOT_FOUND") {
                alert("Số phòng không hợp lệ");
            }
            else if (error.response.data.errorCode === "BAD_REQUEST") {
                alert("Dữ liệu đầu vào thiếu thông tin");
            }
            else {
                alert("Đã xảy ra lỗi, vui lòng thử lại");
            }
        }
    }

    return (
        <div className="min-h-screen bg-gray-100 flex flex-col items-center py-10 px-4">
            <div className="bg-white rounded-lg shadow-md p-8 w-full max-w-4xl">
                <button
                    onClick={() => navigate("/cu_dan")}
                    className="flex items-center text-gray-600 hover:text-gray-900 mb-4"
                >
                    <span className="text-xl mr-2">←</span>
                    Quay lại danh sách cư dân
                </button>
                <h1 className="text-2xl font-semibold mb-6">Thêm cư dân mới</h1>

                <form
                    onSubmit={handleSubmit}
                    className="space-y-6 overflow-y-auto"
                >
                    {infor.map((item, index) => (
                        <Input
                            key={index}
                            label={item.label}
                            name={item.key}
                            type={item.type || "text"}
                            options={item.options || []}
                            value={cuDan[item.key] ?? item?.options?.[0] ?? ""}
                            required={item.required}
                            onChange={handleChange}
                        />
                    ))}

                    <div className="flex justify-end gap-4 mt-4">
                        <button
                            type="button"
                            onClick={() => navigate(`/cu_dan`)}
                            className="px-6 py-2 bg-gray-300 rounded hover:bg-gray-400"
                        >
                            Hủy
                        </button>
                        <button
                            type="submit"
                            className="px-6 py-2 bg-green-500 text-white rounded hover:bg-green-600"
                        >
                            Lưu
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
