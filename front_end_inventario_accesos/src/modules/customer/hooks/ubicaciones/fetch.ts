import { api } from "@/configuration/app";
import type { AllUbicaciones } from "./ubicaciones";
import { useAuthStore } from "@/store/auth/userAuth";


export const allUbicaciones: AllUbicaciones = async () => {
    const response = await api.get('locations', {
        headers: {
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
}; 