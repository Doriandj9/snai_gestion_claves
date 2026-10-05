import { useQuery } from "@tanstack/react-query";
import { allResourcesAttributes } from "./fetch";



export const useAllResourcesAttributes = () => {

    const query = useQuery({
        queryKey: ['attributes-resources'],
        queryFn: allResourcesAttributes
    });

    return {...query};
};