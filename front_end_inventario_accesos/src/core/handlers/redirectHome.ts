import { webRoutes } from "@/routes/webRoutes";
import { useAuthStore } from "@/store/auth/userAuth";
import { redirect } from "react-router-dom";

export const redirectPages = async () => {
    const isAuth = useAuthStore.getState().isAuth;


    if (!isAuth) {
        return null;
    }

    const user = useAuthStore.getState().user;
    



    if (user?.roles.find((rol) => rol.includes("ADMIN"))) {
        console.log('dentro');
        return redirect(webRoutes.admin.path);
        
    }

    

    return redirect("dashboard");
};