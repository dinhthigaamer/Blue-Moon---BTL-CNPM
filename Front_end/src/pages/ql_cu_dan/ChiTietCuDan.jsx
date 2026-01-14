import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import residentAPI from "../../api/residentAPI";
import ConfirmModal from "../../components/ConfirmModal";

export default function ChiTietCuDan() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [confirm, setConfirm] = useState({
        open: false,
        action: null, // "change" | "add"
        user: null
    });

    const infor = [
        { label: "Phòng", key: "roomNumber" },
        { label: "Họ và tên", key: "fullName" },
        { label: "Email", key: "email" },
        { label: "Số điện thoại", key: "phone", type: "tel" },
        { label: "Số căn cước", key: "cccd" },
        { label: "Ngày sinh", key: "dateOfBirth", type: "date" },
        { label: "Giới tính", key: "gender" },
        { label: "Tạm trú/tạm vắng", key: "residenceStatus" },
        { label: "Số xe máy", key: "bikeCount", type: "number" },
        { label: "Số xe ô tô", key: "carCount", type: "number" },
        { label: "Dân tộc", key: "ethnicity" },
        { label: "Tôn giáo", key: "religion" },
        { label: "Nghề nghiệp", key: "occupation" }
    ];

    const [cuDan, setCuDan] = useState({
        "fullName": "Nguyễn Văn A",
        "phone": "0123456789",
        "roomNumber": "A1-203",
        "dateOfBirth": "2020-01-02"
    });

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

    const handleConfirm = () => {
        try {
            const response = residentAPI.deleteRes(id);

            alert("Đã xoá thành công");
            setConfirm({ open: false });
            navigate("/cu_dan");
        } catch (error) {
            alert("Xoá không thành công");
        }
    };

    return (
        <div className="px-6 py-6 bg-gray-100 min-h-screen">
            <div className="max-w-3xl">
                {/* QUAY LẠI */}
                <button
                    onClick={() => navigate("/cu_dan")}
                    className="flex items-center text-gray-600 hover:text-gray-900 mb-4"
                >
                    <span className="text-xl mr-2">←</span>
                    Quay lại danh sách cư dân
                </button>

                <div className="bg-white rounded-lg shadow p-6">
                    <div className="flex justify-between items-center mb-6">
                        <h1 className="text-xl font-semibold">
                            Chi tiết cư dân
                        </h1>
                    </div>


                    <div className="space-y-4">
                        {
                            infor.map((item, index) => {
                                // console.log(item.key, cuDan[item.key] || "Không có");
                                return (<Info label={item.label} value={cuDan[item.key] || null} />);
                            })
                        }
                    </div>

                    <div className="flex justify-end gap-4 mt-6">
                        <button
                            onClick={() => navigate("edit")}
                            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                        >
                            Chỉnh sửa
                        </button>

                        <button
                            onClick={() => setConfirm({ open: true, action: "del", user: id })}
                            className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
                        >
                            Xóa
                        </button>
                    </div>
                </div>
            </div>

            {confirm.open && (
                <ConfirmModal
                    open={confirm.open}
                    title="Xác nhận"
                    message={`Bạn có chắc muốn xoá cư dân này ?`}
                    onConfirm={handleConfirm}
                    onClose={() => setConfirm({ open: false })}
                />
            )}
        </div>
    );
}

function Info({ label, value }) {
    return (
        <div className="grid grid-cols-3 gap-4 py-2">
            <div className="text-sm text-gray-500">
                {label}
            </div>
            <div className="col-span-2 text-sm font-medium text-gray-800">
                {value || "-"}
            </div>
        </div>
    );
}





