import React from 'react';

const MyTable = ({ columns, data, actionLabel, onActionClick }) => {
    return (
        <div className="overflow-x-auto rounded-lg border border-gray-200 shadow-sm">
            <table className="min-w-full divide-y divide-gray-200 bg-white text-sm">
                <thead className="bg-gray-50">
                    <tr>
                        {/* Cột STT cố định ở đầu */}
                        <th className="whitespace-nowrap px-4 py-2 font-semibold text-gray-900 text-left w-16">
                            STT
                        </th>
                        {columns.map((col) => (
                            <th
                                key={col.key}
                                className="whitespace-nowrap px-4 py-2 font-semibold text-gray-900 text-left"
                            >
                                {col.label}
                            </th>
                        ))}
                        {/* Cột hành động nếu cần */}
                        {onActionClick && (
                            <th className="px-4 py-2 font-semibold text-gray-900 text-right">Thao tác</th>
                        )}
                    </tr>
                </thead>

                <tbody className="divide-y divide-gray-200">
                    {data.map((row, rowIndex) => (
                        <tr key={rowIndex} className="hover:bg-gray-50 transition-colors">
                            {/* Hiển thị STT dựa trên rowIndex */}
                            <td className="whitespace-nowrap px-4 py-2 text-gray-700 font-medium">
                                {rowIndex + 1}
                            </td>
                            {columns.map((col) => (
                                <td key={col.key} className="whitespace-nowrap px-4 py-2 text-gray-700">
                                    {row[col.key]}
                                </td>
                            ))}
                            {/* Nút bấm hành động */}
                            {onActionClick && (
                                <td className="whitespace-nowrap px-4 py-2 text-right">
                                    <button
                                        onClick={() => onActionClick(row)}
                                        className="inline-block rounded bg-blue-600 px-4 py-2 text-xs font-medium text-white hover:bg-blue-700"
                                    >
                                        {actionLabel || 'Xem'}
                                    </button>
                                </td>
                            )}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default MyTable;