import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from 'react'
import ConfirmModal from "../../components/ConfirmModal";
import residentAPI from "../../api/residentAPI";

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
        { label: "Phòng", key: "roomNumber" },
        { label: "Họ và tên", key: "fullName" },
        { label: "Số điện thoại", key: "phone", type: "tel" },
        { label: "Số căn cước", key: "cccd" },
        { label: "Ngày sinh", key: "dateOfBirth", type: "date" },
        { label: "Tạm trú/tạm vắng", key: "residenceStatus" },
        { label: "Số xe máy", key: "bikeCount", type: "number" },
        { label: "Số xe ô tô", key: "carCount", type: "number" },
        { label: "Dân tộc", key: "ethnicity" },
        { label: "Tôn giáo", key: "religion" },
        { label: "Nghề nghiệp", key: "occupation" }
    ];

    const handleChange = (e) => {
        setCuDan({ ...cuDan, [e.target.name]: e.target.value });
    }

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await residentAPI.getDetailById(id);
                console.log(response);
                setCuDan(response.data.data)
            } catch (error) {

            }
        };

        fetchUser();
    }, []);

    handleConfirm = async () => {

    }

    return (
        <div className="min-h-screen bg-gray-100 flex flex-col items-center py-10 px-4">
            <div className="bg-white rounded-lg shadow-md p-8 w-full max-w-4xl">
                <h1 className="text-2xl font-semibold mb-6">Chỉnh sửa cư dân</h1>

                <form className="space-y-6 overflow-y-auto">
                    {infor.map((item, index) => (
                        <Input
                            key={index}
                            label={item.label}
                            name={item.key}
                            type={item.type || "text"}
                            placeholder={cuDan[item.key] || ""}
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

function Input({ label, ...props }) {
    return (
        <div>
            <p className="text-sm text-gray-500 mb-1">{label}</p>
            <input
                {...props}
                className="w-full border px-3 py-2 rounded"
            />
        </div>
    );
}