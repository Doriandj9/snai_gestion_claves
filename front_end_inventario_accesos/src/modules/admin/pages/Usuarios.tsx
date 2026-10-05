import { AppTable } from "@/core/components/AppTable";
import { useUserTableHelper } from "../helpers/userTableHelper";
import { useAllUsers } from "../hooks/users/queries";
import type { Usuario } from "@/core/@types/core";
import { UserActions } from "../components/UserActions";


export const Usuarios = () => {
    const {data, error, isLoading} = useAllUsers();

    const actions = (usuario: Usuario) => {
        return (
            <>
             <UserActions usuario={usuario} />
            </>
        );
    };
    
    const helper = useUserTableHelper(actions);

    return (
        <>
            <div  className="my-2">
            <AppTable data={data ?? []} error={error}  isLoading={isLoading} tableHelper={helper} displayRows={5}/>
            </div>
           
        </>
    );
};
