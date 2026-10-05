import { api } from "@/configuration/app";
import type { GetAllUsers } from "./users";
import { useAuthStore } from "@/store/auth/userAuth";


export const getAllUsers: GetAllUsers = async () =>  {
    const response = await api.get("users", {
        headers: {
            Authorization: `Bearer ${useAuthStore.getState().token}`
        }
    });

    return response.data;
}; 
