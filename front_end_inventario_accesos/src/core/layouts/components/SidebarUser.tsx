import { useAuthStore } from "@/store/auth/userAuth";
import { Avatar } from "@chakra-ui/react";
import { LogoutComponent } from "./LogoutComponent";

const colors = [
    "gray",
    "red",
    "green",
    "blue",
    "teal",
    "pink",
    "purple",
    "cyan",
    "orange",
    "yellow"
];
const rn = Math.random();

export const SidebarUser = () => {
    const user = useAuthStore((state) => state.user);
    const random = Math.floor(rn * colors.length);

    return (
        <>
            <div className="max-w-full shadow p-4 m-2">
                <div className="mt-1">
                    <div className="flex items-center gap-4">
                        <Avatar.Root colorPalette={colors[random]}>
                            <Avatar.Fallback name={user?.name} />
                        </Avatar.Root>
                        <div className="flex-col gap-4">
                            <h4 className="">
                                {user?.name}
                            </h4>
                            <h4 className="text-[12px]">
                                {user?.ubicaciones}
                            </h4>
                        </div>
                    </div>
                </div>
                <div className="flex justify-end mt-2">
                    <LogoutComponent />
                </div>
            </div>
        </>
    );
};