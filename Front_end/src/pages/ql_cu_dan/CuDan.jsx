import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import MyTable from '../../components/MyTable';
import residentAPI from "../../api/residentAPI";
import ConfirmModal from "../../components/ConfirmModal";

export default function CuDan() {
    const navigate = useNavigate();

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

    const columns = [
        { label: "Họ và tên", key: "fullName" },
        { label: "Phòng", key: "roomNumber" },
        { label: "Ngày sinh", key: "dateOfBirth" },
        { label: "Số căn cước", key: "cccd" },
    ];

    const infor = [
        { label: "Phòng", key: "roomNumber" },
        { label: "Họ và tên", key: "fullName" },
        { label: "Số điện thoại", key: "phone", type: "tel" },
        { label: "Số căn cước", key: "cccd" },
        { label: "Ngày sinh", key: "dateOfBirth", type: "date" },
        { label: "Tạm trú/tạm vắng", key: "residenceStatus" },
        { label: "Số xe máy", key: "bikeCount", type: "number" },
        { label: "Số xe ô tô", key: "carCount", type: "number" },
        { label: "Dân tộc", key: "ethnicity" },
        { label: "Tôn giáo", key: "religion" },
        { label: "Nghề nghiệp", key: "occupation" }
    ];

    const [data, setData] = useState([]);

    useEffect(() => {
        const getResidents = async function () {
            try {
                const response = await residentAPI.getResident();

                if (response.data.success === true) {
                    setData(response.data.data);

                    console.log("Fetch data thành công");
                }
            } catch (error) {
                alert("Đã xảy ra lỗi, vui lòng thử lại");
            }
        }

        getResidents();
    }, []);

    const clickRowHandler = (row) => {
        navigate(`${row.id}`);
    };

    console.log(clickRowHandler);

    const handleConfirm = async () => {
        try {
            await residentAPI.createRes(cuDan);
            data.push(cuDan);

            alert("Thêm cư dân thành cồng");

        } catch (error) {

        }
    };

    return (
        <div className="min-h-screen flex flex-col space-y-4 ">
            <p className="font-semibold py-2">
                Danh sách cư dân, bấm vào để xem chi tiết
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
                Thêm cư dân
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