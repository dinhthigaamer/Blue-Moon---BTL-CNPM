import { useState } from "react";
import { Outlet } from "react-router-dom";
import Taskbar from "./components/Taskbar";
import HeaderPage from "./components/HeaderPage";
import Body from "./components/Body";

export default function MainLayout({ account, setAccount }) {
    const [collapsed, setCollapsed] = useState(false);

    return (
        <div className="flex h-screen bg-gray-100">
            <Taskbar
                collapsed={collapsed}
                toggle={() => setCollapsed(prev => !prev)}
            />

            <div className="flex flex-col flex-1">
                <HeaderPage
                    account={account}
                    setAccount={setAccount}
                />
                <Body>
                    <Outlet />
                </Body>
            </div>
        </div>
    );
}
