import { useQuery } from "@tanstack/react-query";
import { allUbicaciones } from "./fetch";



export const useAllUbicaciones = () => {
    const all = useQuery({
        queryKey: ['ubicaciones'],
        queryFn: allUbicaciones
    });

    return {...all};
};