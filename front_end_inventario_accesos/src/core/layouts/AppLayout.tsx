import { Outlet } from "react-router-dom";
import { Navbar } from "./components/Navbar";
import { NavbarAdmin } from "./components/NavbarAdmin";
import { DashboardNavbar } from "./components/DashboardNavbar";
import { SidebarUser } from "./components/SidebarUser";
import { Show } from "@chakra-ui/react";


export const AppLayout: React.FC<{ isAdmin?: boolean }> = ({ isAdmin }) => {
    return (
        <>
            <div className="min-h-screen bg-slate-100">
                <aside className="bg-white shadow-sm -translate-x-80 fixed inset-0 
            z-50 h-full w-72 transition-transform 
            duration-300 lg:translate-x-0">
                    <div className="mx-2">
                        <h2 className="mt-2 text-center font-bold">SNAI</h2>
                        <SidebarUser />
                        <Show
                            when={!isAdmin}
                            fallback={<NavbarAdmin />}
                        >
                            <Navbar />
                        </Show>
                    </div>
                </aside>

                <div className="p-4 lg:ml-80">
                    <DashboardNavbar />
                    <div className="mt-4">
                        <Outlet />

                    </div>
                </div>
            </div>
        </>
    );
};