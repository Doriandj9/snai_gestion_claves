import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { allSubcategories, createSubCategoriaFn } from "./fetch";
import type { FormSubcategoryCreate } from "../../schema/subcategoriesSchema";



export const useAllSubcategories = () => {
    const query = useQuery({
        queryKey: ['subcategories'],
        queryFn: allSubcategories,
    });

    return {...query};
};


export const useCreateSubcategory = () => {
    const client = useQueryClient();
    const mutation = useMutation({
        mutationKey: ['create-subcategory'],
        mutationFn: (data: FormSubcategoryCreate) => createSubCategoriaFn(data),
        onSuccess(){
            client.invalidateQueries({queryKey: ['subcategories']});
        }
    });

    return {...mutation};
};