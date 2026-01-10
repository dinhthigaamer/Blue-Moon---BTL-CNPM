import { useState, useEffect } from "react";
import MyTable from "../../components/MyTable";
import ConfirmModal from "../../components/ConfirmModal";
import authAPI from "../../api/authAPI";

export default function QLTaiKhoan() {
    const [isOpen, setIsOpen] = useState(false);
    const [data, setData] = useState([]);

    const columns = [
        { label: "Username", key: "username" },
        { label: "Họ tên", key: "fullName" },
        // { label: "Email", key: "email" },
        // { label: "SĐT", key: "phone" },
        // { label: "CCCD", key: "cccd" },
        { label: "Vai trò", key: "role" },
    ];

    const infor = [
        { label: "Username", key: "username" },
        { label: "Họ tên", key: "fullName" },
        { label: "Email", key: "email" },
        { label: "Số điện thoại", key: "phone" },
        { label: "Số căn cước", key: "cccd" },
        { label: "Vai trò", key: "role" },
    ]
    const [taiKhoan, setTaiKhoan] = useState({
        id: "",
        username: "",
        fullName: "",
        email: "",
        phone: "",
        cccd: "",
        role: "",
        enabled: false
    });

    const [confirm, setConfirm] = useState({
        open: false,
        action: null, // "approve" | "reject"
        user: null
    });


    useEffect(() => {
        const getAccounts = async function () {
            try {
                const response = await authAPI.getListAccount();

                if (response.data.success === true) {
                    setData(response.data.data);
                    setDataById(data.reduce((acc, item) => {
                        acc[item.id] = item;
                        return acc;
                    }, {}));

                    console.log("Fetch data thành công");
                }
            } catch (error) {
                alert("Đã xảy ra lỗi, vui lòng thử lại");
            }
        }

        getAccounts();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setTaiKhoan(prev => ({ ...prev, [name]: value }));
    };

    const handleClickRow = (row) => {
        setTaiKhoan(row);
        setIsOpen(true);
        // console.log(row);
    }

    const approveAccount = async (id) => {
        try {
            const response = await authAPI.approveAccount(id);

            if (response.data.success === true) {
                alert("Đã duyệt thành công");
                setData(prev => prev.filter(user => user.id !== id));
            }
        } catch (error) {
            alert("Đã xảy ra lỗi, vui lòng thử lại !");
        }
    };

    const rejectAccount = async (id) => {
        try {
            const response = await authAPI.rejectAccount(id);

            if (response.data.success === true) {
                alert("Đã từ chối thành công");
                setData(prev => prev.filter(user => user.id !== id));
            }
        } catch (error) {
            alert("Đã xảy ra lỗi, vui lòng thử lại !");
        }
    };

    const handleConfirm = async (id) => {
        if (confirm.action === "reject") {
            rejectAccount(id);
        } else {
            approveAccount(id);
        }

        setConfirm({ open: false }); setIsOpen(true);
    }

    return (
        <div className="min-h-screen flex flex-col space-y-4">
            <p className="font-semibold py-2">
                Danh sách yêu cầu tạo tài khoản
            </p>

            <MyTable
                columns={columns}
                data={data}
                clickRowHandler={handleClickRow}
            />

            {isOpen && (
                <div>
                    {/* Overlay */}
                    <div
                        className="fixed inset-0 bg-black bg-opacity-50 z-40"
                        onClick={() => setIsOpen(false)}
                    />

                    {/* Modal */}
                    <div className="fixed inset-0 flex items-center justify-center z-50">
                        <div className="bg-white rounded-lg p-6 w-full max-w-md max-h-[80vh] flex flex-col">
                            <h2 className="text-lg font-semibold mb-4">
                                Thông tin yêu cầu tạo tài khoản
                            </h2>

                            <form className="space-y-4 overflow-y-auto">
                                {infor.map(item => (
                                    <Input
                                        key={item.key}
                                        label={item.label}
                                        name={item.key}
                                        value={taiKhoan[item.key] || ""}
                                        onChange={handleChange}
                                        disabled
                                    />
                                ))}

                                <div className="flex justify-end gap-2 pt-2">
                                    <button
                                        type="button"
                                        onClick={() => setIsOpen(false)}
                                        className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                                    >
                                        Đóng
                                    </button>
                                    <button
                                        type="button"
                                        className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
                                        onClick={() => {
                                            setIsOpen(false);
                                            setConfirm({ open: true, action: "approve", username: taiKhoan.username })
                                        }
                                        }
                                    >
                                        Duyệt
                                    </button>
                                    <button
                                        type="button"
                                        className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
                                        onClick={() => {
                                            setIsOpen(false);
                                            setConfirm({ open: true, action: "reject", username: taiKhoan.username });
                                        }
                                        }
                                    >
                                        Từ chối
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            )}

            {confirm.open && (
                <ConfirmModal
                    open={confirm.open}
                    title={"Xác nhận"}
                    message={`Bạn có chắc muốn ${confirm.action === "approve" ? "Duyệt" : "Từ chối"} 
                        tài khoản ${confirm.username}`}
                    onConfirm={() => { handleConfirm(taiKhoan.id) }}
                    onClose={() => { setConfirm({ open: false }); setIsOpen(true); }}
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
                className="w-full border px-3 py-2 rounded bg-gray-100"
            />
        </div>
    );
}
