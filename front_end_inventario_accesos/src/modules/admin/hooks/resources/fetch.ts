import { api } from "@/configuration/app";
import { useAuthStore } from "@/store/auth/userAuth";
import type { AllResourcesAttributesFn } from "./resources";



export const allResourcesAttributes: AllResourcesAttributesFn = async () => {
    const response = await api.get('resources/attributes', {
        headers: {
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
};