import { api } from "@/configuration/app";
import { useAuthStore } from "@/store/auth/userAuth";
import type { PageResponse } from "@/core/@types/core";
import type { AllResourcesForUserFn, CreateRecursoFn, RecursoTable, UpdateRecursoFn } from "./recurso"; 


const _allResourcesForUser = async (
    username: string, 
    page?: number, 
    size?: number
): Promise<PageResponse<RecursoTable> | RecursoTable[]> => {
    
    const token = useAuthStore.getState().token;
    
    if (page !== undefined && size !== undefined) {
        
        const response = await api.get("resources", {
            params: { username, page, size },
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        
        return response.data as PageResponse<RecursoTable>;

    } 
    
    const response = await api.get("resources", {
        params: { username },
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    return response.data as RecursoTable[];
};

export const allResourcesForUser: AllResourcesForUserFn = _allResourcesForUser as AllResourcesForUserFn;


export const createRecurso: CreateRecursoFn = async (data) => {
    const response = await api.post('resources',data,{
        headers: {
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
};

export const updateRecurso: UpdateRecursoFn = async (data, recursoId) => {
    const response = await api.put(`resources/${recursoId}`,data,{
        headers: {
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
};