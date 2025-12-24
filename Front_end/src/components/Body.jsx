export default function Body({ children }) {
    return (
        <main className="flex-1 p-6 overflow-y-auto">
            {/* Content sẽ được render ở đây */}
            {children}
        </main>
    );
}
