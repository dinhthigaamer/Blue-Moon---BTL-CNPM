import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import MyTable from '../../components/MyTable';

export default function CuDan() {
    const [isOpen, setIsOpen] = useState(false);
    const [cuDan, setCuDan] = useState({
        roomNumber: "",
        fullName: "",
        phone: "",
        cccd: "",
        dateOfBirth: "",
        residenceStatus: "",
        vehicleCount: "",
        ethnicity: "",
        religion: "",
        occupation: ""
    });

    const handleChange = (e) => {
        const { name, value, type } = e.target;

        setCuDan(prev => ({
            ...prev,
            [name]: type === "number" ? Number(value) : value
        }));
    };

    const columns = [
        { label: "Họ và tên", key: "fullName" },
        { label: "Phòng", key: "roomNumber" },
        { label: "Số phương tiện", key: "vehicleCount" },
        { label: "Số căn cước", key: "cccd" },
    ];

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

    const data = [
        {
            "id": 10,
            "fullName": "Nguyen Van A",
            "phone": "0911111111",
            "cccd": "012345678901",
            "dateOfBirth": "1999-01-01",
            "religion": "Khong",
            "ethnicity": "Kinh",
            "occupation": "Nhan vien",
            "residenceStatus": "TEMPORARY_ABSENCE",
            "vehicleCount": 1,
            "householdId": 5,
            "roomNumber": 101
        }
    ];

    function handleClick() {
        setIsOpen(true)
    };

    return (
        <div className="min-h-screen flex flex-col space-y-4 ">
            <p className="font-semibold py-2">
                Danh sách cư dân
            </p>
            <MyTable
                columns={columns}
                data={data}
            />

            <button
                className="w-1/4 bg-teal-400 hover:bg-teal-500 text-white font-semibold py-2 rounded transition-colors"
                onClick={handleClick}
            >
                Thêm cư dân
            </button>

            {isOpen && (
                <div>
                    {/* Overlay mờ */}
                    <div
                        className="fixed inset-0 bg-black bg-opacity-50 z-40"
                        onClick={() => setIsOpen(false)}
                    />

                    {/* Form điền cư dân mới */}
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