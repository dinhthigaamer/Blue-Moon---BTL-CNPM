import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from 'react'
import ConfirmModal from "../../components/ConfirmModal";
import householdAPI from "../../api/householdAPI";

export default function SuaCanHo() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [confirm, setConfirm] = useState({
        open: false,
        action: null, // "change" | "add"
        user: null
    });

    const [canHo, setCanHo] = useState({});

    const infor = [
        { label: "Phòng", key: "roomNumber" },
        { label: "Chủ hộ", key: "ownerName" },
        { label: "CCCD của chủ hộ", key: "ownerCccd" },
        { label: "Diện tích", key: "area" },
        { label: "Số người ở", key: "residentCount" },
        { label: "Số xe máy", key: "bikeCount", type: "number" },
        { label: "Số xe ô tô", key: "carCount", type: "number" },
    ];

    const handleChange = (e) => {
        setCuDan({ ...canHo, [e.target.name]: e.target.value });
    }

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await householdAPI.getDetailById(id);
                // console.log(response);
                setCanHo(response.data.data);
            } catch (error) {

            }
        };

        fetchUser();
    }, []);

    const handleConfirm = async () => {
        try {
            const body = {
                "ownerName": canHo["ownerName"],
                "ownerCccd": canHo["ownerCccd"],
                "area": canHo["area"],
                "isVacant": canHo["isVacant"]
            }
            console.log(canHo);
            householdAPI.update(id, body);
            alert("Cập nhật thành công");
            setConfirm({ open: false });

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
                    Quay lại danh sách căn hộ
                </button>
                <h1 className="text-2xl font-semibold mb-6">Chỉnh sửa căn hộ</h1>

                <form className="space-y-6 overflow-y-auto">
                    {infor.map((item, index) => (
                        <Input
                            key={index}
                            label={item.label}
                            name={item.key}
                            type={item.type || "text"}
                            placeholder={canHo[item.key] || ""}
                            disabled={item.disabled ? true : false}
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
                                setConfirm({ open: true, action: "update" })
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
                    message={`Bạn có chắc muốn lưu thay đổi cho căn hộ`}
                    onConfirm={handleConfirm}
                    onClose={() => setConfirm({ open: false })}
                />
            )}
        </div>
    );
}

function Input({ label, ...props }) {
    console.log(props);
    return (
        <div>
            <p className="text-sm text-gray-500 mb-1">{label}</p>
            <input
                disabled={props["disabled"]}
                {...props}
                className="w-full border px-3 py-2 rounded"
            />
        </div>
    );
}