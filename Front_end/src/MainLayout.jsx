import { useState } from "react";
import { Outlet } from "react-router-dom";
import Taskbar from "./components/Taskbar";
import HeaderPage from "./components/HeaderPage";
import Body from "./components/Body";

export default function MainLayout({ account, setAccount, namePage, setNamePage }) {
    const [collapsed, setCollapsed] = useState(false);

    return (
        <div className="flex h-screen bg-gray-100">
            <Taskbar
                collapsed={collapsed}
                namePage={namePage}
                setNamePage={setNamePage}
                toggle={() => setCollapsed(prev => !prev)}
            />

            <div className="flex flex-col flex-1">
                <HeaderPage
                    account={account}
                    setAccount={setAccount}

                    namePage={namePage}
                    setNamePage={setNamePage}
                />
                <Body>
                    <Outlet />
                </Body>
            </div>
        </div>
    );
}
