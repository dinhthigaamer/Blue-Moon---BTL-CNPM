import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import MyTable from '../../components/MyTable';

export default function CuDan() {
    const [isOpen, setIsOpen] = useState(false);
    const columns = [
        { label: "Họ và tên", key: "fullName" },
        { label: "Phòng", key: "roomNumber" },
        { label: "Số phương tiện", key: "vehicleCount" },
        { label: "Số căn cước", key: "cccd" },
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
        <div className="min-h-screen flex flex-col">
            <button
                className="w-1/4 bg-teal-400 hover:bg-teal-500 text-white font-semibold py-2 rounded transition-colors"
                onClick={handleClick}
            >
                Thêm cư dân
            </button>
            <p className="font-semibold py-2">
                Danh sách cư dân
            </p>
            <MyTable
                columns={columns}
                data={data}
            />

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
                        <div className="bg-white rounded-lg p-6 w-full max-w-md">
                            <h2 className="text-lg font-semibold mb-4">Khởi tạo hộ dân mới</h2>
                            <form className="space-y-4">
                                <input
                                    type="text"
                                    placeholder="Email"
                                    className="border px-3 py-2 rounded w-full"
                                />
                                <input
                                    type="text"
                                    placeholder="OTP"
                                    className="border px-3 py-2 rounded w-full"
                                />
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
