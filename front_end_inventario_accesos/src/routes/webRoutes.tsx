import type { User } from "@/core/@types/core";
import { requireAuth } from "@/core/handlers/auth";
import { redirectPages } from "@/core/handlers/redirectHome";
import { AppLayout } from "@/core/layouts/AppLayout";
import { Categories } from "@/modules/admin/pages/Categories";
import { Ubicaciones } from "@/modules/admin/pages/Ubicaciones";
import { Usuarios } from "@/modules/admin/pages/Usuarios";
import { AuthLogin } from "@/modules/auth/pages/AuthLogin";
import { Inventory } from "@/modules/customer/pages/Inventory";
import { LuLayoutDashboard, LuClipboardMinus, LuBoxes, LuMapPinned, LuUsers, LuNotebookPen } from 'react-icons/lu';

export type StructureBasicRoutesWeb = {
    path: string;
    title: string;
    element: React.ReactNode;
    uri?: CallableFunction;
    middleware?: () => Promise<User | null> | Promise<Response | null>,
    children?: WebRoutesType;
    index?: boolean;
    icon?: React.ReactNode
};

export type ValuesStructureBasicRoutesWeb = StructureBasicRoutesWeb[];

export type WebRoutesType = {
    [key: string]: StructureBasicRoutesWeb;
}


export const webRoutes = {
    auth: {
        title: 'Inicio de Session',
        path: '/',
        middleware: redirectPages,
        element: <AuthLogin />,
        uri: function () {
            return `${this.path}`;
        }
    },
    admin: {
        title: 'Dashboard',
        path: '/admin',
        element: <AppLayout isAdmin />,
        middleware: requireAuth,
        uri: function () {
            return this.path;
        },
        children: {
            dashboard: {
                title: 'Dashboard',
                path: '/admin',
                element: <>dd</>,
                icon: <LuLayoutDashboard />,
                middleware: requireAuth,
                uri: function () {
                    return this.path;
                },
                index: true,
            },
            categories: {
                path: 'categories',
                title: 'Categorías / Subcategories',
                element: <Categories />,
                uri: function () {
                    return `/admin/${this.path}`;
                },
                icon: <LuBoxes />
            },
            users: {
                path: 'users',
                title: 'Usuarios',
                element: <Usuarios />,
                uri: function () {
                    return `/admin/${this.path}`;
                },
                icon: <LuUsers />
            },
            locations: {
                path: 'locations',
                title: 'Ubicaciones',
                element: <Ubicaciones />,
                uri: function () {
                    return `/admin/${this.path}`;
                },
                icon: <LuMapPinned />
            },
            reports: {
                title: 'Reportes',
                path: 'reports',
                icon: <LuClipboardMinus />,
                element: <>reports</>,
                uri: function () {
                    return `/admin/${this.path}`;
                }
            }
        }
    },
    customer: {
        title: 'Dashboard',
        path: '/dashboard',
        element: <AppLayout />,
        middleware: requireAuth,
        uri: function () {
            return this.path;
        },
        children: {
            dashboard: {
                title: 'Dashboard',
                path: '/dashboard',
                element: <>dd</>,
                icon: <LuLayoutDashboard />,
                middleware: requireAuth,
                uri: function () {
                    return this.path;
                },
                index: true,
            },
            inventory: {
                path: 'inventory',
                title: 'Inventario',
                icon: <LuNotebookPen />,
                element: <Inventory />,
                uri: function(){
                    return `/dashboard/${this.path}`;
                }
            }
        }
    }
} as const;

