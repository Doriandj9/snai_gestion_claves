import { api } from "@/configuration/app";
import type { AllAlcances } from "./alcances";
import { useAuthStore } from "@/store/auth/userAuth";



export const allAlcances: AllAlcances  = async () => {
    const response = await api.get('scopes', {
        headers: {
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
};