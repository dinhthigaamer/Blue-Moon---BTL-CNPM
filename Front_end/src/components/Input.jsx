export default function Input({
    label,
    type = "text",
    required,
    options = [],
    ...props
}) {
    const baseClass = `
        w-full
        rounded-lg
        border
        px-3 py-2
        text-sm
        outline-none
        transition
        border-gray-300
        focus:border-blue-500
        focus:ring-2
        focus:ring-blue-200
        disabled:bg-gray-100
        disabled:cursor-not-allowed
    `;

    return (
        <div className="space-y-1">
            {label && (
                <label className="text-sm font-medium text-gray-600">
                    {label}
                    {required && (
                        <span className="ml-1 text-red-500">*</span>
                    )}
                </label>
            )}

            {type === "select" ? (
                <select
                    required={required}
                    {...props}
                    className={baseClass}
                >
                    {/* <option value="" disabled>
                        -- Chọn --
                    </option> */}

                    {options.map((val, index) => (
                        <option key={index} value={val.value}>
                            {val.label}
                        </option>
                    ))}
                </select>
            ) : (
                <input
                    type={type}
                    required={required}
                    {...props}
                    className={baseClass}
                />
            )}
        </div>
    );
}
