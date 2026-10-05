import { useQuery } from "@tanstack/react-query";
import { getAllUsers } from "./fetch";



export const useAllUsers = () => {
    const data = useQuery({
        queryKey: ['users'],
        queryFn: getAllUsers,
    });

    return {...data};
};