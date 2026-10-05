import { api } from "@/configuration/app";
import type { AllSubcategories, CreateSubCategoriaFn } from "./subcategories";
import { useAuthStore } from "@/store/auth/userAuth";


export const allSubcategories: AllSubcategories = async () => {
    const response = await api.get('subcategories', {
        headers: {
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
}; 

export const createSubCategoriaFn:CreateSubCategoriaFn = async (data) => {
    const response = await api.post("subcategories", data, {
        headers: {
            "Content-Type": 'application/json',
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
};