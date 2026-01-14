import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import MyTable from '../../components/MyTable';
import householdAPI from "../../api/householdAPI";
import ConfirmModal from "../../components/ConfirmModal";

export default function CanHo() {
    const navigate = useNavigate();

    const [canHo, setCanHo] = useState({
        "roomNumber": "203",
        "ownerName": "Nguyễn Văn A",
        "residentCount": 0,
        "vehicleCount": 0
    });

    const columns = [
        { label: "Số phòng", key: "roomNumber" },
        { label: "Chủ sở hữu", key: "ownerName" },
        { label: "Số người", key: "residentCount" },
        { label: "Số phương tiện", key: "vehicleCount" },
        { label: "Tình trạng: ", key: "isVacant" }
    ]

    const [data, setData] = useState([]);
    console.log(data);

    useEffect(() => {
        const getHouses = async function () {
            try {
                const response = await householdAPI.getHousehold();

                if (response.data.success === true) {
                    setData(response.data.data);

                    console.log("Fetch data thành công");
                }
            } catch (error) {
                alert("Đã xảy ra lỗi, vui lòng thử lại");
            }
        }

        getHouses();
    }, []);

    const clickRowHandler = (row) => {
        navigate(`${row.id}`);
    };

    return (
        <div className="min-h-screen flex flex-col space-y-4 ">
            <p className="font-semibold py-2">
                Danh sách căn hộ, bấm vào để xem chi tiết
            </p>
            <MyTable
                columns={columns}
                data={data}
                clickRowHandler={clickRowHandler}
            />

            <button
                className="w-1/4 bg-teal-400 hover:bg-teal-500 text-white font-semibold py-2 rounded transition-colors"
                onClick={() => { navigate("add") }}
            >
                Thêm căn hộ
            </button>
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