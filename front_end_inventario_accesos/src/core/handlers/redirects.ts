import { webRoutes } from "@/routes/webRoutes";
import { useAuthStore } from "@/store/auth/userAuth";
import type { NavigateFunction } from "react-router-dom";


export const redirectForPermissions  = (navigate: NavigateFunction) => {
    const isAuth = useAuthStore.getState().isAuth;
    const params = (new URL(location.href)).searchParams;
    const isRedirect = params.has('redirect');
    
    if(!isAuth){
        return null; 
    }

    const user = useAuthStore.getState().user;
    
    let route = "/dashboard";

    if(user?.roles.find((rol) => rol.includes("ADMIN"))){
       route = webRoutes.admin.path;
    }

    if(isRedirect){
        route = params.get('redirect') ?? route;
    }

    navigate(route);

    return user;


};