import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import MyTable from "../../components/MyTable"

export default function CanHo() {
    const [isOpen, setIsOpen] = useState(false);

    const columns = [
        { label: "Số phòng", key: "roomNumber" },
        { label: "Chủ sở hữu", key: "ownerName" },
        { label: "Số người", key: "residentCount" },
        { label: "Số phương tiện", key: "vehicleCount" },
    ]

    const infor = [
        { label: "Số phòng", key: "roomNumber" },
        { label: "Chủ sở hữu", key: "ownerName" },
        { label: "Số người", key: "residentCount" },
        { label: "Số phương tiện", key: "vehicleCount" },
    ]

    const [canHo, setCanHo] = useState({
        "roomNumber": "203",
        "ownerName": "Nguyễn Văn A",
        "residentCount": 0,
        "vehicleCount": 0
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCanHo((prev) => ({ ...prev, [name]: value }));
    };

    const data = [
        {
            "id": 10,
            "roomNumber": 101,
            "ownerName": "Nguyen Van A",
            "residentCount": 0,
            "vehicleCount": 0,
            "isVacant": false
        }
    ]

    function handleClick() {
        setIsOpen(true)
    };

    return (
        <div className="min-h-screen flex flex-col space-y-4">
            <p className="font-semibold py-2">
                Danh sách căn hộ, bấm vào để xem chi tiết
            </p>
            <MyTable
                columns={columns}
                data={data}
            />
            <button
                className="w-1/4 bg-teal-400 hover:bg-teal-500 text-white font-semibold py-2 rounded transition-colors"
                onClick={handleClick}
            >
                Thêm căn hộ
            </button>

            {isOpen && (
                <div>
                    {/* Overlay mờ */}
                    <div
                        className="fixed inset-0 bg-black bg-opacity-50 z-40"
                        onClick={() => setIsOpen(false)}
                    />

                    {/* Form điền phòng mới */}
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
                                            value={canHo[item.key] || "Không có"}
                                            onChange={handleChange}
                                        />);
                                    })
                                }
                                <div className="flex justify-end gap-2">
                                    <button
                                        type="button"
                                        onClick={() => setIsOpen(false)}
                                        className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                                    >
                                        Hủy
                                    </button>
                                    <button
                                        type="submit"
                                        className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
                                    >
                                        Gửi
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

function clickRowHandler() {
    
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