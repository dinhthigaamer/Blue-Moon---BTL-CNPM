export default function ConfirmModal({ open, title, message, onConfirm, onClose }) {
    if (!open) return null;

    return (
        <>
            <div className="fixed inset-0 bg-black/70 z-40 backdrop-blur-sm" />
            <div className="fixed inset-0 flex items-center justify-center z-50">
                <div className="bg-white rounded-lg p-6 w-full max-w-sm">
                    <h3 className="text-lg font-semibold mb-4">{title}</h3>
                    <p className="text-gray-600 mb-6">{message}</p>

                    <div className="flex justify-end gap-2">
                        <button
                            className="px-4 py-2 bg-gray-300 rounded"
                            onClick={onClose}
                        >
                            Hủy
                        </button>
                        <button
                            className="px-4 py-2 bg-blue-500 text-white rounded"
                            onClick={onConfirm}
                        >
                            Xác nhận
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
}
