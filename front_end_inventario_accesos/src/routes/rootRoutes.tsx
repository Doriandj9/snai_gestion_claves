import type { NonIndexRouteObject, RouteObject } from "react-router-dom";
import App from "@/App";
import { webRoutes, type WebRoutesType } from "./webRoutes";



const recursiveRoutes = (children?: WebRoutesType): RouteObject[] => {
    if (!children) return [];

    return Object.entries(children).map(
        ([key, def]): RouteObject => {

            const { element, path, title, children: subChildren, middleware, index } = def;

            // Si es ruta index (redirect por defecto)
            if (index === true) {
                return {
                    index: true,
                    element,
                    loader: middleware,
                    handle: { title, key }
                };
            }

            // Ruta normal
            return {
                path,
                element,
                children: recursiveRoutes(subChildren),
                loader: middleware,
                handle: { title, key }
            };
        }
    );
};


export const rootRoutes:NonIndexRouteObject[] = [
    {
        path: '/',
        element: <App />,
        children: [
            ...recursiveRoutes(webRoutes)
        ]
    }
];

