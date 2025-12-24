import logo from "../assets/Avatar.png"

export default function HeaderPage() {
    return (
        <header className="bg-white px-6 py-4 flex justify-between items-center shadow-sm">
            <h1 className="text-xl font-semibold text-gray-700">
                Trang chủ
            </h1>

            <div className="flex items-center gap-4">
                <input
                    type="text"
                    placeholder="Tìm kiếm..."
                    className="px-4 py-2 border rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-teal-400"
                />

                <div className="text-right">
                    <p className="text-sm font-semibold text-gray-700">Sofia</p>
                    <p className="text-xs text-gray-400">Admin</p>
                </div>

                <img src={logo} className="w-10 h-10 rounded-full" />
            </div>
        </header>
    );
}
