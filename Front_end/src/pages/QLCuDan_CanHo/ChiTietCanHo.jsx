import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";

export default function ChiTietCanHo() {
    const { id } = useParams();
    const navigate = useNavigate();
    const infor = [
        { label: "Số phòng", key: "roomNumber" },
        { label: "Chủ sở hữu", key: "ownerName" },
        { label: "Số người", key: "residentCount" },
        { label: "Số phương tiện", key: "vehicleCount" },
    ]

    const [editing, setEditing] = useState(false);

    const [canHo, setCanHo] = useState({
        "roomNumber": "203",
        "ownerName": "Nguyễn Văn A",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCanHo((prev) => ({ ...prev, [name]: value }));
    };

    const handleSave = (e) => {
        e.preventDefault();
        setEditing(false);
        alert("Cập nhật căn hộ thành công (demo)");
        // TODO: gọi API update
    };

    const handleDelete = () => {
        const ok = confirm("Bạn có chắc chắn muốn xóa căn hộ này?");
        if (!ok) return;

        alert("Đã xóa căn hộ (demo)");
        // TODO: gọi API delete
        navigate("/can_ho");
    };

    return (
        <div className="px-6 py-6 bg-gray-100 min-h-screen">
            <div className="max-w-3xl">
                {/* Nút quay lại */}
                <button
                    onClick={() => navigate(-1)}
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

                        {!editing && (
                            <div className="flex gap-2">
                                <button
                                    onClick={() => setEditing(true)}
                                    className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                                >
                                    Chỉnh sửa
                                </button>

                                <button
                                    onClick={handleDelete}
                                    className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
                                >
                                    Xóa
                                </button>
                            </div>
                        )}
                    </div>

                    {/* Xem thông tin */}
                    {!editing && (
                        <div className="space-y-4">
                            {
                                infor.map((item, index) => {
                                    // console.log(item.key, cuDan[item.key] || "Không có");
                                    return (<Info label={item.label} value={canHo[item.key] || "Không có"} />);
                                })
                            }
                        </div>
                    )}

                    {/* Form chỉnh sửa */}
                    {editing && (
                        <form onSubmit={handleSave} className="space-y-4">
                            {
                                infor.map((item, index) => {
                                    // console.log(item.key, cuDan[item.key] || "Không có");
                                    return (<Input
                                        label={item.label}
                                        name={item.key}
                                        value={canHo[item.key] || "Không có"}
                                        onChange={handleChange}
                                    />);
                                })
                            }

                            <div className="flex justify-end gap-2 pt-2">
                                <button
                                    type="button"
                                    onClick={() => setEditing(false)}
                                    className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                                >
                                    Hủy
                                </button>

                                <button
                                    type="submit"
                                    className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                                >
                                    Lưu
                                </button>
                            </div>
                        </form>
                    )}
                </div>
            </div>
        </div>
    );
}

// Component helper
function Info({ label, value }) {
    return (
        <div>
            <p className="text-sm text-gray-500">{label}</p>
            <p className="font-medium">{value}</p>
        </div>
    );
}

function Input({ label, ...props }) {
    return (
        <div>
            <p className="text-sm text-gray-500 mb-1">{label}</p>
            <input {...props} className="w-full border px-3 py-2 rounded" />
        </div>
    );
}
