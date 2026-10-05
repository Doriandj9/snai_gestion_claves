import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import type { CreateCategoryType } from "../../schema/categoriesSchema";
import { allCategories, createCategory } from "./fetch";



export const useAllCategories = () => {
    const query  = useQuery({
        queryKey: ['categories'],
        queryFn: allCategories
    }); 

    return {...query};
};


export const useCrateCategory = () => {
    const client = useQueryClient();
    const create = useMutation({
        mutationKey: ['create-category'],
        mutationFn: (data: CreateCategoryType) => createCategory(data),
        onSuccess(){
            client.invalidateQueries({queryKey: ['categories']});
        }   
    });

    return {...create};
};


