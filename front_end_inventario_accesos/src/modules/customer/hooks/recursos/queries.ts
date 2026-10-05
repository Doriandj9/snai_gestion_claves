import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { allResourcesForUser, createRecurso, updateRecurso } from "./fetch";



export const useAllRecursosForUser = (username: string) => {
    const hook = useQuery({
        queryKey: [username],
        queryFn: () => allResourcesForUser(username)
    });

    return { ...hook };
};

export const useAllRecursosForUserPaged = (username: string, page: number, size: number) => {
    const hook = useQuery({
        queryKey: [username, page, size],
        queryFn: () => allResourcesForUser(username ?? '', page, size),
        enabled: username !== ''
    });

    return { ...hook };
};


export const useCreateRecurso = (username: string, page?: number, size?: number) => {
    const client = useQueryClient();
    const post = useMutation({
        mutationKey: ['create-resource'],
        mutationFn: (data: { [key: string]: unknown }) => createRecurso(data),
        onSuccess() {
            if (page && size) {
                client.invalidateQueries({ queryKey: [username, page - 1, size] });
                return;
            }

            client.invalidateQueries({ queryKey: [username] });

        }
    });

    return { ...post };
};


export const useUpdateRecurso = (recursoId?: string, username?: string, page?: number, size?: number) => {
    const client = useQueryClient();

    const put = useMutation({
        mutationKey: ['update-recurso'],
        mutationFn: (data: {[key: string]: unknown}) => updateRecurso(data,String(recursoId)),
        onSuccess() {
            if (page && size) {
                client.invalidateQueries({ queryKey: [username, page - 1, size] });
                return;
            }

            client.invalidateQueries({ queryKey: [username] });

        }
    });

    return {...put};
};