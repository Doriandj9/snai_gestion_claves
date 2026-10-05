import { useAuthStore } from "@/store/auth/userAuth";
import type { User } from "../@types/core";
import { redirect } from "react-router-dom";



export const requireAuth = async (): Promise<User> => {
    const isAuth = useAuthStore.getState().isAuth;
    const user = useAuthStore.getState().user;
    const url = new URL(location.href);
    const path = url.pathname;

    if (!isAuth || !user) {
        throw redirect(`/?redirect=${encodeURI(`${path}?${url.searchParams.toString()}`)}`);
    }

    return user;
}; 