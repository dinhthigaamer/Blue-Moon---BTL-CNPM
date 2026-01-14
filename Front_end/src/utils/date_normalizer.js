const dateNomalizer = {};

dateNomalizer.normalizeDate = (dob) => {
    if (!dob) return "";

    //Dạng mảng
    if (Array.isArray(dob)) {
        const [year, month, day] = dob;

        return `${year}-${String(month).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
    }

    if (dob.includes("T")) {
        return dob.split("T")[0];
    }

    // dạng YYYYMMDD
    if (/^\d{8}$/.test(dob)) {
        return `${dob.slice(0, 4)}-${dob.slice(4, 6)}-${dob.slice(6, 8)}`;
    }

    return dob; // fallback
};

export default dateNomalizer;

