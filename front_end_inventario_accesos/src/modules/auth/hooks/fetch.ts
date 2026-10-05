import { api } from "@/configuration/app";
import type { AuthLoginFn } from "./auth";
import { useAuthStore } from "@/store/auth/userAuth";




export const authFn: AuthLoginFn = async (data) => {
    const response = await api.post("auth/login", data);

    return response.data;
};

export const authLogoutFn = async () => {
    const response = await api.post("auth/logout",null,{
        headers: {
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
};