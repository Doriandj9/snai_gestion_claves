import { useAuthLogout } from "@/modules/auth/hooks/authLogin";
import { useAuthStore } from "@/store/auth/userAuth";
import { Button } from "@chakra-ui/react";
import { LuLogOut } from 'react-icons/lu';
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";

export const LogoutComponent = () => {
    const {mutateAsync, isPending} = useAuthLogout();
    const logout = useAuthStore((state) => state.logout);
    const navigate = useNavigate();

    const handleLogout = () => {
        toast.promise(mutateAsync(),{
            loading: 'Cerrando la session',
            success: () => {
                logout();
                navigate('/');
                return 'Éxito';
            },
            error: (er) => {
                console.log(er);
                logout();
                navigate('/');
                return 'Éxito';
            },
            position: 'top-center'
        });
    };

    return (
        <>
            <Button colorPalette={'red'} onClick={() => handleLogout()} loading={isPending}>
                Salir <LuLogOut />
            </Button>
        </>
    );
};