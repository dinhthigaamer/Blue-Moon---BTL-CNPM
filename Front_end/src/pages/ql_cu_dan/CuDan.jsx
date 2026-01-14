import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import MyTable from '../../components/MyTable';
import residentAPI from "../../api/residentAPI";
import ConfirmModal from "../../components/ConfirmModal";
import dateNormalizer from "../../utils/date_normalizer";

export default function CuDan() {
    const navigate = useNavigate();

    const [cuDan, setCuDan] = useState({
        householdId: "",
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
        { label: "Email", key: "email" },
        { label: "Số điện thoại", key: "phone", type: "tel" },
        { label: "Số căn cước", key: "cccd" },
        { label: "Ngày sinh", key: "dateOfBirth", type: "date" },
        { label: "Giới tính", key: "gender" },
        { label: "Tạm trú/tạm vắng", key: "residenceStatus" },
        { label: "Số xe máy", key: "bikeCount", type: "number" },
        { label: "Số xe ô tô", key: "carCount", type: "number" },
        { label: "Dân tộc", key: "ethnicity" },
        { label: "Tôn giáo", key: "religion" },
        { label: "Nghề nghiệp", key: "occupation" }
    ];

    const [data, setData] = useState([]);

    const [search, setSearch] = useState("");
    const [openFilter, setOpenFilter] = useState(false);

    const [filters, setFilters] = useState({
        residenceStatus: "",
        carCount: ""
    });

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const buildQuery = () => {
        const params = new URLSearchParams();

        if (search) {
            params.append("roomName", search);
            // backend map keyword -> fullName OR roomNumber
        }

        Object.entries(filters).forEach(([key, value]) => {
            if (value !== "") {
                params.append(key, value);
            }
        });

        return params.toString();
    };

    const fetchResidents = async () => {
        const query = buildQuery();
        const userList = await residentAPI.queryRes(query);
        setData(userList.data.data);
    };


    useEffect(() => {
        const getResidents = async function () {
            try {
                const response = await residentAPI.getResident();

                if (response.data.success === true) {
                    const residents = response.data.data;
                    setData(residents.map((r, idx) => ({
                        ...r,
                        dateOfBirth: dateNormalizer.normalizeDate(r.dateOfBirth)
                    })));


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
        <div className="min-h-screen flex flex-col space-y-4">

            <p className="font-semibold py-2">
                Danh sách cư dân, bấm vào để xem chi tiết
            </p>

            {/* 📋 TABLE */}
            <MyTable
                columns={columns}
                data={data}
                clickRowHandler={clickRowHandler}
            />

            {/* ➕ ADD BUTTON */}
            <button
                className="
            w-1/4
            bg-teal-400
            hover:bg-teal-500
            text-white
            font-semibold
            py-2
            rounded
            transition-colors
        "
                onClick={() => navigate("add")}
            >
                Thêm cư dân
            </button>
        </div>

    );
}

function Input({ label, ...props }) {
    return (
        <div className="w-full">
            {label && (
                <label className="block text-sm font-medium text-gray-600 mb-1">
                    {label}
                </label>
            )}

            <input
                {...props}
                className="
                    w-full
                    rounded-lg
                    border border-gray-300
                    px-3 py-2
                    text-sm
                    outline-none
                    transition
                    focus:border-blue-500
                    focus:ring-2
                    focus:ring-blue-200
                "
            />
        </div>
    );
}
