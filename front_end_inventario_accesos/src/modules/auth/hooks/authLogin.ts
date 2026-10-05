import { useMutation } from "@tanstack/react-query";
import type { AuthFormType } from "../schemas/auth";
import { authFn, authLogoutFn } from "./fetch";



export const useAuthAttempt = () => {
    const attempt = useMutation({
        mutationKey: ['attempt'],
        mutationFn: (data: AuthFormType) => authFn(data)
    });

    return {...attempt};
};


export const useAuthLogout = () => {
    const logout = useMutation({
        mutationKey: ['logout'],
        mutationFn: authLogoutFn,
    });

    return {...logout};
};