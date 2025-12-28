import { useNavigate } from 'react-router-dom'
import { useState } from 'react'

export default function CuDan() {
    const [isOpen, setIsOpen] = useState(false);

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
            <table class="border border-gray-400 border-collapse w-full">
                <thead>
                    <tr>
                        <th class="border border-gray-400 px-4 py-2">Mã căn hộ</th>
                        <th class="border border-gray-400 px-4 py-2">Chủ hộ</th>
                        <th class="border border-gray-400 px-4 py-2">Số người ở</th>
                        <th class="border border-gray-400 px-4 py-2">Số phương tiện</th>
                        <th class="border border-gray-400 px-4 py-2">Tình trạng</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="border border-gray-400 px-4 py-2 text-center">1</td>
                        <td class="border border-gray-400 px-4 py-2">An</td>
                        <td class="border border-gray-400 px-4 py-2 text-center">20</td>
                        <td class="border border-gray-400 px-4 py-2">An</td>
                        <td class="border border-gray-400 px-4 py-2 text-center">20</td>
                    </tr>
                </tbody>
            </table>

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
