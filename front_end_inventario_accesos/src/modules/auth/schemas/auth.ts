import { useForm } from "react-hook-form";
import { z } from 'zod'; 
import { zodResolver } from '@hookform/resolvers/zod';
import { useAuthAttempt } from "../hooks/authLogin";
import { showError } from "@/core/utilities/errors";
import { useAuthStore } from "@/store/auth/userAuth";
import { redirectForPermissions } from "@/core/handlers/redirects";
import { useNavigate } from "react-router-dom";

const schema = z.object({
    username: z.string("Ingrese su usuario").min(3, "Ingrese su usuario"),
    password: z.string("ingrese su contraseña").min(1, "Ingrese su contraseña")
});

export type AuthFormType = z.input<typeof schema>;

export const useAuthForm = () => {
    const {control, handleSubmit} = useForm<AuthFormType>({
        resolver: zodResolver(schema)
    });
    const navigate = useNavigate();
    const setToken = useAuthStore((state) => state.setAuth);

    const {isPending, mutate, error, isSuccess} = useAuthAttempt();
    
    const handleAuth = handleSubmit((data) => {
        mutate(data, {
            onSuccess(response){
                setToken(response.jwt,response.refreshToken);
                redirectForPermissions(navigate);
            },
            onError(error) {
                showError(error);
            },
        });
    });
    
    return {control, handleAuth, loading: isPending, error, isSuccess};
};





