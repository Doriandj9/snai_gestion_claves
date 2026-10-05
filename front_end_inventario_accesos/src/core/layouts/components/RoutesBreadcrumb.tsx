import { webRoutes, type StructureBasicRoutesWeb } from "@/routes/webRoutes";
import { Breadcrumb, Show } from "@chakra-ui/react";
import React from "react";
import { NavLink, useLocation } from "react-router-dom";


export const RoutesBreadcrumb = () => {
    const { pathname } = useLocation();
    const routes = pathname.split('/').filter((path) => path !== '');
    const arrayBreadcrumb = [];
    let routeFind: StructureBasicRoutesWeb[] = Object.values(webRoutes).filter((t) => t.path !== '/');

    for (const route of routes) {
        const routeSelect = routeFind.find((r) => r.path.includes(route));

        if (routeSelect) {
            arrayBreadcrumb.push({
                path: routeSelect.path,
                title: routeSelect.title
            });
        }

        if (routeSelect?.children) {
            routeFind = Object.values(routeSelect.children);
        }
    }

    return (
        <>
            <Breadcrumb.Root>
                <Breadcrumb.List>
                    {arrayBreadcrumb.map((li, index) => (
                        <React.Fragment key={li.path}>
                            <Breadcrumb.Item >
                                <NavLink className={({ isActive }) => isActive ? "text-blue-950" : ""}
                                    to={li.path}> {li.title}</NavLink>
                            </Breadcrumb.Item>
                            <Show
                                when={index !== arrayBreadcrumb.length - 1}
                            >
                                <Breadcrumb.Separator />
                            </Show>
                        </React.Fragment>
                    ))}

                </Breadcrumb.List>
            </Breadcrumb.Root>
            <h6 className="block antialiased tracking-normal font-sans text-base font-semibold leading-relaxed text-gray-800">
                {arrayBreadcrumb[arrayBreadcrumb.length -1].title}
            </h6>
        </>
    );
};