import { webRoutes, type StructureBasicRoutesWeb } from "@/routes/webRoutes";
import { toast } from "sonner";
import { showError } from "./errors";



const recursiveSetObjet = (route: StructureBasicRoutesWeb, container: { [x: string]: string; }[]) => {

    const result = {
        [route.uri ? route.uri() : route.path]:  route.title
    };

    container.push(result);

    if (!route.children) {
       return;
    }

    Object.entries(route.children)
    .forEach((routes) => {
        const route = routes[1];
        recursiveSetObjet(route, container);
    });
};


export const setTitleApp = (pathname: string) => {
    const objRoutes: { [x: string]: string; }[] = [];
    Object.entries(webRoutes)
        .forEach((routes) => {
            const route = routes[1];
            recursiveSetObjet(route, objRoutes);
        });
    const objTitle = objRoutes.find((res) => Reflect.has(res, pathname) || Reflect.has(res, `/${pathname}`));
    const title = objTitle ? objTitle[pathname] ?? objTitle[`/${pathname}`] : '';
    
    document.title = `${title} | SIA TICS `;

};

export const recursiveRoutes = (route: StructureBasicRoutesWeb, routesArr: StructureBasicRoutesWeb[]): void => {

    if (!route.children) return;

    const values = Object.values(route.children);

    for (const route of values) {
        routesArr.push(route);
        recursiveRoutes(route, routesArr);
    }

    return;

};


export const copyFn =  async (text: string) => {

    try{
        navigator.clipboard.writeText(text);
        toast.success('Copiado',{position: 'top-center'});
    }catch(e){
        showError(e as Error);
    }

};