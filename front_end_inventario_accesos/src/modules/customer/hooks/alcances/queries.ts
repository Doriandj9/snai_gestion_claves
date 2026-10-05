import { useQuery } from "@tanstack/react-query";
import { allAlcances } from "./fetch";

export const useAllAlcances = () => {
    const hook = useQuery({
        queryKey: ['alcances'],
        queryFn: allAlcances
    });
    return {...hook};
};