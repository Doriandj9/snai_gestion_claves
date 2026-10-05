import { useAuthStore } from "@/store/auth/userAuth";
import { ContentResourcesCards } from "../components/ContentResourcesCards";
import { CreateUpdateResource } from "../components/CreateUpdateResource";
import { useState } from "react";
import { PaginationContext } from "../contexts/paginationContext";
import { Button } from "@chakra-ui/react";
import { LuPlus } from "react-icons/lu";



export const Inventory = () => {
    const user = useAuthStore((state) => state.user);
    const [page, setPage] = useState(1);

    return (
        <>
            <div className="flex justify-between mb-1">
                <h2 className="text-lg font-bold">Mis registros</h2>
                <CreateUpdateResource
                    trigger={(change) => (
                        <Button onClick={() => change(true)}>
                            <LuPlus /> Nuevo Registro
                        </Button>
                    )}
                    page={page} size={5} />
            </div>
            <div>
                <PaginationContext.Provider
                    value={{ page, change: setPage }}
                >
                    <ContentResourcesCards username={user?.username ?? ''} />
                </PaginationContext.Provider>

            </div>
        </>
    );
};