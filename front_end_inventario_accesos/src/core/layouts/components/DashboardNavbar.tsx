import { IconButton } from "@chakra-ui/react";
import { RoutesBreadcrumb } from "./RoutesBreadcrumb";
import { SearchDashboard } from "./SearchDashboard";
import { LuSettings } from "react-icons/lu";


export const DashboardNavbar = () => {

    return (
        <>
            <nav className="block w-full max-w-full bg-transparent text-white shadow-none rounded-xl transition-all px-0 py-1">
                <div className="flex flex-col-reverse justify-between gap-6 md:flex-row md:items-center">
                    <div className="capitalize">
                        <RoutesBreadcrumb />
                    </div>
                    <div className="flex items-center">
                        <div className="mr-auto md:mr-4 md:w-56" aria-label="search-content">
                            <div className="relative w-full min-w-[200px] h-10">
                                <SearchDashboard />
                            </div>
                        </div>
                        <div className="lg:hidden">
                            <IconButton variant={'subtle'} >
                                <span className="absolute top-1/2 left-1/2 transform -translate-y-1/2 -translate-x-1/2">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true" strokeWidth="3" className="h-6 w-6 text-gray-900"><path fillRule="evenodd" d="M3 6.75A.75.75 0 013.75 6h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 6.75zM3 12a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 12zm0 5.25a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75a.75.75 0 01-.75-.75z" clipRule="evenodd"></path></svg>
                                </span>
                            </IconButton>

                        </div>
                        <IconButton variant={'subtle'}>
                            <LuSettings />
                        </IconButton>
                    </div>
                </div>
            </nav>
        </>
    );
};