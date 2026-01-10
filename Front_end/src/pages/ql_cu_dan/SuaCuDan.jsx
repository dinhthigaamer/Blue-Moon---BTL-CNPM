import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from 'react'
import ConfirmModal from "../../components/ConfirmModal";

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
        { label: "Số phương tiện", key: "vehicleCount", type: "number" },
        { label: "Dân tộc", key: "ethnicity" },
        { label: "Tôn giáo", key: "religion" },
        { label: "Nghề nghiệp", key: "occupation" }
    ];

    const handleChange = (e) => {
        // setCuDan({ ...cuDan, [e.target.name]: e.target.value });
    }

    // useEffect(() => {
    //     const fetchUser = async () => {
    //         try {
    //             const response = await residentAPI.getDetailById(id);
    //             console.log(response);
    //             setCuDan(response.data.data)
    //         } catch (error) {

    //         }
    //     };

    //     fetchUser();
    // }, []);

    return (
        <div>
            <div
                className="fixed inset-0 flex items-center justify-center z-50"
            >
                <div className="bg-white rounded-lg p-6 w-full max-w-md max-h-[80vh] flex flex-col">
                    <h2 className="text-lg font-semibold mb-4">Khởi tạo hộ dân mới</h2>
                    <form className="space-y-4 overflow-y-auto">
                        {
                            infor.map((item, index) => {
                                // console.log(item.key, cuDan[item.key] || "Không có");
                                return (<Input
                                    label={item.label}
                                    name={item.key}
                                    value={cuDan[item.key]}
                                    onChange={handleChange}
                                />);
                            })
                        }
                        <div className="flex justify-end gap-2">
                            <button
                                type="button"
                                onClick={() => navigate(-1)}
                                className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                            >
                                Hủy
                            </button>
                            <button
                                type="submit"
                                className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
                                onClick={setConfirm({ open: true, action: "add", username: cuDan.fullName })}
                            >
                                Gửi
                            </button>
                        </div>
                    </form>
                </div>
            </div >

            {confirm.open && (
                <ConfirmModal
                    open={confirm.open}
                    title={"Xác nhận"}
                    message={`Bạn có chắc muốn thêm cư dân  ${confirm.username}`}
                    onConfirm={() => { handleConfirm() }}
                    onClose={() => { setConfirm({ open: false }); setIsOpen(true); }}
                />
            )}
        </div >
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