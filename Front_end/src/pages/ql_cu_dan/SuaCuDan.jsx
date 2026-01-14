import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from 'react'
import ConfirmModal from "../../components/ConfirmModal";
import residentAPI from "../../api/residentAPI";
import Input from "../../components/Input";
import dateNormalizer from "../../utils/date_normalizer";

export default function SuaCuDan() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [confirm, setConfirm] = useState({
        open: false,
        action: null, // "change" | "add"
        user: null
    });

    const [cuDan, setCuDan] = useState({});

    const infor = [
        { label: "Phòng", key: "roomNumber", required: true },
        { label: "Họ và tên", key: "fullName", required: true, disabled: true },
        { label: "Email", key: "email", required: true },
        { label: "Số điện thoại", key: "phone", type: "tel" },
        { label: "Số căn cước", key: "cccd", required: true, disabled: true },
        { label: "Ngày sinh", key: "dateOfBirth", type: "date" },
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

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await residentAPI.getDetailById(id);
                console.log(response);
                const residents = response.data.data;
                setCuDan({
                    ...residents,
                    dateOfBirth: dateNormalizer.normalizeDate(residents.dateOfBirth)
                });
            } catch (error) {
                alert(error?.response?.data?.message || "Đã xảy ra lỗi, vui lòng thử lại");
            }
        };

        fetchUser();
    }, []);

    const handleConfirm = async () => {
        try {
            const id = cuDan["id"];

            const payload = {
                "roomNumber": cuDan["roomNumber"],
                "phone": cuDan["phone"],
                "email": cuDan["email"],
                "carCount": cuDan["carCount"],
                "bikeCount": cuDan["bikeCount"],
                "ethnicity": cuDan["ethnicity"],
                "religion": cuDan["religion"],
                // "dateOfBirth": cuDan["dateOfBirth"],
                "occupation": cuDan["occupation"],
                "residenceStatus": cuDan["residenceStatus"],
                "gender": cuDan["gender"]
            };

            await residentAPI.updateRes(id, payload);
            alert("Cập nhật thành công");
            navigate(-1);
        } catch (error) {
            console.log(error);
            alert("Cập nhật thất bại");
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
                <h1 className="text-2xl font-semibold mb-6">Chỉnh sửa cư dân</h1>

                <form className="space-y-6 overflow-y-auto">
                    {infor.map((item, index) => (
                        <Input
                            key={index}
                            label={item.label}
                            name={item.key}
                            type={item.type || "text"}
                            options={item.options || []}
                            value={cuDan[item.key] ?? item?.options?.[0] ?? ""}
                            disabled={item.disabled}
                            required={item.required}
                            onChange={handleChange}
                        />
                    ))}

                    <div className="flex justify-end gap-4 mt-4">
                        <button
                            type="button"
                            onClick={() => navigate(-1)}
                            className="px-6 py-2 bg-gray-300 rounded hover:bg-gray-400"
                        >
                            Hủy
                        </button>
                        <button
                            type="button"
                            onClick={() =>
                                setConfirm({ open: true, action: "update", username: cuDan.fullName })
                            }
                            className="px-6 py-2 bg-green-500 text-white rounded hover:bg-green-600"
                        >
                            Lưu
                        </button>
                    </div>
                </form>
            </div>

            {confirm.open && (
                <ConfirmModal
                    open={confirm.open}
                    title="Xác nhận"
                    message={`Bạn có chắc muốn lưu thay đổi cho ${confirm.username}?`}
                    onConfirm={handleConfirm}
                    onClose={() => setConfirm({ open: false })}
                />
            )}
        </div>
    );
}

// function Input({ label, ...props }) {
//     console.log(props);
//     return (
//         <div>
//             <p className="text-sm text-gray-500 mb-1">{label}</p>
//             <input
//                 disabled={props["disabled"]}
//                 {...props}
//                 className="w-full border px-3 py-2 rounded"
//             />
//         </div>
//     );
// }