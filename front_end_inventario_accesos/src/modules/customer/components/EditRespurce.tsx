import { IconButton } from "@chakra-ui/react";
import { LuPencil } from "react-icons/lu";
import { CreateUpdateResource } from "./CreateUpdateResource";
import { useContext } from "react";
import { PaginationContext } from "../contexts/paginationContext";
import type { RecursoTable } from "../hooks/recursos/recurso";
import { useAuthStore } from "@/store/auth/userAuth";

type EditResourceProps = {
    recurso: RecursoTable
};

export const EditResource: React.FC<EditResourceProps> = ({recurso}) => {
    const { page } = useContext(PaginationContext);
    const user = useAuthStore((state) => state.user);

    return (
        <>
            <CreateUpdateResource
                page={page}
                size={5}
                update
                recurso={recurso}
                trigger={(change) => (
                    <IconButton
                        disabled={user?.id !== recurso.user.userId}
                        onClick={() => change(true)}
                        variant="outline" colorPalette={'blue'}><LuPencil /></IconButton>
                )}
            />
        </>
    );
};