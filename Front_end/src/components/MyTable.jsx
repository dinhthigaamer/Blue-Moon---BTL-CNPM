import React from 'react';

const MyTable = ({ columns, data, clickRowHandler = (e) => console.log(e.target.closest("tr").dataset.id) }) => {
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
                    </tr>
                </thead>

                <tbody className="divide-y divide-gray-200">
                    {data.map((row, rowIndex) => (
                        <tr data-id={row.id}
                            className="hover:bg-gray-50 transition-colors"
                            onClick={clickRowHandler}
                        >
                            {/* Hiển thị STT dựa trên rowIndex */}
                            <td className="whitespace-nowrap px-4 py-2 text-gray-700 font-medium">
                                {rowIndex + 1}
                            </td>
                            {columns.map((col) => (
                                <td key={col.key} className="whitespace-nowrap px-4 py-2 text-gray-700">
                                    {row[col.key]}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default MyTable;