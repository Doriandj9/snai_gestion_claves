import { api } from "@/configuration/app";
import type { AllCategoriesFn, CrateCategoryFn } from "./categories";
import { useAuthStore } from "@/store/auth/userAuth";


export const allCategories: AllCategoriesFn = async () => {
    const response = await api.get('categories', {
        headers: {
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
    
};

export const createCategory: CrateCategoryFn = async (data) => {
    const response = await api.post("categories", data, {
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
};