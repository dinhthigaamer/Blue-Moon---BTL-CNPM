import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import householdAPI from "../../api/householdAPI";
import ConfirmModal from "../../components/ConfirmModal";

export default function ChiTietCanHo() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [confirm, setConfirm] = useState({
        open: false,
        action: null, // "change" | "add"
        user: null
    });

    const infor = [
        { label: "Phòng", key: "roomNumber" },
        { label: "Chủ hộ", key: "ownerName" },
        { label: "CCCD của chủ hộ", key: "ownerCccd" },
        { label: "Diện tích", key: "area" },
        { label: "Số người ở", key: "residentCount" },
        { label: "Số xe máy", key: "bikeCount", type: "number" },
        { label: "Số xe ô tô", key: "carCount", type: "number" },
    ];

    const [canHo, setCanHo] = useState({
        "roomNumber": "203",
        "ownerName": "Nguyễn Văn A",
        "residentCount": 0,
        "vehicleCount": 0
    });

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await householdAPI.getDetailById(id);
                const { residents, ...responseNew } = response.data.data;
                // console.log(response);
                setCanHo(responseNew);
            } catch (error) {

            }
        };

        fetchUser();
    }, []);

    const handleConfirm = () => {
        try {
            const response = householdAPI.deleteHouse(id);

            alert("Đã xoá thành công");
            setConfirm({ open: false });
            navigate("/can_ho");
        } catch (error) {
            console.log(error);
            alert("Xoá không thành công");
        }
    };

    return (
        <div className="px-6 py-6 bg-gray-100 min-h-screen">
            <div className="max-w-3xl">
                {/* QUAY LẠI */}
                <button
                    onClick={() => navigate("/can_ho")}
                    className="flex items-center text-gray-600 hover:text-gray-900 mb-4"
                >
                    <span className="text-xl mr-2">←</span>
                    Quay lại danh sách căn hộ
                </button>

                <div className="bg-white rounded-lg shadow p-6">
                    <div className="flex justify-between items-center mb-6">
                        <h1 className="text-xl font-semibold">
                            Chi tiết căn hộ
                        </h1>
                    </div>


                    <div className="space-y-4">
                        {
                            infor.map((item, index) => {
                                // console.log(item.key, cuDan[item.key] || "Không có");
                                return (<Info label={item.label} value={canHo[item.key] || null} />);
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
                    message={`Bạn có chắc muốn xoá căn hộ này ?`}
                    onConfirm={handleConfirm}
                    onClose={() => setConfirm({ open: false })}
                />
            )}
        </div>
    );
}

function Info({ label, value }) {
    // console.log(label, value)
    return (
        <div key={label}>
            <p className="text-sm text-gray-500">{label}</p>
            <p className="font-medium">{value}</p>
        </div>
    );
}

